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

package org.hibernate.omm.dialect.exporter;

import com.mongodb.lang.Nullable;
import org.bson.BsonArray;
import org.bson.BsonBoolean;
import org.bson.BsonDocument;
import org.bson.BsonString;

import java.util.Collections;

import static com.mongodb.internal.operation.IndexHelper.generateIndexName;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public final class MongoIndexCommandUtil {

    private MongoIndexCommandUtil() {
    }

    public static BsonDocument getIndexCreationCommand(final String collection, @Nullable final String indexName,
            final BsonDocument keys,
            final boolean unique) {
        final var command = new BsonDocument("createIndexes", new BsonString(collection));
        final var index = new BsonDocument();
        index.append("key", keys);
        index.append("name", new BsonString(indexName != null ? indexName : generateIndexName(keys)));
        index.append("unique", BsonBoolean.valueOf(unique));
        command.put("indexes", new BsonArray(Collections.singletonList(index)));
        return command;
    }

}
