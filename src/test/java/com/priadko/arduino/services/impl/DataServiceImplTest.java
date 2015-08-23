package com.priadko.arduino.services.impl;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.services.DataService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
@Transactional
public class DataServiceImplTest {

    @Autowired
    DataService dataService;

    @Test
    @Ignore
    public void createManyRecords() {
        for (int i = 0; i < 100_000; i++) {
            Measure measure = new Measure();
            dataService.create(measure);
        }
    }

}