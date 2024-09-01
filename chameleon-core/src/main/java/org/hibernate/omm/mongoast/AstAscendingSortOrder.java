package org.hibernate.omm.mongoast;

import org.bson.BsonWriter;

public record AstAscendingSortOrder() implements AstSortOrder {
    @Override
    public void render(final BsonWriter writer) {
        writer.writeInt32(1);
    }
}
