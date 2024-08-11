/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    public MongoConfigMissingException(final Collection<String> missingConfigurations) {
        Assertions.assertTrue(CollectionUtil.isNotEmpty(missingConfigurations));
        this.missingConfigurations = missingConfigurations.stream().collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public String getMessage() {
        return String.format("mandatory Mongo specific configuration missing: " + missingConfigurations);
    }
}
