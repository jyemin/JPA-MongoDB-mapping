package org.hibernate.omm.mongoast.filters;

import org.bson.BsonWriter;
import org.hibernate.omm.mongoast.AstNodeType;
import org.hibernate.omm.mongoast.AstValue;

public record AstAllFilterOperation(AstValue value) implements AstFilterOperation {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.AllFilterOperation;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeStartDocument();
        writer.writeName("$all");
        value.render(writer);
        writer.writeEndDocument();
    }
}
