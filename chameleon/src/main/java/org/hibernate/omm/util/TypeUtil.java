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
package org.hibernate.omm.util;

import com.mongodb.assertions.Assertions;
import com.mongodb.lang.Nullable;
import org.bson.BsonArray;
import org.bson.BsonBinary;
import org.bson.BsonBoolean;
import org.bson.BsonDateTime;
import org.bson.BsonDecimal128;
import org.bson.BsonDouble;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.BsonString;
import org.bson.BsonTimestamp;
import org.bson.BsonType;
import org.bson.BsonValue;

import java.sql.Types;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public final class TypeUtil {
    private TypeUtil() {
    }

    public static Integer getJdbcType(final BsonType bsonType) {
        Assertions.notNull("bsonType", bsonType);
        return switch (bsonType) {
            case ARRAY -> Types.ARRAY;
            case BINARY -> Types.BINARY;
            case BOOLEAN -> Types.BOOLEAN;
            case DATE_TIME -> Types.TIME;
            case DECIMAL128 -> Types.DECIMAL;
            case DOUBLE -> Types.DOUBLE;
            case INT32 -> Types.INTEGER;
            case INT64 -> Types.BIGINT;
            case STRING -> Types.VARCHAR;
            case TIMESTAMP -> Types.TIMESTAMP;
            default -> Types.NULL;
        };
    }

    @Nullable
    public static Object unwrap(@Nullable final BsonValue bsonValue) {
        if (bsonValue == null) {
            return null;
        }
        return switch (bsonValue.getBsonType()) {
            case ARRAY -> ((BsonArray) bsonValue).getValues().stream().map(TypeUtil::unwrap).toList();
            case BINARY -> ((BsonBinary) bsonValue).getData();
            case BOOLEAN -> ((BsonBoolean) bsonValue).getValue();
            case DATE_TIME -> ((BsonDateTime) bsonValue).getValue();
            case DECIMAL128 -> ((BsonDecimal128) bsonValue).getValue().bigDecimalValue();
            case DOUBLE -> ((BsonDouble) bsonValue).getValue();
            case INT32 -> ((BsonInt32) bsonValue).getValue();
            case INT64 -> ((BsonInt64) bsonValue).getValue();
            case STRING -> ((BsonString) bsonValue).getValue();
            case TIMESTAMP -> ((BsonTimestamp) bsonValue).getValue();
            default -> null;
        };
    }
}
