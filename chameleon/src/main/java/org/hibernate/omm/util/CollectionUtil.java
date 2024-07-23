package org.hibernate.omm.util;

import java.util.Collection;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public final class CollectionUtil {
    private CollectionUtil() {
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public static boolean hasMoreThanOneElement(Collection<?> collection) {
        return collection != null && collection.size() > 1;
    }
}
