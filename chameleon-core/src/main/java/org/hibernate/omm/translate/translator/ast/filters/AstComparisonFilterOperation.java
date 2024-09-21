package org.hibernate.omm.translate.translator.ast.filters;

import org.bson.BsonWriter;
import org.hibernate.omm.translate.translator.ast.AstNodeType;
import org.hibernate.omm.translate.translator.ast.AstValue;

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
