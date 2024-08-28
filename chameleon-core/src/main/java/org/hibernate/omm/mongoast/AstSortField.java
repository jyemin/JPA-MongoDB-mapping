package org.hibernate.omm.mongoast;

import org.bson.BsonWriter;

public record AstSortField(String path, AstSortOrder order) {
    public void render(BsonWriter writer) {
        writer.writeName(path);
        order.render(writer);
    }
}
