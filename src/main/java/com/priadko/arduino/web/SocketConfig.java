package com.priadko.arduino.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class SocketConfig implements WebSocketConfigurer {

    private final SocketHandler socketHandler;

    @Autowired
    public SocketConfig(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, "/gs-guide-websocket/")
                .setAllowedOrigins("*")
                .withSockJS();
    }


}
