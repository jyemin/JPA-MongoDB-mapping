package org.hibernate.omm.extension;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TestInstance( TestInstance.Lifecycle.PER_CLASS )
@ExtendWith(ChameleonExtension.class)
public @interface MongoIntegrationTest {
    Class<?>[] externalEntities() default {};
    HibernateProperty[] hibernateProperties() default {};
}
