package com.priadko.arduino.dao;

import com.priadko.arduino.entry.Measure;

import java.util.List;

public interface MeasureDao {
    void create(Measure measure);

    void delete(Measure measure);

    List getAll();

    List getMeasureByType(String name);
}
