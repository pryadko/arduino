package com.priadko.arduino.util;

import com.priadko.arduino.entry.Measure;
import org.junit.Assert;
import org.junit.Test;

public class ParseMeasureTest {

    @Test
    public void testParseMeasure() throws Exception {
        //given
        String inputString = "Temperature = 12.5 *C";
        //when
        Measure actualMeasure = ParseMeasure.parseMeasure(inputString);
        //then
        assert actualMeasure != null;
        Assert.assertEquals(12.5d, actualMeasure.getValue(), 0.0);
        Assert.assertEquals("Temperature", actualMeasure.getTypeMeasure().getName());
    }

    @Test
    public void shouldReturnNullIfInputStringIsEmpty() throws Exception {
        //given
        String inputString = "  ";
        //when
        Measure actualMeasure = ParseMeasure.parseMeasure(inputString);
        //then
        Assert.assertNull(actualMeasure);
    }

    @Test(expected = NullPointerException.class)
    public void shouldReturnExceptionIfInputStringIsNull() throws Exception {
        //when
        ParseMeasure.parseMeasure(null);
    }

    @Test
    public void testParseMeasureDoubleConvert() throws Exception {
        //given
        String inputString = "Temperature= 12 *C";
        //when
        Measure actualMeasure = ParseMeasure.parseMeasure(inputString);
        //then
        assert actualMeasure != null;
        Assert.assertEquals(12d, actualMeasure.getValue(), 0.0);
    }

    @Test
    public void testParseMeasureDoubleConvert2() throws Exception {
        //given
        String inputString = "Temperature =12.00 *C";
        //when
        Measure actualMeasure = ParseMeasure.parseMeasure(inputString);
        //then
        assert actualMeasure != null;
        Assert.assertEquals(12d, actualMeasure.getValue(), 0.0);
    }

    @Test
    public void testParseMeasureDoubleConvert3() throws Exception {
        //given
        String inputString = "Te mper at ure = 12.001 *C";
        //when
        Measure actualMeasure = ParseMeasure.parseMeasure(inputString);
        //then
        assert actualMeasure != null;
        Assert.assertEquals(12.001d, actualMeasure.getValue(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseMeasureUnsupportedFormat() throws Exception {
        //given
        String inputString = "Temperature = 12.Fds001 *C";
        //when
        Measure actualMeasure = ParseMeasure.parseMeasure(inputString);
        //then
        assert actualMeasure != null;
        Assert.assertEquals(12.001d, actualMeasure.getValue(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseMeasureUnsupportedFormat2() throws Exception {
        //given
        String inputString = "Temperature = 12.001*C";
        //when
        Measure actualMeasure = ParseMeasure.parseMeasure(inputString);
        //then
        assert actualMeasure != null;
        Assert.assertEquals(12.001d, actualMeasure.getValue(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseMeasureUnsupportedFormat3() throws Exception {
        //given
        String inputString = "Temperature=12.001*C";
        //when
        Measure actualMeasure = ParseMeasure.parseMeasure(inputString);
        //then
        assert actualMeasure != null;
        Assert.assertEquals(12.001d, actualMeasure.getValue(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseMeasureUnsupportedFormat4() throws Exception {
        //given
        String inputString = "Temperature 12.001*C";
        //when
        Measure actualMeasure = ParseMeasure.parseMeasure(inputString);
        //then
        assert actualMeasure != null;
        Assert.assertEquals(12.001d, actualMeasure.getValue(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseMeasureUnsupportedFormat5() throws Exception {
        //given
        String inputString = "Temperature = 51 = 12.001*C";
        //when
        Measure actualMeasure = ParseMeasure.parseMeasure(inputString);
        //then
        assert actualMeasure != null;
        Assert.assertEquals(12.001d, actualMeasure.getValue(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseMeasureUnsupportedFormat6() throws Exception {
        //given
        String inputString = "Temperature = 12.001 * C";
        //when
        Measure actualMeasure = ParseMeasure.parseMeasure(inputString);
        //then
        assert actualMeasure != null;
        Assert.assertEquals(12.001d, actualMeasure.getValue(), 0.0);
    }
}