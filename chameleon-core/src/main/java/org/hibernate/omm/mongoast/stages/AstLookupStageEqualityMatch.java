package org.hibernate.omm.mongoast.stages;

import org.bson.BsonWriter;
import org.hibernate.omm.mongoast.AstNodeType;

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
