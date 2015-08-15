package com.priadko.arduino.dao;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;

import java.util.List;

public interface MeasureDao {
    void create(Measure measure);

    void delete(Measure measure);

    List getAll();

    List<Measure> getMeasureByType(TypeMeasure typeMeasure);
}
