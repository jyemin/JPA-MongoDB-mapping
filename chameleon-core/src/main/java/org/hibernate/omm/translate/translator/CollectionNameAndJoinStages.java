package org.hibernate.omm.translate.translator;

import java.util.List;
import org.hibernate.omm.translate.translator.mongoast.stages.AstStage;

public record CollectionNameAndJoinStages(String collectionName, List<AstStage> joinStages) {}
