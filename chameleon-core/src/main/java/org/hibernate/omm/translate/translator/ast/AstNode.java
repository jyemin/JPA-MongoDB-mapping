package org.hibernate.omm.translate.translator.ast;

import org.bson.BsonWriter;

public interface AstNode {
    AstNodeType nodeType();

    void render(BsonWriter writer);
}

