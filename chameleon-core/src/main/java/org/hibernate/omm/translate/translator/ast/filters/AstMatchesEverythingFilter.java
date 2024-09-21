package org.hibernate.omm.translate.translator.ast.filters;

import org.bson.BsonWriter;
import org.hibernate.omm.translate.translator.ast.AstNodeType;

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
