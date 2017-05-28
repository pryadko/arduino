package com.priadko.arduino.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class SocketConfig implements WebSocketConfigurer {

    private final MeasureSocketHandler measureSocketHandler;

    @Autowired
    public SocketConfig(MeasureSocketHandler measureSocketHandler) {
        this.measureSocketHandler = measureSocketHandler;
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(measureSocketHandler, "/gs-guide-websocket/")
                .setAllowedOrigins("*")
                .withSockJS();
    }


}
