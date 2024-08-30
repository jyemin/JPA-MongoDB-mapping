/*
 *
 * Copyright 2008-present MongoDB, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.hibernate.omm.service;

import com.mongodb.ReadConcern;
import com.mongodb.WriteConcern;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class CollectionRWConcerns {

    private final Map<String, ReadConcern> readConcernByCollection;
    private final Map<String, WriteConcern> writeConcernByCollection;

    public CollectionRWConcerns(final Map<String, ReadConcern> readConcernByCollection, final Map<String, WriteConcern> writeConcernByCollection) {
        this.readConcernByCollection = Collections.unmodifiableMap(readConcernByCollection);
        this.writeConcernByCollection = Collections.unmodifiableMap(writeConcernByCollection);
    }

    public Optional<ReadConcern> getReadConcern(final String collectionName) {
        return Optional.ofNullable(readConcernByCollection.get(collectionName));
    }

    public Optional<WriteConcern> getWriteConcern(final String collectionName) {
        return Optional.ofNullable(writeConcernByCollection.get(collectionName));
    }

}
