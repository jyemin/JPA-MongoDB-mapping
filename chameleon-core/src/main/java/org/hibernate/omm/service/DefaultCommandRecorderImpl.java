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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link CommandRecorder} implementation. Not thread-safe.
 *
 * @author Nathan Xu
 * @since 1.0.0
 */
public class DefaultCommandRecorderImpl implements CommandRecorder {

    public static final DefaultCommandRecorderImpl INSTANCE = new DefaultCommandRecorderImpl();

    private DefaultCommandRecorderImpl() {}

    private final List<BsonDocument> records = new ArrayList<>();

    @Override
    public void record(final BsonDocument command) {
        records.add(command);
    }

    @Override
    public List<BsonDocument> getCommandRecords() {
        return Collections.unmodifiableList(records);
    }

    @Override
    public void clearCommandRecords() {
        this.records.clear();
    }

}
