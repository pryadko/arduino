package com.priadko.arduino.dao;

import com.priadko.arduino.entry.TypeMeasure;

import java.util.List;

public interface TypeMeasureDao {
    void create(TypeMeasure typeMeasure);

    void delete(TypeMeasure typeMeasure);

    void add(TypeMeasure typeMeasure);

    List<TypeMeasure> getAll();

    List getTypeMeasureByName(String name);

}
