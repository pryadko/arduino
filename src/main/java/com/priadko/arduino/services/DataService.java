package com.priadko.arduino.services;

import com.priadko.arduino.entry.Measure;

import java.util.Calendar;
import java.util.List;

public interface DataService {

    void writeMeasure(String string);

    void createTypeMeasure(String name);

    List getAllByType(String name);

    List getAllTypeMeasure();

    Measure getLastValuesByType(String name);

    List getValuesByPeriod(String name, Calendar time1, Calendar time2);

    Double getAvgValueByPeriod(String name, Calendar time1, Calendar time2);

    List<Double> getCountAvgValueForPeriod(String typeName, int count, Calendar time1, Calendar time2);
}
