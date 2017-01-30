package com.priadko.arduino.web;

import com.priadko.arduino.services.ApplicationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebObserver {
    @Autowired
    public WebObserver(ApplicationObserver applicationObserver, SimpMessagingTemplate webSocket) {
        applicationObserver.addObserver(
                (o, arg) -> webSocket.convertAndSend("/measure", arg));
    }
}
