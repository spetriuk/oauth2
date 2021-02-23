package com.petriuk.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractHibernateDao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    protected void createUpdate(T t) {
        getSession().persist(t);
    }

    protected void deleteEntity(T t) {
        getSession().remove(t);
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
