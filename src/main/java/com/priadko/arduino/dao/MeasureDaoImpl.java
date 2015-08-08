package com.priadko.arduino.dao;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;
import com.priadko.arduino.util.HibernateUtil;
import org.hibernate.Session;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class MeasureDaoImpl implements MeasureDao {
    @Override
    public void create(Measure measure) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        session.save(measure);
        session.getTransaction().commit();
        session.flush();
    }

    @Override
    public void delete(Measure measure) {

    }

    @Override
    public void add(Measure measure) {

    }

    @Override
    public List<Measure> getAll() {
        return null;
    }

    @Override
    public List<Measure> getMeasureByType(TypeMeasure typeMeasure) {
        return null;
    }
}
