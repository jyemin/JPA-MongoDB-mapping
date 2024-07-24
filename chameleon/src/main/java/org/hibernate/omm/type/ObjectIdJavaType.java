package org.hibernate.omm.type;

import org.bson.types.ObjectId;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractClassJavaType;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class ObjectIdJavaType extends AbstractClassJavaType<ObjectId> {

    public static final ObjectIdJavaType INSTANCE = new ObjectIdJavaType(ObjectId.class);

    protected ObjectIdJavaType(final Class<? extends ObjectId> type) {
        super(type);
    }

    @Override
    public ObjectId fromString(final CharSequence charSequence) {
        return new ObjectId(charSequence.toString());
    }

    @Override
    public MutabilityPlan<ObjectId> getMutabilityPlan() {
        return ImmutableMutabilityPlan.instance();
    }

    @Override
    public <X> X unwrap(final ObjectId value, final Class<X> type, final WrapperOptions options) {
        return type.cast(value);
    }

    @Override
    public <X> ObjectId wrap(final X value, final WrapperOptions options) {
        return (ObjectId) value;
    }

    @Override
    public JdbcType getRecommendedJdbcType(JdbcTypeIndicators context) {
        return ObjectIdJdbcType.INSTANCE;
    }
}
