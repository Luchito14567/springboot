package com.example.appSocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.appSocket.controller.CrudWebSocketHandler;



@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final CrudWebSocketHandler crudWebSocketHandler;

    @Autowired
    public WebSocketConfig(CrudWebSocketHandler crudWebSocketHandler) {
        this.crudWebSocketHandler = crudWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(crudWebSocketHandler, "/ws").setAllowedOrigins("*");
    }
}