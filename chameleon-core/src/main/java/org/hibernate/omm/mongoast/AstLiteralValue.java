package org.hibernate.omm.mongoast;

import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.BsonValueCodec;
import org.bson.codecs.EncoderContext;

public record AstLiteralValue(BsonValue literalValue) implements AstValue {
    @Override
    public AstNodeType nodeType() {
        // TODO: what does Linq3 AST use for literal values?
        throw new UnsupportedOperationException();
    }

    @Override
    public void render(final BsonWriter writer) {
        new BsonValueCodec().encode(writer, literalValue, EncoderContext.builder().build());
    }
}
