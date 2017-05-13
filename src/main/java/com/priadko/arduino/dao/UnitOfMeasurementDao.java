package com.priadko.arduino.dao;

import com.priadko.arduino.entry.UnitOfMeasurement;
import org.springframework.data.repository.CrudRepository;

public interface UnitOfMeasurementDao extends CrudRepository<UnitOfMeasurement, Long> {

    UnitOfMeasurement findByName(String name);

}
