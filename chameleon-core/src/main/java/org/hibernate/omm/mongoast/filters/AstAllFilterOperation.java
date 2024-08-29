package org.hibernate.omm.mongoast.filters;
import org.bson.BsonUndefined;
import org.bson.BsonWriter;
import org.hibernate.omm.mongoast.AstNodeType;

public record AstAllFilterOperation(BsonUndefined value) implements AstFilterOperation {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.AllFilterOperation;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeStartDocument();
        if (value != null) {
            writer.writeUndefined("$all");
        }
        writer.writeEndDocument();
    }
}
