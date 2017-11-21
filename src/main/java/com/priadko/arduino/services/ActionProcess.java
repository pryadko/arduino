package com.priadko.arduino.services;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;

import java.util.*;

@Service
public class ActionProcess {

    private static final String TYPE = "type";
    private static final String PAYLOAD = "payload";
    private static final String DATA = "data";
    private static final String CHANNEL = "channel";
    private static final String REDUX = "redux";

    public WebSocketMessage<?> createInitMessage(Collection<MeasureData> measureDatas) {
        return getTextMessage(ActionConstants.INIT_MEASURES, getInitValues(measureDatas));
    }

    private List<Map<String, Object>> getInitValues(Collection<MeasureData> measureDatas) {
        List<Map<String, Object>> lists = new ArrayList<>();

        measureDatas.forEach(measureData -> lists.add(measureData.getMessageForInit()));

        lists.sort(Comparator.comparingInt(o -> (Integer) o.get("id")));

        return lists;
    }

    public WebSocketMessage<?> createUpdateMessage(MeasureData measureData) {
        return getTextMessage(ActionConstants.UPDATE_MEASURE, measureData.getUpdate());
    }

    // TODO need some rework to remove hardcoded string value
    private TextMessage getTextMessage(String action, Object stateMeasures) {
        Map<Object, Object> objectObjectHashMap = new HashMap<>();

        Map<String, Object> type = new HashMap<>();
        type.put(TYPE, action);
        type.put(PAYLOAD, new Gson().toJson(stateMeasures));

        objectObjectHashMap.put(DATA, type);
        objectObjectHashMap.put(TYPE, CHANNEL);
        objectObjectHashMap.put(CHANNEL, REDUX);
        String s = new Gson().toJson(objectObjectHashMap);
        return new TextMessage(s);
    }
}
