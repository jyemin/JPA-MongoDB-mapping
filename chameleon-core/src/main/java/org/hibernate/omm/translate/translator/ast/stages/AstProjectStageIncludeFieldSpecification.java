package org.hibernate.omm.translate.translator.ast.stages;

import org.bson.BsonWriter;
import org.hibernate.omm.translate.translator.ast.AstNodeType;

record AstProjectStageIncludeFieldSpecification(String path) implements AstProjectStageSpecification {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.ProjectStageIncludeFieldSpecification;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeInt32(path, 1);
    }
}
