package org.hibernate.omm.translate.translator.ast;

import org.bson.BsonWriter;

public record AstFieldUpdate(String name, AstValue value) implements AstNode {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.FieldUpdate;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeName(name);
        value.render(writer);
    }
}
