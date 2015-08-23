package com.priadko.arduino.dao.impl;

import com.priadko.arduino.dao.TypeMeasureDao;
import com.priadko.arduino.entry.TypeMeasure;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TypeMeasureDaoImpl implements TypeMeasureDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @Transactional
    public void create(TypeMeasure typeMeasure) {
        Session session = sessionFactory.getCurrentSession();
        session.save(typeMeasure);
    }

    @Override
    public void delete(TypeMeasure typeMeasure) {

    }

    @Override
    public void add(TypeMeasure typeMeasure) {

    }

    @Override
    public List<TypeMeasure> getAll() {
        return null;
    }

    @Override
    @Transactional
    public List<TypeMeasure> getTypeMeasureByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from TypeMeasure as p where p.name=:name");
        query.setParameter("name", name);
        List l = query.list();
        return l;
    }
}
