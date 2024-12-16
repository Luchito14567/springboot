package com.example.appSocket.controller;
import com.example.appSocket.model.Task;
import com.example.appSocket.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Component
public class CrudWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private TaskService taskService; // Servicio CRUD

    private final ObjectMapper objectMapper = new ObjectMapper(); // Convertir JSON a objetos y viceversa

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Map<String, Object> response = new HashMap<>();
        
       
        
        try {
            // Convertir el mensaje recibido a un mapa
            Map<String, Object> request = objectMapper.readValue(payload, Map.class);
            String action = (String) request.get("action");

            System.out.println(action);
            
            switch (action) {
                case "create":
                    // Crear tarea
                    Task task = new Task();
                    task.setName((String) request.get("name"));
                    task.setDescription((String) request.get("description"));
                    task.setCompleted(Boolean.parseBoolean((String) request.get("completed")));
                    
                    System.out.println((String) request.get("name"));
                    
                    System.out.println(taskService);
                    
                    task = taskService.save(task);
                    response.put("message", "Tarea creada");
                    response.put("task", task);
                    break;

                case "read":
                    // Leer todas las tareas
                    response.put("message", "Lista de tareas");
                    response.put("tasks", taskService.findAll());
                    break;

                case "update":
                    Long id = Long.valueOf((String) request.get("id"));
                    Task taskToUpdate = taskService.findById(id);
                    if (taskToUpdate != null) {
                        taskToUpdate.setName((String) request.get("name"));
                        taskToUpdate.setDescription((String) request.get("description"));
                        taskToUpdate.setCompleted(Boolean.parseBoolean((String) request.get("completed")));
                        taskService.save(taskToUpdate);
                        response.put("message", "Tarea actualizada");
                        response.put("task", taskToUpdate);
                    } else {
                        response.put("message", "Tarea no encontrada");
                    }
                    break;

                case "delete":
                    // Eliminar tarea
                    Long idToDelete = Long.valueOf((String) request.get("id"));
                    taskService.deleteById(idToDelete);
                    response.put("message", "Tarea eliminada");
                    break;

                default:
                    response.put("message", "Acción no reconocida");
                    break;
            }

            // Enviar respuesta al cliente
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));

        } catch (Exception e) {
            response.put("error", e.getMessage());
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
        }
    }
}