package com.priadko;

import com.priadko.arduino.util.HibernateUtil;
import org.junit.Ignore;
import org.junit.Test;

public class AppTest {
    @Test
    public void createTenMillionRecord() throws Exception {
        HibernateUtil.getSessionFactory().openSession();
        App app = new App();

        for (int i = 0; i < 70_000; i++) {
            app.saveMeasureToDB(app.parseMeasure("Temperature = 34 *C"));
        }
    }
}