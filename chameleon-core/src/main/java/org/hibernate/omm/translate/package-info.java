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
 * One of the two pillars of Chameleon (another one is the "virtual JDBC Driver" in {@link org.hibernate.omm.jdbc} package).
 * Do our due diligence to render SQM AST tree into Bson string (including MQL and Bson command text).
 * <p/>
 * Initially we copied existing {@link org.hibernate.sql.ast.spi.AbstractSqlAstTranslator} intact
 * for referencing and imitating purpose (renamed to {@link org.hibernate.omm.translate.AbstractSqlAstTranslator};
 * but ultimately we should get rid of its usage.
 *
 * @author Nathan Xu
 * @since 1.0.0
 */
package org.hibernate.omm.translate;
