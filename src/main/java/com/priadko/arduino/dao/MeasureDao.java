package com.priadko.arduino.dao;

import com.priadko.arduino.entry.Measure;

import java.util.Calendar;
import java.util.List;

public interface MeasureDao {
    void create(Measure measure);

    void delete(Measure measure);

    List getAll();

    List getMeasureByType(String name);

    Measure getLastValuesByType(String name);

    List getValuesByPeriod(String name, Calendar time1, Calendar time2);

    Double getAvgValueByPeriod(String name, Calendar time1, Calendar time2);
}
