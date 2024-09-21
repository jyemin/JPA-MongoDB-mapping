package org.hibernate.omm.translate.translator.ast.stages;

import org.bson.BsonWriter;
import org.hibernate.omm.translate.translator.ast.AstNodeType;
import org.hibernate.omm.translate.translator.ast.expressions.AstExpression;

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
