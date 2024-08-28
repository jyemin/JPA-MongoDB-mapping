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

package org.hibernate.omm.type.struct;

import org.hibernate.dialect.aggregate.AggregateSupportImpl;
import org.hibernate.mapping.AggregateColumn;
import org.hibernate.mapping.Column;

import static org.hibernate.type.SqlTypes.STRUCT;
import static org.hibernate.type.SqlTypes.STRUCT_ARRAY;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class MongoSQLAggregateSupport extends AggregateSupportImpl {

    public static final MongoSQLAggregateSupport INSTANCE = new MongoSQLAggregateSupport();

    private MongoSQLAggregateSupport() {}

    @Override
    public String aggregateComponentCustomReadExpression(
            String template,
            String placeholder,
            String aggregateParentReadExpression,
            String columnExpression,
            AggregateColumn aggregateColumn,
            Column column) {
        switch ( aggregateColumn.getTypeCode() ) {
            case STRUCT:
            case STRUCT_ARRAY:
                return template.replace( placeholder, '(' + aggregateParentReadExpression + ")." + columnExpression );
        }
        throw new IllegalArgumentException( "Unsupported aggregate SQL type: " + aggregateColumn.getTypeCode() );
    }

    @Override
    public String aggregateComponentAssignmentExpression(
            String aggregateParentAssignmentExpression,
            String columnExpression,
            AggregateColumn aggregateColumn,
            Column column) {
        switch ( aggregateColumn.getTypeCode() ) {
            case STRUCT:
            case STRUCT_ARRAY:
                return aggregateParentAssignmentExpression + "." + columnExpression;
        }
        throw new IllegalArgumentException( "Unsupported aggregate SQL type: " + aggregateColumn.getTypeCode() );
    }

    @Override
    public boolean requiresAggregateCustomWriteExpressionRenderer(int aggregateSqlTypeCode) {
        return false;
    }

}
