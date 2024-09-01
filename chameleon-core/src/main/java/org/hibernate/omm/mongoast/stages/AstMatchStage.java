package org.hibernate.omm.mongoast.stages;

import org.bson.BsonWriter;
import org.hibernate.omm.mongoast.AstNodeType;
import org.hibernate.omm.mongoast.filters.AstFilter;

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
