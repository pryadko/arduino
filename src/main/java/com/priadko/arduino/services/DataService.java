package com.priadko.arduino.services;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;

public interface DataService {

    Measure writeMeasure(String string);

    TypeMeasure createTypeMeasure(String name);

}
