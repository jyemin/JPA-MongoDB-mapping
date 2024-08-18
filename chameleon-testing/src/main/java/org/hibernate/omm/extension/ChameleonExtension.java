package org.hibernate.omm.extension;

import com.mongodb.client.MongoDatabase;
import jakarta.persistence.Entity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.omm.cfg.MongoAvailableSettings;
import org.hibernate.omm.jdbc.MongoConnectionProvider;
import org.hibernate.omm.service.CommandRecorder;
import org.hibernate.omm.service.DefaultCommandRecorderImpl;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.ReflectionUtils;
import org.testcontainers.containers.MongoDBContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import static org.junit.platform.commons.support.AnnotationSupport.findAnnotatedFields;

/**
 * A JUnit 5 extension mechanism injecting initialized {@link SessionFactory} and/or {@link MongoDatabase}
 * into testing class's static or instance fields annotated with {@link SessionFactoryInjected} or {@link MongoDatabaseInjected},
 * and scanning the inner classes annotated with {@link Entity} and adding them automatically into Hibernate's meta model.
 * <p/>
 * Another benefit is the testing class could extend from other parent class, if needed.
 *
 * @author Nathn Xu
 * @since 1.0.0
 */
class ChameleonExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {

    private static final String MONGODB_DOCKER_IMAGE_NAME = "mongo:5.0.28";
    private static final String DATABASE_NAME = "test";

    private MongoDBContainer mongoDBContainer;
    private SessionFactoryImplementor sessionFactory;
    private MongoDatabase mongoDatabase;

    @Override
    public void beforeAll(final ExtensionContext context) {
        var testClass = context.getRequiredTestClass();

        var annotatedClasses = new ArrayList<Class<?>>();
        Arrays.stream(testClass.getDeclaredClasses())
                .filter(clazz -> clazz.getAnnotation(Entity.class) != null)
                .forEach(annotatedClasses::add);

        if (annotatedClasses.isEmpty()) {
            throw new IllegalStateException("No Entity inner static class found within the testing class: " + testClass.getName());
        }

        mongoDBContainer = new MongoDBContainer(MONGODB_DOCKER_IMAGE_NAME);
        mongoDBContainer.start();

        var cfg = new Configuration();
        cfg.setProperty(MongoAvailableSettings.MONGODB_CONNECTION_URL.getConfiguration(), mongoDBContainer.getConnectionString());
        cfg.setProperty(MongoAvailableSettings.MONGODB_DATABASE.getConfiguration(), DATABASE_NAME);
        annotatedClasses.forEach(cfg::addAnnotatedClass);

        var standardServiceRegistryBuilder = cfg.getStandardServiceRegistryBuilder();
        standardServiceRegistryBuilder.addService(CommandRecorder.class, DefaultCommandRecorderImpl.INSTANCE);

        sessionFactory = (SessionFactoryImplementor) cfg.buildSessionFactory();
        var mongoConnectionProvider = (MongoConnectionProvider) sessionFactory.getServiceRegistry().getService(ConnectionProvider.class);
        assert mongoConnectionProvider != null;
        mongoDatabase = mongoConnectionProvider.getMongoDatabase();

        injectStaticFields(testClass, SessionFactoryInjected.class, SessionFactory.class, sessionFactory);
        injectStaticFields(testClass, MongoDatabaseInjected.class, MongoDatabase.class, mongoDatabase);
        injectStaticFields(testClass, CommandRecorderInjected.class, CommandRecorder.class, DefaultCommandRecorderImpl.INSTANCE);
    }

    @Override
    public void afterAll(final ExtensionContext context) {
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


    @Override
    public void beforeEach(final ExtensionContext context) {
        try (var collectionNameIter = mongoDatabase.listCollectionNames().iterator()) {
            while (collectionNameIter.hasNext()) {
                mongoDatabase.getCollection(collectionNameIter.next()).drop();
            }
        }

        var commandRecorder = DefaultCommandRecorderImpl.INSTANCE;
        commandRecorder.clearCommandRecords();

        injectInstanceFields(context.getRequiredTestClass(), context.getRequiredTestInstance(), SessionFactoryInjected.class,
                SessionFactory.class,
                sessionFactory);
        injectInstanceFields(context.getRequiredTestClass(), context.getRequiredTestInstance(), MongoDatabaseInjected.class,
                MongoDatabase.class, mongoDatabase);
        injectInstanceFields(context.getRequiredTestClass(), context.getRequiredTestInstance(), SessionFactoryInjected.class,
                SessionFactory.class,
                sessionFactory);
        injectInstanceFields(context.getRequiredTestClass(), context.getRequiredTestInstance(), CommandRecorderInjected.class,
                CommandRecorder.class, commandRecorder);
    }



    private void injectStaticFields(Class<?> testClass, Class<? extends Annotation> annotationClass, Class<?> fieldType,
            Object injectedValue) {
        doInjectFields(true, testClass, testClass, annotationClass, fieldType, injectedValue);
    }

    private void injectInstanceFields(Class<?> testClass, Object testInstance, Class<? extends Annotation> annotationClass,
            Class<?> fieldType,
            Object injectedValue) {
        doInjectFields(false, testClass, testInstance, annotationClass, fieldType, injectedValue);
    }

    private void doInjectFields(boolean injectStaticField, Class<?> testClass, Object fieldOwner,
            Class<? extends Annotation> annotationClass, Class<?> fieldType,
            Object injectedValue) {
        Predicate<Field> predicate = field -> (injectStaticField ?
                ReflectionUtils.isStatic(field) : ReflectionUtils.isNotStatic(field) ) && fieldType.isAssignableFrom(field.getType());
        findAnnotatedFields(testClass, annotationClass, predicate)
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        field.set(fieldOwner, injectedValue);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });
    }

    @Override
    public void afterEach(final ExtensionContext extensionContext) throws Exception {
        DefaultCommandRecorderImpl.INSTANCE.clearCommandRecords();
    }
}
