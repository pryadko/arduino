package com.priadko.arduino.dao;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Calendar;
import java.util.List;

public interface MeasureDao extends CrudRepository<Measure, Long> {

}
