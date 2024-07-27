package org.hibernate.omm.util;

import com.mongodb.lang.Nullable;

import java.util.Collection;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public final class CollectionUtil {

    private CollectionUtil() {
    }

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

}
