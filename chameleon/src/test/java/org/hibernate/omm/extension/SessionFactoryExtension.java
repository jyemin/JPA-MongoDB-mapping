package org.hibernate.omm.extension;

import jakarta.persistence.Entity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.omm.cfg.MongoAvailableSettings;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.ReflectionUtils;
import org.testcontainers.containers.MongoDBContainer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import static org.junit.platform.commons.support.AnnotationSupport.findAnnotatedFields;

/**
 * A JUnit 5 extension mechanism injecting initialized {@link SessionFactory} into testing class's
 * instance field annotated with {@link SessionFactoryInjected}, which will scan the inner classes
 * annotated with {@link Entity} and added them automatically into Hibernate's meta model.
 * <p/>
 * Another benefit is testing class could extend from other parent class, if needed.
 * @author Nathn Xu
 * @since 1.0.0
 */
public class SessionFactoryExtension implements BeforeEachCallback {

    private static final String MONGODB_DOCKER_IMAGE_NAME = "mongo:5.0.28";

    @Override
    public void beforeEach(final ExtensionContext context) {
        var testClass = context.getRequiredTestClass();

        var annotatedClasses = new ArrayList<Class<?>>();
        Arrays.stream(testClass.getDeclaredClasses())
                .filter(clazz -> clazz.getAnnotation(Entity.class) != null)
                .forEach(annotatedClasses::add);

        if (annotatedClasses.isEmpty()) {
            throw new IllegalStateException("no Entity inner static class found with the testing class!");
        }

        try (var mongoDBContainer = new MongoDBContainer(MONGODB_DOCKER_IMAGE_NAME)) {
            mongoDBContainer.start();

            var cfg = new Configuration();
            cfg.setProperty(MongoAvailableSettings.MONGODB_CONNECTION_URL, mongoDBContainer.getConnectionString());
            cfg.setProperty(MongoAvailableSettings.MONGODB_DATABASE, "test");
            annotatedClasses.forEach(cfg::addAnnotatedClass);
            var sessionFactory = cfg.buildSessionFactory();

            Object testInstance = context.getRequiredTestInstance();
            injectFields(testClass, testInstance, sessionFactory);
        }
    }

    private void injectFields(Class<?> testClass, Object testInstance, SessionFactory sessionFactory) {

        Predicate<Field> predicate = field -> ReflectionUtils.isNotStatic(field) && SessionFactory.class.isAssignableFrom(field.getType());
        findAnnotatedFields(testClass, SessionFactoryInjected.class, predicate)
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        field.set(testInstance, sessionFactory);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });
    }

}
