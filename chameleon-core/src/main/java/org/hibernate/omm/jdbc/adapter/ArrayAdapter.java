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
package org.hibernate.omm.jdbc.adapter;

import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.Map;

/**
 * @author Nathan Xu
 * @see org.hibernate.omm.jdbc.MongoResultSet#getArray(String)
 * @see org.hibernate.omm.jdbc.MongoConnection#createArrayOf(String, Object[])
 * @since 1.0.0
 */
public interface ArrayAdapter extends Array {

    @Override
    default String getBaseTypeName() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Object getArray(final Map<String, Class<?>> map) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Object getArray(final long index, final int count) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Object getArray(final long index, final int count, final Map<String, Class<?>> map) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getResultSet() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getResultSet(final Map<String, Class<?>> map) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getResultSet(final long index, final int count) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getResultSet(final long index, final int count, final Map<String, Class<?>> map) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void free() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }
}
