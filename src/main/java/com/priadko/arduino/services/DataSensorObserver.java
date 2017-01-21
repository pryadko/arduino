package com.priadko.arduino.services;

import com.priadko.arduino.hardware.SerialPortListener;
import com.priadko.arduino.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSensorObserver {

    @Autowired
    public DataSensorObserver(SerialPortListener serialPort, DataService dataService) {
        serialPort.addObserver(
                (o, arg) ->
                        dataService.writeMeasure(ParseUtil.parseMeasure((String) arg)));
    }
}
