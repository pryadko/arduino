package com.priadko.arduino.services;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.hardware.SerialPortDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Observable;
import java.util.Observer;

@Service
public class ApplicationObserver extends Observable {

    @Autowired
    public ApplicationObserver(SerialPortDataSource serialPort, DataService dataService) {
        serialPort.addObserver(getObserver(dataService));
    }

    private Observer getObserver(DataService dataService) {
        return (o, arg) ->
        {
            Measure measure = dataService.writeMeasure((String) arg);

            if(measure == null) return;
            setChanged();
            notifyObservers(measure);
        };
    }

}
