package com.priadko.arduino.services.impl;

import com.priadko.arduino.dao.MeasureDao;
import com.priadko.arduino.dao.TypeMeasureDao;
import com.priadko.arduino.dao.UnitOfMeasurementDao;
import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;
import com.priadko.arduino.entry.UnitOfMeasurement;
import com.priadko.arduino.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DataServiceImpl implements DataService {
    final private MeasureDao measureDao;
    final private TypeMeasureDao typeMeasureDao;
    final private UnitOfMeasurementDao unitOfMeasurementDao;

    @Autowired
    public DataServiceImpl(MeasureDao measureDao, TypeMeasureDao typeMeasureDao, UnitOfMeasurementDao unitOfMeasurementDao) {
        this.measureDao = measureDao;
        this.typeMeasureDao = typeMeasureDao;
        this.unitOfMeasurementDao = unitOfMeasurementDao;
    }

    @Override
    public Measure writeMeasure(Measure measure) {

        return measureDao.save(prepare(measure));
    }

    private Measure prepare(Measure measure) {
        String nameOfTypeMeasure = measure.getTypeMeasure().getName();
        String nameUnitOfMeasurement = measure.getTypeMeasure().getUnitOfMeasurement().getName();
        TypeMeasure typeMeasure = typeMeasureDao.findByName(nameOfTypeMeasure);
        UnitOfMeasurement unitOfMeasurement = unitOfMeasurementDao.findByName(nameUnitOfMeasurement);

        if (unitOfMeasurement == null) {
            unitOfMeasurement = createUnitOfMeasurement(nameUnitOfMeasurement);
        }

        if (typeMeasure == null) {
            typeMeasure = createTypeMeasure(nameOfTypeMeasure);
        }

        measure.setTypeMeasure(typeMeasure);
        typeMeasure.setUnitOfMeasurement(unitOfMeasurement);

        return measure;
    }

    private UnitOfMeasurement createUnitOfMeasurement(String nameUnitOfMeasurement) {
        UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
        unitOfMeasurement.setName(nameUnitOfMeasurement);

        return unitOfMeasurementDao.save(unitOfMeasurement);
    }

    @Override
    public TypeMeasure createTypeMeasure(String name) {
        TypeMeasure typeMeasure = new TypeMeasure();
        typeMeasure.setName(name);

        return typeMeasureDao.save(typeMeasure);
    }

}
