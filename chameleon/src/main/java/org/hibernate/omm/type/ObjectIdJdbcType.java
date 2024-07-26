package org.hibernate.omm.type;

import com.mongodb.lang.Nullable;
import org.bson.BsonObjectId;
import org.bson.assertions.Assertions;
import org.bson.types.ObjectId;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.BasicBinder;
import org.hibernate.type.descriptor.jdbc.BasicExtractor;
import org.hibernate.type.descriptor.jdbc.JdbcLiteralFormatter;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.internal.JdbcLiteralFormatterCharacterData;
import org.hibernate.type.spi.TypeConfiguration;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class ObjectIdJdbcType implements JdbcType {

    public static ObjectIdJdbcType INSTANCE = new ObjectIdJdbcType();

    @Override
    public <T> JavaType<T> getJdbcRecommendedJavaTypeMapping(
            @Nullable Integer length,
            @Nullable Integer scale,
            TypeConfiguration typeConfiguration) {
        Assertions.notNull("typeConfiguration", typeConfiguration);
        return typeConfiguration.getJavaTypeRegistry().getDescriptor(ObjectId.class);
    }

    @Override
    public <T> JdbcLiteralFormatter<T> getJdbcLiteralFormatter(JavaType<T> javaType) {
        Assertions.notNull("javaType", javaType);
        return new JdbcLiteralFormatterCharacterData<>(javaType, false);
    }

    @Override
    public Class<?> getPreferredJavaTypeClass(@Nullable WrapperOptions options) {
        return ObjectId.class;
    }

    @Override
    public <X> ValueBinder<X> getBinder(final JavaType<X> javaType) {
        Assertions.notNull("javaType", javaType);
        return new BasicBinder<>(javaType, this) {
            @Override
            protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options) throws SQLException {
                st.setObject(index, value, MongoSqlType.OBJECT_ID);
            }

            @Override
            protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
                    throws SQLException {
                st.setObject(name, value, MongoSqlType.OBJECT_ID);
            }
        };
    }

    @Override
    public <X> ValueExtractor<X> getExtractor(final JavaType<X> javaType) {
        Assertions.notNull("javaType", javaType);
        return new BasicExtractor<>(javaType, this) {
            @Override
            protected X doExtract(ResultSet rs, int paramIndex, WrapperOptions options) throws SQLException {
                return javaType.getJavaTypeClass().cast(rs.getObject(paramIndex, BsonObjectId.class).getValue());
            }

            @Override
            protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
                return javaType.getJavaTypeClass().cast(statement.getObject(index, BsonObjectId.class).getValue());
            }

            @Override
            protected X doExtract(CallableStatement statement, String name, WrapperOptions options) throws SQLException {
                return javaType.getJavaTypeClass().cast(statement.getObject(name, ObjectId.class));
            }
        };
    }

    @Override
    public String getFriendlyName() {
        return "OBJECT_ID";
    }

    @Override
    public int getJdbcTypeCode() {
        return SqlTypes.JAVA_OBJECT;
    }

    @Override
    public String toString() {
        return "ObjectIdTypeDescriptor(" + getFriendlyName() + ")";
    }
}
