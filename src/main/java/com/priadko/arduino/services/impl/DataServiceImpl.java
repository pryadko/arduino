package com.priadko.arduino.services.impl;

import com.priadko.arduino.dao.MeasureDao;
import com.priadko.arduino.dao.TypeMeasureDao;
import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;
import com.priadko.arduino.services.DataService;
import com.priadko.arduino.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class DataServiceImpl implements DataService {
    final private MeasureDao measureDao;
    final private TypeMeasureDao typeMeasureDao;

    @Autowired
    public DataServiceImpl(MeasureDao measureDao, TypeMeasureDao typeMeasureDao) {
        this.measureDao = measureDao;
        this.typeMeasureDao = typeMeasureDao;
    }

    @Override
    public void writeMeasure(String string) {
        Measure measure = ParseUtil.parseMeasure(string);

        if (measure == null) {
            return;
        }

        String nameOfTypeMeasure = measure.getTypeMeasure().getName();
        TypeMeasure typeMeasure = typeMeasureDao.findByName(nameOfTypeMeasure);
        if (typeMeasure == null) {
            typeMeasure = createTypeMeasure(nameOfTypeMeasure);
        }

        measure.setTypeMeasure(typeMeasure);

        Calendar cal = Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        measure.setDateTime(new Timestamp(date.getTime()));

        measureDao.save(measure);
    }

    @Override
    public TypeMeasure createTypeMeasure(String name) {
        TypeMeasure typeMeasure = new TypeMeasure();
        typeMeasure.setName(name);
        return typeMeasureDao.save(typeMeasure);
    }

}
