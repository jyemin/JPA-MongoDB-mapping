package org.hibernate.omm.translate.translator.ast.stages;

import org.bson.BsonWriter;
import org.hibernate.omm.translate.translator.ast.AstNodeType;

public record AstLookupStageEqualityMatch(String localField, String foreignField) implements AstLookupStageMatch {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.LookupStageEqualityMatch;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeString("localField", localField);
        writer.writeString("foreignField", foreignField);
    }
}
