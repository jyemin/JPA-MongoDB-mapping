package org.hibernate.omm.dialect.type;

import com.mongodb.assertions.Assertions;
import com.mongodb.lang.Nullable;
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
    @Nullable
    public <X> X unwrap(@Nullable final ObjectId value, final Class<X> type, @Nullable final WrapperOptions options) {
        Assertions.notNull("type", type);
        return type.cast(value);
    }

    @Override
    @Nullable
    public <X> ObjectId wrap(@Nullable final X value, @Nullable final WrapperOptions options) {
        return (ObjectId) value;
    }

    @Override
    public JdbcType getRecommendedJdbcType(@Nullable JdbcTypeIndicators context) {
        return ObjectIdJdbcType.INSTANCE;
    }
}
