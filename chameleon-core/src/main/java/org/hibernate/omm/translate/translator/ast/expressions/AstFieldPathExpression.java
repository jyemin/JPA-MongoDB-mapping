package org.hibernate.omm.translate.translator.ast.expressions;

import org.bson.BsonWriter;
import org.hibernate.omm.translate.translator.ast.AstNodeType;

public record AstFieldPathExpression(String path) implements AstExpression {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.FieldPathExpression;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeString(path); // path already includes "$" prefix
    }
}
