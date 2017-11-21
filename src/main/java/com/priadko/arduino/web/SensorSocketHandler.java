package com.priadko.arduino.web;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.services.DataService;
import com.priadko.arduino.services.MeasureData;
import com.priadko.arduino.services.observer.StateMeasures;
import com.priadko.arduino.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Observer;

@Service
public class SensorSocketHandler extends TextWebSocketHandler {

    private final StateMeasures stateMeasures;
    private final DataService dataService;

    @Autowired
    public SensorSocketHandler(DataService dataService, StateMeasures stateMeasures) {
        this.dataService = dataService;
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
        System.out.println("");
//        sessions.add(session);
//
//        session.sendMessage(actionProcess.createInitMessage(stateMeasures.getCurrentState()));
    }


    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        Measure measure = ParseUtil.parseMeasure((String) message.getPayload());
        dataService.writeMeasure(measure);
        stateMeasures.updateMeasure(measure);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

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