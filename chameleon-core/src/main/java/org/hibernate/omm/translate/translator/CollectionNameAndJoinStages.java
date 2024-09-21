package org.hibernate.omm.translate.translator;

import org.hibernate.omm.translate.translator.ast.stages.AstStage;

import java.util.List;

public record CollectionNameAndJoinStages(String collectionName, List<AstStage> joinStages) {
}
