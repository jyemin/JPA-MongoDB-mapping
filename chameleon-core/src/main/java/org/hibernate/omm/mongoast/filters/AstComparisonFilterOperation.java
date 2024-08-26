package org.hibernate.omm.mongoast.filters;

import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.BsonValueCodec;
import org.bson.codecs.EncoderContext;
import org.hibernate.omm.mongoast.AstNodeType;

public record AstComparisonFilterOperation(AstComparisonFilterOperator operator, BsonValue value) implements AstFilterOperation {
    private static final BsonValueCodec BSON_VALUE_CODEC = new BsonValueCodec();

    @Override
    public AstNodeType nodeType() {
        return AstNodeType.ComparisonFilterOperation;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeStartDocument();
        writer.writeName(operator.operatorName());
        BSON_VALUE_CODEC.encode(writer, value, EncoderContext.builder().build());
        writer.writeEndDocument();
    }
}
