/**
 * Contains all Mongo Bson rendering logic. During early stage,
 * {@link org.hibernate.sql.ast.spi.AbstractSqlAstTranslator} is heavily relied upon (copied into
 * {@link org.hibernate.omm.translate.AbstractSqlAstTranslator}
 * with minor changes to make it possible for classes in this package to inherit from it, directly or indirectly.
 *
 * @author Nathan Xu
 * @since 1.0.0
 */
package org.hibernate.omm.translate.translator;
