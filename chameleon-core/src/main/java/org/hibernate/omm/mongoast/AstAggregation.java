package org.hibernate.omm.mongoast;

import org.bson.BsonWriter;

public record AstAggregation(String collection, AstPipeline pipeline) implements AstNode {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.Aggregation;
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
