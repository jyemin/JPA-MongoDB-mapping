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

import org.bson.BsonDocument;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.service.Service;

import java.util.List;

/**
 * A {@link Service} mainly used for testing purpose.
 * <p/>
 * Sometimes we need to verify on real command issued to Mongo,
 * e.g. verifying {@link BeforeExecutionGenerator}.
 *
 * @author Nathan Xu
 * @since 1.0.0
 */
public interface CommandRecorder extends Service {

    /**
     * Record a complet4e Bson command issued to Mongo
     *
     * @param command a de-parameterized Bson command
     */
    void record(BsonDocument command);

    /**
     * Return immutable accumulated command recorded.
     * @return previous accumulated records
     */
    List<BsonDocument> getCommandRecords();

    /**
     * empty accumulated command records
     */
    void clearCommandRecords();
}
