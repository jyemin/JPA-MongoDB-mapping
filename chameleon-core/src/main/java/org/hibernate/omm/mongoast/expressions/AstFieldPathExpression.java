package org.hibernate.omm.mongoast.expressions;

import org.bson.BsonWriter;
import org.hibernate.omm.mongoast.AstNodeType;

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
