package com.priadko.arduino.services;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;

public interface DataService {

    Measure writeMeasure(Measure measure);

    TypeMeasure createTypeMeasure(String name);

}
