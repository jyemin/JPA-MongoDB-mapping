package org.hibernate.omm.translate.translator.ast;

import org.bson.BsonWriter;
import org.hibernate.omm.translate.translator.ast.stages.AstStage;

import java.util.List;

public record AstPipeline(List<AstStage> stages) implements AstNode {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.Pipeline;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeStartArray();
        stages.forEach(stage -> stage.render(writer));
        writer.writeEndArray();
    }

}
