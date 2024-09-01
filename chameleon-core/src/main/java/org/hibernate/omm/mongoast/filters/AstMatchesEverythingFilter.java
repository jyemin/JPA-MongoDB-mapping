package org.hibernate.omm.mongoast.filters;

import org.bson.BsonWriter;
import org.hibernate.omm.mongoast.AstNodeType;

public record AstMatchesEverythingFilter() implements AstFilter {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.MatchesEverythingFilter;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeStartDocument();
        writer.writeEndDocument();
    }
}
