package com.priadko.arduino.dao;

import com.priadko.arduino.dao.impl.MeasureDaoImpl;
import com.priadko.arduino.entry.Measure;
import org.junit.Ignore;
import org.junit.Test;

public class MeasureDaoImplTest {

    @Test
    @Ignore
    public void createTenMillionRecord() throws Exception {
        MeasureDao measureDao = new MeasureDaoImpl();
        for (int i = 0; i < 100_000; i++) {
            Measure measure = new Measure();
            measureDao.create(measure);
        }
    }

}