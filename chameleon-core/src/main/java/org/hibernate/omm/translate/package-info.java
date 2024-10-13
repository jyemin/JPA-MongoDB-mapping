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
