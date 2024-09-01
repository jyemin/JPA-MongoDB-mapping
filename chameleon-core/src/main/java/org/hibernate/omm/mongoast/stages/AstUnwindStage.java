package org.hibernate.omm.mongoast.stages;

import org.bson.BsonWriter;
import org.hibernate.omm.mongoast.AstNodeType;

public record AstUnwindStage(String path) implements AstStage {
    @Override
    public AstNodeType nodeType() {
        return AstNodeType.UnwindStage;
    }

    @Override
    public void render(final BsonWriter writer) {
        writer.writeStartDocument();
        writer.writeName("$unwind");

        writer.writeString('$' + path);

        writer.writeEndDocument();
    }
}
