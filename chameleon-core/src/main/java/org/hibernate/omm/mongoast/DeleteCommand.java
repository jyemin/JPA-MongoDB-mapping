package org.hibernate.omm.mongoast;

import org.bson.BsonWriter;
import org.hibernate.omm.mongoast.filters.AstFilter;

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
        writer.writeInt32("limit", 0);
        writer.writeEndDocument();
        writer.writeEndArray();
        writer.writeEndDocument();
    }
}
