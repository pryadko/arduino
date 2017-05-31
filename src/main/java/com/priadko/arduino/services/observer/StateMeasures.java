package com.priadko.arduino.services.observer;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.services.MeasureData;
import com.priadko.arduino.services.config.Config;
import com.priadko.arduino.services.config.ConfigEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

@Service
public class StateMeasures extends Observable {

    private Map<String, MeasureData> currentMeasures = new TreeMap<>();

    @Autowired
    public StateMeasures(Config config, Fabrica fabrica) {
        config.getList().stream()
                .filter(ConfigEntry::isAllow)
                .forEach(configEntry -> currentMeasures.put(configEntry.getName(), fabrica.create(configEntry)));
    }

    public void updateMeasure(Measure measure) {
        String measureName = measure.getTypeMeasure().getName();
        double newValue = measure.getValue();

        MeasureData measureData = currentMeasures.get(measureName);
        if (measureData == null) {
            return;
        }

        if (measureData.isChangedEnough(newValue)) {
            setChanged();
            measureData.updateValue(newValue);
            notifyObservers(measureData);
        }
    }

    public Collection<MeasureData> getCurrentState() {

        return currentMeasures.values();
    }

}
