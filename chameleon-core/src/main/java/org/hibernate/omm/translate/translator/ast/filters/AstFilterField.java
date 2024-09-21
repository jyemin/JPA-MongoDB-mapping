package org.hibernate.omm.translate.translator.ast.filters;

import org.bson.BsonWriter;
import org.hibernate.omm.translate.translator.ast.AstNode;
import org.hibernate.omm.translate.translator.ast.AstNodeType;

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
