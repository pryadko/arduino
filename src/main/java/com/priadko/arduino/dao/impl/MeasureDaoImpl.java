package com.priadko.arduino.dao.impl;

import com.priadko.arduino.dao.MeasureDao;
import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MeasureDaoImpl implements MeasureDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @Transactional
    public void create(Measure measure) {
        Session session = sessionFactory.getCurrentSession();
        session.save(measure);
    }

    @Override
    public void delete(Measure measure) {

    }

    @Override
    @Transactional
    public List getAll() {
        Session session = sessionFactory.getCurrentSession();
        List measureList = session.createCriteria(Measure.class).list();
        return measureList;
    }

    @Override
    public List<Measure> getMeasureByType(TypeMeasure typeMeasure) {
        return null;
    }
}
