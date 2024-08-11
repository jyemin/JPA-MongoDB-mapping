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
package org.hibernate.omm.type;

import org.hibernate.type.descriptor.java.JavaType;

/**
 * Simulate {@link java.sql.Types} so we can differentiate among different MongoDB types during invoking
 * {@link java.sql.PreparedStatement#setObject(int, Object, int)}
 *
 * @author Nathan Xu
 * @see ObjectIdJdbcType#getBinder(JavaType)
 * @since 1.0.0
 */
public final class MongoSqlType {
    public static final int UNKNOWN = 4_000;
    public static final int OBJECT_ID = 3_000;
}
