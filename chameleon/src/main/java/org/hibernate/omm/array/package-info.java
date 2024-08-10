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
