package org.hibernate.omm.mongoast.expressions;

import org.bson.BsonDocument;
import org.bson.BsonWriter;
import org.bson.codecs.BsonDocumentCodec;
import org.bson.codecs.EncoderContext;
import org.hibernate.omm.mongoast.AstNodeType;

public record AstFormulaExpression(BsonDocument formula) implements AstExpression {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.FormulaExpression;
    }

    @Override
    public void render(final BsonWriter writer) {
        new BsonDocumentCodec().encode(writer, formula, EncoderContext.builder().build());
    }
}
