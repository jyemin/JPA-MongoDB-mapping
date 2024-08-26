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
import org.bson.BsonElement;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Constraint;
import org.hibernate.mapping.Index;
import org.hibernate.mapping.Selectable;

import java.util.List;

import static com.mongodb.internal.operation.IndexHelper.generateIndexName;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public final class MongoIndexCommandUtil {

    private MongoIndexCommandUtil() {
    }

    public static BsonDocument getKeys(final Index index) {
        final var keys = new BsonDocument();
        for (Selectable selectable : index.getSelectables()) {
            if (!selectable.isFormula()) {
                keys.put(selectable.getText(), new BsonInt32(1));
            }
        }
        return keys;
    }

    public static BsonDocument getKeys(final Constraint constraint) {
        final var keys = new BsonDocument();
        for (Column column : constraint.getColumns()) {
            keys.put(column.getName(), new BsonInt32(1));
        }
        return keys;
    }

    public static BsonDocument getIndexCreationCommand(final String collection, @Nullable final String indexName,
            final BsonDocument keys,
            final boolean unique) {

        final var index = new BsonDocument(
                List.of(
                        new BsonElement("key", keys),
                        new BsonElement("unique", BsonBoolean.valueOf(unique))
                )
        );
        if (indexName != null) {
            index.put("name", new BsonString(indexName));
        }

        return new BsonDocument(
                List.of(
                        new BsonElement("createIndexes", new BsonString(collection)),
                        new BsonElement("indexes", new BsonArray(List.of(index)))
                )
        );
    }

    public static BsonDocument getIndexDeletionCommand(final String collection, @Nullable final String indexName, final BsonDocument keys) {
        return new BsonDocument(
                List.of(
                        new BsonElement("dropIndexes", new BsonString(collection)),
                        new BsonElement("index", new BsonString(indexName != null ? indexName : generateIndexName(keys)))
                )
        );
    }
}
