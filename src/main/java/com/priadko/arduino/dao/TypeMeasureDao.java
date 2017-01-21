package com.priadko.arduino.dao;

import com.priadko.arduino.entry.TypeMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TypeMeasureDao extends CrudRepository<TypeMeasure, Long> {

    TypeMeasure findByName(String s);

}
