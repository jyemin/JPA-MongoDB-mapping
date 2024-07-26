package org.hibernate.omm;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.omm.cfg.MongoAvailableSettings;
import org.hibernate.omm.jdbc.MongoConnectionProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.MongoDBContainer;

import java.util.List;

/**
 * @author Nathan Xu
 */
public abstract class AbstractMongodbIntegrationTests {

    private static final String MONGODB_DOCKER_IMAGE_NAME = "mongo:5.0.28";

    private MongoDBContainer mongoDBContainer;

    private SessionFactory sessionFactory;

    @BeforeEach
    void createSessionFactory() {
        Configuration cfg = new Configuration();
        mongoDBContainer = new MongoDBContainer(MONGODB_DOCKER_IMAGE_NAME);
        mongoDBContainer.start();
        cfg.setProperty(MongoAvailableSettings.MONGODB_CONNECTION_URL, mongoDBContainer.getConnectionString());
        cfg.setProperty(MongoAvailableSettings.MONGODB_DATABASE, "test");
        getAnnotatedClasses().forEach(cfg::addAnnotatedClass);
        sessionFactory = cfg.buildSessionFactory();
    }

    @AfterEach
    void closeSessionFactory() {
        sessionFactory.close();
        mongoDBContainer.stop();
    }

    public abstract List<Class<?>> getAnnotatedClasses();

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    protected MongoDatabase getMongoDatabase() {
        return MongoConnectionProvider.mongoDatabase;
    }

    protected void deleteCollection(String collectionName) {
        Document command = new Document("delete", collectionName)
                .append("deletes", List.of(new Document("q", new Document()).append("limit", 0)));
        Document result = getMongoDatabase().runCommand(command);
        if (result.getDouble("ok") != 1.0) {
            throw new IllegalStateException("failed to delete collection: " + collectionName);
        }
    }
}
