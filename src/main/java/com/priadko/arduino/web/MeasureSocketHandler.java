package com.priadko.arduino.web;

import com.google.gson.Gson;
import com.priadko.arduino.services.ActionProcess;
import com.priadko.arduino.services.MeasureData;
import com.priadko.arduino.services.observer.StateMeasures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Service
public class MeasureSocketHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessions = new ArrayList<>();

    private final ActionProcess actionProcess;

    private final StateMeasures stateMeasures;

    @Autowired
    public MeasureSocketHandler(ActionProcess actionProcess, StateMeasures stateMeasures) {
        this.actionProcess = actionProcess;
        this.stateMeasures = stateMeasures;
        stateMeasures.addObserver(getObserver());
    }

    private Observer getObserver() {
        return (o, value) ->
        {
            MeasureData measureData = (MeasureData) value;
            sendMessage(measureData);
        };
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        sessions.add(session);

        session.sendMessage(actionProcess.createInitMessage(stateMeasures.getCurrentState()));
    }

    private void sendMessage(MeasureData measureData) {
        sessions.removeIf(webSocketSession -> !webSocketSession.isOpen());

        sessions.forEach(webSocketSession -> {
                    try {
                        webSocketSession.sendMessage(actionProcess.createUpdateMessage(measureData));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

}