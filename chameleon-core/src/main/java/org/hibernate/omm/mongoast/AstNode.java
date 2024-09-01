package org.hibernate.omm.mongoast;

import org.bson.BsonWriter;

public interface AstNode {
    AstNodeType nodeType();

    void render(BsonWriter writer);
}

