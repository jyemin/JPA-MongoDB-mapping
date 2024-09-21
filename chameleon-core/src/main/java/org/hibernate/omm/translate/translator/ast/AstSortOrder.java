package org.hibernate.omm.translate.translator.ast;

import org.bson.BsonWriter;

public interface AstSortOrder {
    void render(BsonWriter writer);
}
