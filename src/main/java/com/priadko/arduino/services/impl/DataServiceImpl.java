package com.priadko.arduino.services.impl;

import com.priadko.arduino.dao.MeasureDao;
import com.priadko.arduino.dao.TypeMeasureDao;
import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;
import com.priadko.arduino.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Observable;

@Service
public class DataServiceImpl extends Observable implements DataService {
    final private MeasureDao measureDao;
    final private TypeMeasureDao typeMeasureDao;

    @Autowired
    public DataServiceImpl(MeasureDao measureDao, TypeMeasureDao typeMeasureDao) {
        this.measureDao = measureDao;
        this.typeMeasureDao = typeMeasureDao;
    }

    @Override
    public Measure writeMeasure(Measure measure) {

        return measureDao.save(prepare(measure));
    }

    private Measure prepare(Measure measure) {
        String nameOfTypeMeasure = measure.getTypeMeasure().getName();
        TypeMeasure typeMeasure = typeMeasureDao.findByName(nameOfTypeMeasure);
        if (typeMeasure == null) {
            typeMeasure = createTypeMeasure(nameOfTypeMeasure);
        }

        measure.setTypeMeasure(typeMeasure);

        Calendar cal = Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        measure.setDateTime(new Timestamp(date.getTime()));

        return measure;
    }

    @Override
    public TypeMeasure createTypeMeasure(String name) {
        TypeMeasure typeMeasure = new TypeMeasure();
        typeMeasure.setName(name);
        return typeMeasureDao.save(typeMeasure);
    }

}
