package com.example.appSocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    @MessageMapping("/update")
    @SendTo("/topic/updates")
    public String sendUpdate(String message) {
        return message;
    }
}