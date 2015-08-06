package com.priadko.arduino.dao;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface MeasureDao {
    void create(Measure measure);

    void delete(Measure measure);

    void add(Measure measure);

    List<Measure> getAll();

    List<Measure> getMeasureByType(TypeMeasure typeMeasure);
}
