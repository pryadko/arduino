package com.priadko.arduino.services;

import com.priadko.arduino.hardware.SerialPortDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSensorObserver {

    @Autowired
    public DataSensorObserver(SerialPortDataSource serialPort, DataService dataService) {
        serialPort.addObserver(
                (o, arg) ->
                        dataService.writeMeasure((String) arg));
    }
}
