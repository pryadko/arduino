package com.priadko.arduino.dao.impl;

import com.priadko.arduino.dao.MeasureDao;
import com.priadko.arduino.entry.Measure;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
        // todo need implemented
    }

    @Override
    @Transactional
    public List getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Measure.class).list();
    }

    @Override
    @Transactional
    public List getMeasureByType(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Measure.class, "measure");
        c.createAlias("measure.typeMeasure", "typeMeasure");
        c.add(Restrictions.eq("typeMeasure.name", name));
        return c.list();
    }
}
