package com.priadko.arduino.services;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;
import com.priadko.arduino.web.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class StateMeasures {
    private final SocketHandler socketHandler;

    private Map<String, Double> currentMeasures = new TreeMap<>();

    @Value("#{'${app.allowed.measure.types}'.split(',')}")
    private List<String> measureTypeAllowed;

    @Value("${app.measure.delta}")
    private Double delta;

    @Autowired
    public StateMeasures(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void updateMeasure(Measure measure) {
        if (measureTypeAllowed(measure.getTypeMeasure()) && measureChangedEnough(measure)) {
            updateMeasureState(measure);
            socketHandler.sendMessage(currentMeasures);
        }
    }

    private void updateMeasureState(Measure measure) {
        currentMeasures.put(measure.getTypeMeasure().getName(), measure.getValue());
    }

    private boolean measureChangedEnough(Measure measure) {
        Double oldValue = getCurrentValueByName(measure.getTypeMeasure().getName());

        return Math.abs(oldValue - measure.getValue()) >= delta;
    }

    private Double getCurrentValueByName(String name) {

        return currentMeasures.getOrDefault(name, Double.NEGATIVE_INFINITY);
    }

    private boolean measureTypeAllowed(TypeMeasure typeMeasure) {

        return measureTypeAllowed.contains(typeMeasure.getName());
    }
}
