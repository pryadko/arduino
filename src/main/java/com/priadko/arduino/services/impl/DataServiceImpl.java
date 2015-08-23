package com.priadko.arduino.services.impl;

import com.priadko.arduino.dao.MeasureDao;
import com.priadko.arduino.dao.TypeMeasureDao;
import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;
import com.priadko.arduino.services.DataService;
import com.priadko.arduino.util.ParseMeasure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class DataServiceImpl implements DataService {

    private static final Logger LOG = LoggerFactory.getLogger(DataServiceImpl.class);

    @Autowired
    MeasureDao measureDao;

    @Autowired
    TypeMeasureDao typeMeasureDao;

    @Override
    public void writeMeasure(String string) {
        Measure measure = ParseMeasure.parseMeasure(string);

        if (measure == null) {return;}

        String nameOfTypeMeasure = measure.getTypeMeasure().getName();
        List typeMeasureByName = typeMeasureDao.getTypeMeasureByName(nameOfTypeMeasure);

        if (typeMeasureByName.isEmpty()) {
            LOG.warn("Not found type of measure = {}, value will be skipped.", nameOfTypeMeasure);
            return;
        }

        Calendar cal = Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        measure.setTypeMeasure((TypeMeasure) typeMeasureByName.get(0));
        measure.setDateTime(new Timestamp(date.getTime()));
        create(measure);
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
