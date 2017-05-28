package com.priadko.arduino.services;

import com.google.gson.Gson;
import com.priadko.arduino.entry.Measure;
import javafx.util.Pair;
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

    public WebSocketMessage<?> createInitMessage(Map<Integer, Measure> stateMeasures) {
        return getTextMessage(ActionConstants.INIT_MEASURES, getInitValues(stateMeasures));
    }

    private List<Map<String,Object>> getInitValues(Map<Integer, Measure> stateMeasures) {
        List<Map<String,Object>> lists = new ArrayList<>();

        stateMeasures.forEach((integer, measure) -> {

            Map<String, Object> measureValue = getStringObjectHashMap(integer, measure);

            lists.add(measureValue);
        });

        return lists;
    }

    private Map<String, Object> getStringObjectHashMap(Integer integer, Measure measure) {
        HashMap<String, Object> measureValue = new HashMap<>();
        measureValue.put("id", integer);
        measureValue.put("name", measure.getTypeMeasure().getName());
        measureValue.put("value", measure.getValue());
        measureValue.put("unitOfMeas", measure.getTypeMeasure().getUnitOfMeasurement().getName());
        return measureValue;
    }

    public WebSocketMessage<?> createUpdateMessage(Pair<Integer, Measure> measure) {
        return getTextMessage(ActionConstants.UPDATE_MEASURE, getUpdatedPair(measure));
    }

    private Map<String, Object> getUpdatedPair(Pair<Integer, Measure> measure) {
        return getStringObjectHashMap(measure.getKey(), measure.getValue());
    }

    // TODO need some rework to remove hardcoded string value
    private TextMessage getTextMessage(String action, Object stateMeasures) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();

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
