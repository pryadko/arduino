package com.priadko.arduino.dao;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;
import com.priadko.arduino.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class TypeMeasureDao {
    public void create(TypeMeasure typeMeasure) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        session.save(typeMeasure);
        session.getTransaction().commit();
        session.flush();
    }

    public void delete(TypeMeasure typeMeasure) {

    }

    public void add(TypeMeasure typeMeasure) {

    }

    public List<TypeMeasure> getAll() {
        return null;
    }

    public List<TypeMeasure> getTypeMeasureByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from TypeMeasure as p where p.name=:name");
        query.setParameter("name", name);
        List l = query.list();
        return l;
    }
}
