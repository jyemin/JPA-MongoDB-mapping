package org.hibernate.omm.mongoast.stages;

import org.bson.BsonWriter;
import org.hibernate.omm.mongoast.AstNodeType;
import org.hibernate.omm.mongoast.expressions.AstExpression;

public record AstProjectStageSetFieldSpecification(String path, AstExpression value) implements AstProjectStageSpecification {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.ProjectStageSetFieldSpecification;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeName(path);
        value.render(writer);
    }
}
