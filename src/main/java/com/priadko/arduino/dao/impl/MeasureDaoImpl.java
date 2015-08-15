package com.priadko.arduino.dao.impl;

import com.priadko.arduino.dao.MeasureDao;
import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MeasureDaoImpl implements MeasureDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void create(Measure measure) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(measure);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Measure measure) {

    }

    @Override
    public List getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List measureList = session.createCriteria(Measure.class).list();
        transaction.commit();
        session.close();
        return measureList;
    }

    @Override
    public List<Measure> getMeasureByType(TypeMeasure typeMeasure) {
        return null;
    }
}
