package org.hibernate.omm.mongoast.stages;

import org.bson.BsonWriter;
import org.hibernate.omm.mongoast.AstNodeType;

record AstProjectStageExcludeFieldSpecification(String path) implements AstProjectStageSpecification {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.ProjectStageExcludeFieldSpecification;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeInt32(path, 0);
    }
}
