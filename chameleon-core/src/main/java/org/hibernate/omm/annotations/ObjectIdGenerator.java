package org.hibernate.omm.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.hibernate.annotations.IdGeneratorType;
import org.hibernate.annotations.ValueGenerationType;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
@IdGeneratorType(org.hibernate.omm.id.ObjectIdGenerator.class)
@ValueGenerationType(generatedBy = org.hibernate.omm.id.ObjectIdGenerator.class)
@Retention(RUNTIME)
@Target({FIELD, METHOD})
public @interface ObjectIdGenerator {}
