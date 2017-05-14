package com.priadko.arduino.services;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.services.config.Config;
import com.priadko.arduino.services.config.ConfigEntry;
import com.priadko.arduino.web.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class StateMeasures {
    private final SocketHandler socketHandler;

    private Map<String, Double> currentMeasures = new TreeMap<>();

    private final Config config;

    @Autowired
    public StateMeasures(SocketHandler socketHandler, Config config) {
        this.socketHandler = socketHandler;
        this.config = config;
    }

    public void updateMeasure(Measure measure) {
        String measureName = measure.getTypeMeasure().getName();
        ConfigEntry config = this.config.findConfigByName(measureName);

        if (config.isAllow() && config.isChangedEnough(getCurrentValueByName(measureName), measure.getValue())) {
            updateMeasureState(measure);
            socketHandler.sendMessage(currentMeasures);
        }
    }

    private void updateMeasureState(Measure measure) {
        currentMeasures.put(measure.getTypeMeasure().getName(), measure.getValue());
    }

    private Double getCurrentValueByName(String name) {

        return currentMeasures.getOrDefault(name, Double.NEGATIVE_INFINITY);
    }

}
