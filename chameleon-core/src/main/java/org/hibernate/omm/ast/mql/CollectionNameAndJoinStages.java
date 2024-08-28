package org.hibernate.omm.ast.mql;

import org.hibernate.omm.mongoast.stages.AstStage;

import java.util.List;

public record CollectionNameAndJoinStages(String collectionName, List<AstStage> joinStages) {
}
