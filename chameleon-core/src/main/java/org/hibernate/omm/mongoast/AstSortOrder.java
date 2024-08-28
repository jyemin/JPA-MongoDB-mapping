package org.hibernate.omm.mongoast;

import org.bson.BsonWriter;

public interface AstSortOrder {
    void render(BsonWriter writer);
}
