package org.hibernate.omm.util;

import com.mongodb.lang.Nullable;

import java.util.Collection;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public final class CollectionUtil {

    public static final String ID_FIELD_NAME = "_id";
    public static final String DB_VERSION_QUERY_FIELD_NAME = "buildinfo";

    private CollectionUtil() {
    }

    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

}
