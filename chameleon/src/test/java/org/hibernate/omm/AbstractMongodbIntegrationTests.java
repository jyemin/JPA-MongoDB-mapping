package org.hibernate.omm;

import com.mongodb.client.MongoDatabase;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.omm.jdbc.MongodbConnectionProvider;

import java.util.List;

public abstract class AbstractMongodbIntegrationTests {
    private SessionFactory sessionFactory;
    private MongoDatabase mongoDatabase;

    public abstract List<Class<?>> getAnnotatedClasses();

    protected SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration cfg = new Configuration();
            getAnnotatedClasses().forEach(cfg::addAnnotatedClass);
            sessionFactory = cfg.buildSessionFactory();
        }
        return sessionFactory;
    }

    protected MongoDatabase getMongoDatabase() {
        if (mongoDatabase == null) {
            mongoDatabase = MongodbConnectionProvider.mongoDatabase;
        }
        return mongoDatabase;
    }
}
