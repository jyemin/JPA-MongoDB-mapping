package org.hibernate.omm;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public abstract class AbstractMongodbIntegrationTests {
    private SessionFactory sessionFactory;

    public abstract List<Class<?>> getAnnotatedClasses();

    protected SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration cfg = new Configuration();
            getAnnotatedClasses().forEach(cfg::addAnnotatedClass);
            sessionFactory = cfg.buildSessionFactory();
        }
        return sessionFactory;
    }
}
