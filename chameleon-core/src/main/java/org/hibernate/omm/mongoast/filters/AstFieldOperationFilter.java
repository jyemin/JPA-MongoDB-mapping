package org.hibernate.omm.mongoast.filters;

import org.bson.BsonWriter;
import org.hibernate.omm.mongoast.AstNodeType;

public record AstFieldOperationFilter(AstFilterField field, AstFilterOperation operation) implements AstFilter {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.FieldOperationFilter;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeStartDocument();
        field.render(writer);
        operation.render(writer);
        writer.writeEndDocument();
    }

    @Override
    public boolean isIdEqualityFilter() {
        return field().path().equals("_id") &&
                operation() instanceof AstComparisonFilterOperation comparisonFilterOperation &&
                comparisonFilterOperation.operator().equals(AstComparisonFilterOperator.EQ);
    }
}
