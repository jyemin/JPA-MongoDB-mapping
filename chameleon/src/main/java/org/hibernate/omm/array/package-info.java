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
/**
 * Array plays a central role in MongoDB and is usually not well-supported in SQL or JPA.
 * Only recently Hibernate starts array support (v6.2 and v6.6), mainly based on PostgreSQL dialect.
 * This package is well deserved to centralize all its complicated processing classes, e.g.:
 * <ul>
 *     <li>Hibernate integration</li>
 *     <li>Struct codesc</li>
 *     <li>array function</li>
 *     <li>AST rendering</li>
 * </ul>
 *
 * @author Nathan Xu
 * @since 1.0.0
 */
package org.hibernate.omm.array;
