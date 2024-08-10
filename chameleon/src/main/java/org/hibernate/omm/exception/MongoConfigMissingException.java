package org.hibernate.omm.exception;

import com.mongodb.assertions.Assertions;
import org.hibernate.omm.util.CollectionUtil;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class MongoConfigMissingException extends RuntimeException {
    private final Set<String> missingConfigurations;

    public MongoConfigMissingException(Collection<String> missingConfigurations) {
        Assertions.assertTrue(CollectionUtil.isNotEmpty(missingConfigurations));
        this.missingConfigurations = missingConfigurations.stream().collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public String getMessage() {
        return String.format("mandatory Mongo specific configuration missing: " + missingConfigurations);
    }
}
