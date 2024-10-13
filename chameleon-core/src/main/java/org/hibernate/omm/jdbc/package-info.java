/**
 * One of the two pillars of Chameleon (the other one is AST rendering in org.hibernate.omm.ast package).
 * We try to create a virtual JDBC Driver to integrate with Hibernate workflow. The JDBC Driver
 * is "virtual" in the sense that it is Bson or Json that is passed through JDBC flow, instead of SQL.
 *
 * @author Nathan Xu
 * @since 1.0.0
 */
package org.hibernate.omm.jdbc;
