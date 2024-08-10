package org.hibernate.omm;

import com.mongodb.client.MongoDatabase;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.omm.cfg.MongoAvailableSettings;
import org.hibernate.omm.jdbc.MongoConnectionProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.MongoDBContainer;

import java.util.List;

/**
 * Abstract parent class to integration testing cases to provide:
 * <ul>
 *     <li>{@link SessionFactory} based on abstract {@link #getAnnotatedClasses()} method </li>
 *     <li>{@link MongoDatabase} instance to interact with Mongo via java driver</li>
 *     <li>guarantee that each test case will be run in a blank db state</li>
 * </ul>
 *
 * @author Nathan Xu
 * @deprecated use {@link org.hibernate.omm.extension.MongoIntegrationTest} instead
 */
public abstract class AbstractMongodbIntegrationTests {

    private static final String MONGODB_DOCKER_IMAGE_NAME = "mongo:5.0.28";
    private static final String DATABASE_NAME = "test";

    private MongoDBContainer mongoDBContainer;

    private SessionFactory sessionFactory;

    @BeforeEach
    void createSessionFactory() {
        Configuration cfg = new Configuration();
        mongoDBContainer = new MongoDBContainer(MONGODB_DOCKER_IMAGE_NAME);
        mongoDBContainer.start();
        cfg.setProperty(MongoAvailableSettings.MONGODB_CONNECTION_URL, mongoDBContainer.getConnectionString());
        cfg.setProperty(MongoAvailableSettings.MONGODB_DATABASE, DATABASE_NAME);
        getAnnotatedClasses().forEach(cfg::addAnnotatedClass);
        sessionFactory = cfg.buildSessionFactory();
    }

    @AfterEach
    void closeSessionFactory() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            } finally {
                sessionFactory = null;
            }
        }
        if (mongoDBContainer != null) {
            try {
                mongoDBContainer.stop();
            } finally {
                mongoDBContainer = null;
            }
        }
    }

    public abstract List<Class<?>> getAnnotatedClasses();

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    protected MongoDatabase getMongoDatabase() {
        return MongoConnectionProvider.mongoDatabase;
    }

}
