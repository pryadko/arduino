package com.priadko.arduino.services;

import com.priadko.arduino.entry.TypeMeasure;

public interface DataService {

    void writeMeasure(String string);

    TypeMeasure createTypeMeasure(String name);

}
