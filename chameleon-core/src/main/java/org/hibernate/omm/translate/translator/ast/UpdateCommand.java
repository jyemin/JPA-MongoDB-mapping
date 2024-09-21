package org.hibernate.omm.translate.translator.ast;

import org.bson.BsonWriter;
import org.hibernate.omm.translate.translator.ast.filters.AstFilter;

import java.util.List;

public record UpdateCommand(String collection, AstFilter filter, List<AstFieldUpdate> updates) implements AstNode {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.UpdateCommand;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeStartDocument();
        writer.writeString("update", collection);
        writer.writeName("updates");
        writer.writeStartArray();
        writer.writeStartDocument();
        writer.writeName("q");
        filter.render(writer);
        writer.writeName("u");
        writer.writeStartDocument();
        writer.writeName("$set");
        writer.writeStartDocument();
        updates.forEach(update ->  update.render(writer));
        writer.writeEndDocument();
        writer.writeEndDocument();
        writer.writeBoolean("multi", !filter.isIdEqualityFilter());
        writer.writeEndDocument();
        writer.writeEndArray();
        writer.writeEndDocument();
    }
}
