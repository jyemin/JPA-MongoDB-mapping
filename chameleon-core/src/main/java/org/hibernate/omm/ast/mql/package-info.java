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
/**
 * Contains all MQL (Mongo Query Language) rendering logic. During early stage,
 * {@link org.hibernate.sql.ast.spi.AbstractSqlAstTranslator} was heavily relied upon (copied into {@link org.hibernate.omm.ast.AbstractSqlAstTranslator}
 * with minor changes to make it possible to inherit from it from classes in this package directly or indirectly.
 *
 * @author Nathan Xu
 * @since 1.0.0
 */
package org.hibernate.omm.ast.mql;
