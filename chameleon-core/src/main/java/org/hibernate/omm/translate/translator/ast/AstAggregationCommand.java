package org.hibernate.omm.translate.translator.ast;

import org.bson.BsonWriter;

public record AstAggregationCommand(String collection, AstPipeline pipeline) implements AstNode {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.AggregationCommand;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeStartDocument();
        writer.writeString("aggregate", collection);
        writer.writeName("pipeline");
        pipeline.render(writer);
        writer.writeEndDocument();
    }
}
