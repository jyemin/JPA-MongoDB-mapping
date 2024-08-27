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
package org.hibernate.omm.type.array;

import org.hibernate.omm.jdbc.adapter.ArrayAdapter;
import org.hibernate.omm.type.MongoSqlType;

import java.util.Map;

public class MongoArray implements ArrayAdapter {

    private final String baseTypeName;
    private final Object elements;

    private int baseType = MongoSqlType.UNKNOWN;

    public MongoArray(final String baseTypeName, final Object elements) {
        this.baseTypeName = baseTypeName;
        this.elements = elements;
    }

    public void setBaseType(final Integer baseType) {
        this.baseType = baseType;
    }

    @Override
    public int getBaseType() {
        return baseType;
    }

    @Override
    public Object getArray() {
        return elements;
    }

    @Override
    public String getBaseTypeName() {
        return baseTypeName;
    }

    @Override
    public Object getArray(final Map map) {
        return elements;
    }

    @Override
    public Object getArray(final long index, final int count) {
        return elements;
    }

    @Override
    public Object getArray(final long index, final int count, final Map map) {
        return elements;
    }
}
