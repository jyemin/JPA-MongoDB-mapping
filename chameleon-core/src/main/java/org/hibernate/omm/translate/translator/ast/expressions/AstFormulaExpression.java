package org.hibernate.omm.translate.translator.ast.expressions;

import org.bson.BsonDocument;
import org.bson.BsonWriter;
import org.bson.codecs.BsonDocumentCodec;
import org.bson.codecs.EncoderContext;
import org.hibernate.omm.translate.translator.ast.AstNodeType;

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
