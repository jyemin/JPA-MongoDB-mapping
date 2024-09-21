package org.hibernate.omm.translate.translator.ast.stages;

import org.bson.BsonWriter;
import org.hibernate.omm.translate.translator.ast.AstNodeType;
import org.hibernate.omm.translate.translator.ast.filters.AstFilter;

public record AstMatchStage(AstFilter filter) implements AstStage{
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.MatchStage;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeStartDocument();
        writer.writeName("$match");

        filter.render(writer);

        writer.writeEndDocument();
    }
}
