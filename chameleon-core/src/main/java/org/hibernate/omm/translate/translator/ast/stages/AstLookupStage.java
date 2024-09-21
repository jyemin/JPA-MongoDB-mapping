package org.hibernate.omm.translate.translator.ast.stages;

import org.bson.BsonWriter;
import org.hibernate.omm.translate.translator.ast.AstNodeType;

import java.util.List;

public record AstLookupStage(String from, String as, AstLookupStageMatch match, List<AstStage> pipeline) implements AstStage {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.LookupStage;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeStartDocument();
        writer.writeName("$lookup");
        writer.writeStartDocument();
        writer.writeString("from", from);
        writer.writeString("as", as);
        match.render(writer);
        if (!pipeline.isEmpty()) {
            writer.writeName("pipeline");
            writer.writeStartArray();
            pipeline.forEach(stage -> stage.render(writer));
            writer.writeEndArray();
        }
        writer.writeEndDocument();
        writer.writeEndDocument();
    }

    public AstLookupStage addPipeline(List<AstStage> pipeline) {
        if (!this.pipeline.isEmpty()) {
            throw new IllegalStateException("Already has a pipeline!");
        }
        return new AstLookupStage(from, as, match, pipeline);
    }
}
