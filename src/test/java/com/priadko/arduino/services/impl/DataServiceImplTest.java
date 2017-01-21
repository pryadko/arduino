package com.priadko.arduino.services.impl;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.services.DataService;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("/dbunit/beginnerData.xml")
@Ignore
public class DataServiceImplTest {

    @Autowired
    DataService dataService;

    @Test
    @Ignore //it's so impossible situation
    public void createManyRecords() {
        //when
        for (int i = 0; i < 70_000; i++) {
            dataService.writeMeasure("Temperature = 23 *C");
        }
        //then
        Assert.assertEquals(70_000, dataService.getAllByType("Temperature").size());
    }

    @Test
    @ExpectedDatabase(
            value = "/dbunit/afterCreateMeasure.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void createMeasure() {
        //when
        dataService.writeMeasure("Temperature = 23 *C");
    }

    @Test
    @ExpectedDatabase("/dbunit/afterCreateTypeMeasure.xml")
    public void createTypeMeasureByName() {
        //when
        dataService.createTypeMeasure("Altitude");
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldntCreateIdenticalTypeMeasure() {
        //when
        dataService.createTypeMeasure("Temperature");
    }

    @Test()
    @ExpectedDatabase("/dbunit/beginnerData.xml")
    public void shouldntCreateUndefinedTypeTemperature() {
        //when
        dataService.writeMeasure("UndefinedType = 100 *C");
    }

    @Test()
    @ExpectedDatabase("/dbunit/beginnerData.xml")
    public void shouldntCreateWithEmptyInput() {
        //when
        dataService.writeMeasure(" ");
    }

    @Test()
    @DatabaseSetup("/dbunit/calculetedData.xml")
    public void shouldReturnLastMeasure() {
        //when
        Measure measure = dataService.getLastValuesByType("Temperature");

        Assert.assertEquals(542.05, measure.getValue(), 0);
        Assert.assertEquals(5, measure.getId());
    }

    @Test()
    @DatabaseSetup("/dbunit/calculetedData.xml")
    public void shouldReturnMeasureByPeriod() throws Exception{
        //given
        Calendar time1 = Calendar.getInstance();
        Calendar time2 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        time1.setTime(sdf.parse("2015-08-15 01:31:56.928"));
        time2.setTime(sdf.parse("2015-08-16 01:31:56.928"));

        //when
        List measures = dataService.getValuesByPeriod("Temperature", time1, time2);

        //then
        Assert.assertEquals(2, measures.size());
        Assert.assertEquals(155.05,((Measure) measures.get(0)).getValue(), 0);
        Assert.assertEquals(-159.05,((Measure) measures.get(1)).getValue(), 0);
    }

    @Test()
    @DatabaseSetup("/dbunit/calculetedData.xml")
    public void shouldReturnAvgMeasureByPeriod() throws Exception{
        //given
        Calendar time1 = Calendar.getInstance();
        Calendar time2 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        time1.setTime(sdf.parse("2015-08-15 01:31:56.928"));
        time2.setTime(sdf.parse("2015-08-16 01:31:56.929"));

        //when
        Double measure = dataService.getAvgValueByPeriod("Temperature", time1, time2);

        //then
        Assert.assertEquals(528.35, measure, 0);
    }

    @Test()
    @DatabaseSetup("/dbunit/calculetedData.xml")
    public void shouldReturnEception() throws Exception{
        //given
        Calendar time1 = Calendar.getInstance();
        Calendar time2 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        time1.setTime(sdf.parse("2015-08-18 01:31:56.928"));
        time2.setTime(sdf.parse("2015-08-18 01:31:56.929"));

        //when
        Double measure = dataService.getAvgValueByPeriod("Temperature", time1, time2);

        //then
        Assert.assertNull(measure);
    }
}