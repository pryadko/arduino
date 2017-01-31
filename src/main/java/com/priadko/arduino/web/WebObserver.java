package com.priadko.arduino.web;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;
import com.priadko.arduino.entry.UiConfig;
import com.priadko.arduino.services.ApplicationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Observer;

@Service
public class WebObserver {

    @Value("${app.measure.type.allowed}")
    private List measureTypeAllowed;

    @Value("${app.measure.delta}")
    private Double delta;

    private final UiConfig uiConfig;

    @Autowired
    public WebObserver(ApplicationObserver applicationObserver, SimpMessagingTemplate webSocket, UiConfig uiConfig) {
        applicationObserver.addObserver(
                getObserver(webSocket));
        this.uiConfig = uiConfig;
    }

    private Observer getObserver(SimpMessagingTemplate webSocket) {
        return (o, arg) -> {
            Measure measure = (Measure) arg;
            if (measureTypeAllowed(measure.getTypeMeasure()) && measureChangedEnough(measure)) {
                webSocket.convertAndSend("/measure", measure);
                uiConfig.putInitialValue(measure.getTypeMeasure().getName(), measure.getValue());
            }
        };
    }

    private boolean measureChangedEnough(Measure measure) {
        Double oldValue = uiConfig.getInitialValue(measure.getTypeMeasure().getName());

        return Math.abs(oldValue - measure.getValue()) >= delta;
    }

    private boolean measureTypeAllowed(TypeMeasure typeMeasure) {
        return measureTypeAllowed.contains(typeMeasure.getName());
    }
}
