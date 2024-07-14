package org.hibernate.omm.dialet;

import org.hibernate.dialect.Dialect;
import org.hibernate.sql.ast.SqlAstTranslatorFactory;

public class MongodbDialect extends Dialect {

  @Override
  public SqlAstTranslatorFactory getSqlAstTranslatorFactory() {

    return null;
  }
}
