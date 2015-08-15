package com.priadko.arduino.services.impl;

import com.priadko.arduino.dao.MeasureDao;
import com.priadko.arduino.dao.TypeMeasureDao;
import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;
import com.priadko.arduino.services.DataService;
import com.priadko.arduino.util.ParseMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class DataServiceImpl implements DataService {

    @Autowired
    MeasureDao measureDao;

    @Autowired
    TypeMeasureDao typeMeasureDao;

    @Override
    public void writeMeasure(String string) {
        Measure measure = ParseMeasure.parseMeasure(string);

        Calendar cal = Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());



        if (measure != null) {
            String nameOfTypeMeasure = measure.getTypeMeasure().getName();
            List typeMeasureByName = typeMeasureDao.getTypeMeasureByName(nameOfTypeMeasure);
            if (typeMeasureByName.isEmpty()) {
                typeMeasureDao.create(measure.getTypeMeasure());
                typeMeasureByName = typeMeasureDao.getTypeMeasureByName(nameOfTypeMeasure);
            }
            measure.setTypeMeasure((TypeMeasure) typeMeasureByName.get(0));
            measure.setDateTime(new Timestamp(date.getTime()));
            create(measure);
        }

    }

    @Override
    public void create(Measure measure) {
        measureDao.create(measure);
    }

    @Override
    public void delete(Measure measure) {
        measureDao.delete(measure);
    }

    @Override
    public List getAll() {
        return measureDao.getAll();
    }

    @Override
    public List<Measure> getMeasureByType(TypeMeasure typeMeasure) {
        return measureDao.getMeasureByType(typeMeasure);
    }
}
