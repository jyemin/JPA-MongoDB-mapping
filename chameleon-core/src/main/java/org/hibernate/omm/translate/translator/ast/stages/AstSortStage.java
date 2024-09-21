package org.hibernate.omm.translate.translator.ast.stages;

import org.bson.BsonWriter;
import org.hibernate.omm.translate.translator.ast.AstNodeType;
import org.hibernate.omm.translate.translator.ast.AstSortField;

import java.util.List;

public record AstSortStage(List<AstSortField> sortFields) implements AstStage {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.SortStage;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeStartDocument();
        writer.writeName("$sort");
        writer.writeStartDocument();
        sortFields.forEach(sortField -> sortField.render(writer));
        writer.writeEndDocument();
        writer.writeEndDocument();
    }
}
