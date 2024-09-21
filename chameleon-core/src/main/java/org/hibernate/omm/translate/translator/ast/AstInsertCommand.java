package org.hibernate.omm.translate.translator.ast;

import org.bson.BsonWriter;

import java.util.List;

public record AstInsertCommand(String collection, List<AstElement> elements) implements AstNode {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.InsertCommand;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeStartDocument();
        writer.writeString("insert", collection);
        writer.writeName("documents");
        writer.writeStartArray();
        writer.writeStartDocument();
        elements.forEach(element -> element.render(writer));
        writer.writeEndDocument();
        writer.writeEndArray();
        writer.writeEndDocument();
    }
}
