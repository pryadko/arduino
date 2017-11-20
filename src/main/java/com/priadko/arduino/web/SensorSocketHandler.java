package com.priadko.arduino.web;

import com.priadko.arduino.services.ActionProcess;
import com.priadko.arduino.services.MeasureData;
import com.priadko.arduino.services.observer.StateMeasures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

@Service
public class SensorSocketHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessions = new ArrayList<>();

    private final ActionProcess actionProcess;

    private final StateMeasures stateMeasures;

    @Autowired
    public SensorSocketHandler(ActionProcess actionProcess, StateMeasures stateMeasures) {
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
//        super.afterConnectionEstablished(session);
     //   System.out.println("");
//        sessions.add(session);
//
//        session.sendMessage(actionProcess.createInitMessage(stateMeasures.getCurrentState()));
    }


    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
     //   System.out.println("");
      //  super.handleMessage(session, message);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    //    System.out.println("");
       super.handleTextMessage(session, message);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
      //  super.handleBinaryMessage(session, message);
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
    }

    private void sendMessage(MeasureData measureData) {

    }

}