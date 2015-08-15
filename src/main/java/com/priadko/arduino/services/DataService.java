package com.priadko.arduino.services;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DataService {

    void writeMeasure(String string);

    void create(Measure measure);

    void delete(Measure measure);

    List getAll();

    List<Measure> getMeasureByType(TypeMeasure typeMeasure);
}
