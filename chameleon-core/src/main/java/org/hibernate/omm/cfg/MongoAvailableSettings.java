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
package org.hibernate.omm.cfg;

/**
 * Contains all the OMM specific configuration entries.
 * <p>
 * As long as OMM clients provided the mandatory entries listed in this interface, they are totally set.
 *
 * @author Nathan Xu
 * @since 1.0.0
 */
public enum MongoAvailableSettings {
    MONGODB_CONNECTION_URL("mongodb.connection.url"),
    MONGODB_DATABASE("mongodb.database");

    private final String configuration;
    MongoAvailableSettings(final String configuration) {
        this.configuration = configuration;
    }

    public String getConfiguration() {
        return configuration;
    }

    @Override
    public String toString() {
        return configuration;
    }
}
