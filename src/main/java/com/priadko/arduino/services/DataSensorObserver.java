package com.priadko.arduino.services;

import com.priadko.arduino.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Observable;
import java.util.Observer;

public class DataSensorObserver implements Observer {

    @Autowired
    private DataService dataService;

    @Override
    public void update(Observable o, Object arg) {
        dataService.writeMeasure(ParseUtil.parseMeasure((String) arg));
    }
}
