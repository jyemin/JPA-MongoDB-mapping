package org.hibernate.omm.mongoast.stages;

import org.hibernate.omm.mongoast.AstNode;
import org.hibernate.omm.mongoast.expressions.AstExpression;

public interface AstProjectStageSpecification extends AstNode {
    static AstProjectStageSpecification Exclude(String path)
    {
        return new AstProjectStageExcludeFieldSpecification(path);
    }

    static AstProjectStageSpecification ExcludeId()
    {
        return new AstProjectStageExcludeFieldSpecification("_id");
    }

    static AstProjectStageSpecification Include(String path)
    {
        return new AstProjectStageIncludeFieldSpecification(path);
    }

    static AstProjectStageSpecification Set(String path, AstExpression value)
    {
        return new AstProjectStageSetFieldSpecification(path, value);
    }

}
