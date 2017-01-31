package com.priadko.arduino.web;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;
import com.priadko.arduino.services.ApplicationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observer;

@Service
public class WebObserver {

    @Value("${app.measure.type.allowed}")
    private List measureTypeAllowed;

    @Value("${app.measure.delta}")
    private Double delta;

    private Map<TypeMeasure, Measure> lastValues = new HashMap<>();

    @Autowired
    public WebObserver(ApplicationObserver applicationObserver, SimpMessagingTemplate webSocket) {
        applicationObserver.addObserver(
                getObserver(webSocket));
    }

    private Observer getObserver(SimpMessagingTemplate webSocket) {
        return (o, arg) -> {
            Measure measure = (Measure) arg;
            if (measureTypeAllowed(measure.getTypeMeasure()) && measureChangedEnough(measure)) {
                webSocket.convertAndSend("/measure", measure);
                lastValues.put(measure.getTypeMeasure(), measure);
            }
        };
    }

    private boolean measureChangedEnough(Measure measure) {
        Measure oldValue = lastValues.getOrDefault(measure.getTypeMeasure(), new Measure());

        return Math.abs(oldValue.getValue() - measure.getValue()) >= delta;
    }

    private boolean measureTypeAllowed(TypeMeasure typeMeasure) {
        return measureTypeAllowed.contains(typeMeasure.getName());
    }
}
