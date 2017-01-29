package com.priadko.arduino.web;

import com.priadko.arduino.hardware.SerialPortDataSource;
import org.springframework.stereotype.Service;

import java.util.Observable;

@Service
public class WebObserverBridge extends Observable {
    public WebObserverBridge(SerialPortDataSource source) {
        source.addObserver((o, arg) -> {

            setChanged();
            notifyObservers(arg);
        });
    }

}
