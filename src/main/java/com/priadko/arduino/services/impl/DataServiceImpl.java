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
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    private static final Logger LOG = LoggerFactory.getLogger(DataServiceImpl.class);

    @Autowired
    MeasureDao measureDao;

    @Autowired
    TypeMeasureDao typeMeasureDao;

    @Override
    public void writeMeasure(String string) {
        Measure measure = ParseMeasure.parseMeasure(string);

        if (measure == null) {
            return;
        }

        String nameOfTypeMeasure = measure.getTypeMeasure().getName();
        TypeMeasure typeMeasureByName = typeMeasureDao.getTypeMeasureByName(nameOfTypeMeasure);

        if (typeMeasureByName == null) {
            LOG.warn("Not found type of measure = {}, value will be skipped.", nameOfTypeMeasure);
            return;
        }

        Calendar cal = Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        measure.setTypeMeasure(typeMeasureByName);
        measure.setDateTime(new Timestamp(date.getTime()));
        measureDao.create(measure);
    }

    @Override
    public List getAllByType(String name) {
        return measureDao.getMeasureByType(name);
    }

    @Override
    public void createTypeMeasure(String name) {
        TypeMeasure typeMeasure = new TypeMeasure();
        typeMeasure.setName(name);
        typeMeasureDao.create(typeMeasure);
    }

    @Override
    public List getAllTypeMeasure() {
        return typeMeasureDao.getAll();
    }

    @Override
    public Measure getLastValuesByType(String typeName) {
        return measureDao.getLastValuesByType(typeName);
    }

    @Override
    public List getValuesByPeriod(String typeName, Calendar time1, Calendar time2) {
        return measureDao.getValuesByPeriod(typeName, time1, time2);
    }

    @Override
    public Double getAvgValueByPeriod(String typeName, Calendar time1, Calendar time2) {
        return measureDao.getAvgValueByPeriod(typeName, time1, time2);
    }

    @Override
    public List<Double> getCountAvgValueForPeriod(String typeName, int count, Calendar time1, Calendar time2) {
        List<Double> values = new ArrayList<>(count);

        long timeLine = (time2.getTimeInMillis() - time1.getTimeInMillis()) / count;

        for (int i = 0; i < count; i++) {
            Calendar t1 = Calendar.getInstance();
            t1.setTimeInMillis(timeLine * i);
            Calendar t2 = Calendar.getInstance();
            t2.setTimeInMillis(timeLine * (i + 1));

            values.add(i, getAvgValueByPeriod(typeName, t1, t2));
        }

        return values;
    }
}
