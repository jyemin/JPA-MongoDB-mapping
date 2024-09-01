package org.hibernate.omm.mongoast.filters;

import org.bson.BsonWriter;
import org.hibernate.omm.mongoast.AstNode;
import org.hibernate.omm.mongoast.AstNodeType;

public record AstFilterField(String path) implements AstNode {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.FilterField;
    }

    @Override
    public void render(final BsonWriter writer) {
         writer.writeName(path);
    }
}
