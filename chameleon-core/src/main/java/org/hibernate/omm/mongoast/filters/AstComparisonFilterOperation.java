package org.hibernate.omm.mongoast.filters;

import org.bson.BsonWriter;
import org.hibernate.omm.mongoast.AstNodeType;
import org.hibernate.omm.mongoast.AstValue;

public record AstComparisonFilterOperation(AstComparisonFilterOperator operator, AstValue value) implements AstFilterOperation {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.ComparisonFilterOperation;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeStartDocument();
        writer.writeName(operator.operatorName());
        value.render(writer);
        writer.writeEndDocument();
    }
}
