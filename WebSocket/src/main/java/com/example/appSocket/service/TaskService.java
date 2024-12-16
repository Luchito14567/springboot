package com.example.appSocket.service;

import com.example.appSocket.model.Task;
import com.example.appSocket.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Crear o actualizar una tarea
    public Task save(Task task) {
    	System.out.println("SAVING...");
    	
        return taskRepository.save(task);
    }

    // Leer todas las tareas
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    // Buscar una tarea por ID
    public Task findById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.orElse(null);
    }

    // Eliminar una tarea por ID
    public void deleteById(Long id) {
    	System.out.print("DELETE... " + id);
        taskRepository.deleteById(id);
    }

	public Task createTask(Task task) {
		return taskRepository.save(task);
	
	}
}