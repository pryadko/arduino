package com.priadko.arduino.web;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SocketHandler extends TextWebSocketHandler {

    private Map<String, Double> stateMeasures;

    private List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        sessions.add(session);

        sendMessage(stateMeasures, session);
    }

    public void sendMessage(Map<String, Double> stateMeasures) {
        this.stateMeasures = stateMeasures;
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                sendMessage(stateMeasures, webSocketSession);
            }
        }
    }

    // TODO need some rework to remove hardcoded string value
    private void sendMessage(Map<String, Double> stateMeasures, WebSocketSession webSocketSession) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();

        Map<String, Object> type = new HashMap<>();
        type.put("type", "RECEIVE_VALUE");
        type.put("payload", new Gson().toJson(stateMeasures));

        objectObjectHashMap.put("data", type);
        objectObjectHashMap.put("type", "channel");
        objectObjectHashMap.put("channel", "redux");
        String s = new Gson().toJson(objectObjectHashMap);
        try {
            webSocketSession.sendMessage(new TextMessage(s));
        } catch (IOException e) {
            //
        }
    }


}