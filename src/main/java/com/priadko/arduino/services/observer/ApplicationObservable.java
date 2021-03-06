package com.priadko.arduino.services.observer;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Observable;
import java.util.Observer;

@Service
public class ApplicationObservable extends Observable {

    @Autowired
    public ApplicationObservable(DataService dataService, StateMeasures stateMeasures) {

    }

    private Observer createObserver(DataService dataService, StateMeasures stateMeasures) {
        return (o, value) ->
        {
            Measure measure = (Measure) value;
            dataService.writeMeasure(measure);
            stateMeasures.updateMeasure(measure);
        };
    }

}
