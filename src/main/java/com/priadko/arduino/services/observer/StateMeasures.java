package com.priadko.arduino.services.observer;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.services.config.Config;
import com.priadko.arduino.services.config.ConfigEntry;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

@Service
public class StateMeasures extends Observable {

    private Map<Integer, Measure> currentMeasures = new TreeMap<>();

    private final Config config;

    @Autowired
    public StateMeasures(Config config) {
        this.config = config;
    }

    public void updateMeasure(Measure measure) {
        String measureName = measure.getTypeMeasure().getName();
        ConfigEntry config = this.config.findConfigByName(measureName);

        if (config.isAllow() && config.isChangedEnough(getCurrentValueByName(measureName), measure.getValue())) {

            Pair<Integer, Measure> measurePair = new Pair<>(config.getId(), measure);
            updateMeasureState(measurePair);
            setChanged();
            notifyObservers(measurePair);
        }
    }

    private void updateMeasureState(Pair<Integer, Measure> measurePair) {
        currentMeasures.put(measurePair.getKey(), measurePair.getValue());
    }

    private Double getCurrentValueByName(String name) {

        return currentMeasures.values().stream()
                .filter(measure -> measure.getTypeMeasure().getName().equals(name))
                .map(Measure::getValue)
                .findAny()
                .orElse(Double.NEGATIVE_INFINITY);
    }

    public Map<Integer, Measure> getCurrentState() {

        return currentMeasures;
    }

}
