package org.hibernate.omm.mongoast.filters;

import org.bson.BsonWriter;
import org.hibernate.omm.mongoast.AstNodeType;

import java.util.List;

public record AstAndFilter(List<AstFilter> filters) implements AstFilter {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.AndFilter;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeStartDocument();
        writer.writeName("$and");
        writer.writeStartArray();
        filters.forEach(filter -> filter.render(writer));
        writer.writeEndArray();
        writer.writeEndDocument();
    }

    @Override
    public boolean isIdEqualityFilter() {
        return filters.stream().anyMatch(AstFilter::isIdEqualityFilter);
    }
}
