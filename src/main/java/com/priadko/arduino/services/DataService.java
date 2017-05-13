package com.priadko.arduino.services;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;
import com.priadko.arduino.entry.UnitOfMeasurement;

public interface DataService {

    Measure writeMeasure(Measure measure);

    TypeMeasure createTypeMeasure(String name, UnitOfMeasurement unitOfMeasurement);

    UnitOfMeasurement createUnitOfMeasurement(String name);
}
