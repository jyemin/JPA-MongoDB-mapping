package org.hibernate.omm.translate.translator.ast;

import org.bson.BsonWriter;
import org.hibernate.omm.translate.translator.ast.filters.AstFilter;

public record DeleteCommand(String collection, AstFilter filter) implements AstNode {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.DeleteCommand;
    }

    @Override
    public void render(BsonWriter writer) {
        writer.writeStartDocument();
        writer.writeString("delete", collection);
        writer.writeName("deletes");
        writer.writeStartArray();
        writer.writeStartDocument();
        writer.writeName("q");
        filter.render(writer);
        writer.writeInt32("limit", filter.isIdEqualityFilter() ? 1 : 0);
        writer.writeEndDocument();
        writer.writeEndArray();
        writer.writeEndDocument();
    }
}
