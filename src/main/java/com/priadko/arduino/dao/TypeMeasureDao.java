package com.priadko.arduino.dao;

import com.priadko.arduino.entry.TypeMeasure;

import java.util.List;

public interface TypeMeasureDao {
    void create(TypeMeasure typeMeasure);

    void delete(TypeMeasure typeMeasure);

    List getAll();

    TypeMeasure getTypeMeasureByName(String name);

}
