package com.priadko.arduino.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;


@Configuration
@EnableWebSocket
public class SocketConfig implements WebSocketConfigurer {

    private final MeasureSocketHandler measureSocketHandler;
    private final SensorSocketHandler sensorSocketHandler;

    @Autowired
    public SocketConfig(MeasureSocketHandler measureSocketHandler, SensorSocketHandler sensorSocketHandler) {
        this.measureSocketHandler = measureSocketHandler;
        this.sensorSocketHandler = sensorSocketHandler;
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(measureSocketHandler, "/gs-guide-websocket/")
                .setAllowedOrigins("*")
                .withSockJS();

        RequestUpgradeStrategy upgradeStrategy = new TomcatRequestUpgradeStrategy();
      /*  registry.addEndpoint("/hello")
                .withSockJS();*/

        registry.addHandler(sensorSocketHandler, "/in")
                .setAllowedOrigins("*")
                            .withSockJS();
    }


}
