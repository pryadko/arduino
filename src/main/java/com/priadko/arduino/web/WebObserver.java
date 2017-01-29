package com.priadko.arduino.web;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebObserver {
    public WebObserver(WebObserverBridge webObserverBridge, SimpMessagingTemplate webSocket) {
        webObserverBridge.addObserver(
                (o, arg) -> webSocket.convertAndSend("/topic/moves", arg));
    }
}
