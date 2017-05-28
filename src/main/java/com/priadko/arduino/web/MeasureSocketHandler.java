package com.priadko.arduino.web;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.services.ActionProcess;
import com.priadko.arduino.services.observer.StateMeasures;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

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
            Pair<Integer, Measure> measure = (Pair<Integer, Measure>) value;
            sendMessage(measure);
        };
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        sessions.add(session);

        session.sendMessage(actionProcess.createInitMessage(stateMeasures.getCurrentState()));
    }

    private void sendMessage(Pair<Integer, Measure> measure) {
        sessions.removeIf(webSocketSession -> !webSocketSession.isOpen());

        sessions.forEach(webSocketSession -> {
                    try {
                        webSocketSession.sendMessage(actionProcess.createUpdateMessage(measure));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

}