package org.hibernate.omm.translate.translator.ast;

import org.bson.BsonWriter;

public record AstPlaceholder() implements AstValue {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.Placeholder;
    }

    @Override
    public void render(BsonWriter writer) {
        writer.writeUndefined();
    }
}
