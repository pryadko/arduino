package com.priadko.arduino.dao.impl;

import com.priadko.arduino.dao.TypeMeasureDao;
import com.priadko.arduino.entry.TypeMeasure;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TypeMeasureDaoImpl implements TypeMeasureDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void create(TypeMeasure typeMeasure) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(typeMeasure);
        transaction.commit();
        session.close();
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
    public List<TypeMeasure> getTypeMeasureByName(String name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from TypeMeasure as p where p.name=:name");
        query.setParameter("name", name);
        List l = query.list();
        transaction.commit();
        session.close();
        return l;
    }
}
