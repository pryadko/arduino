package com.priadko.arduino.services.impl;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("/dbunit/beginnerData.xml")
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

}