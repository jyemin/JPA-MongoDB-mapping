package org.hibernate.omm.util;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public final class DocumentUtil {
    private DocumentUtil() {
    }

    public static List<String> getProjectionFieldsIncluded(Document projectionDoc) {
        if (projectionDoc == null) {
            return null;
        }
        List<String> fields = new ArrayList<>(projectionDoc.size() + 1);
        if (!projectionDoc.containsKey("_id")) {
            fields.add("_id");
        }
        for (var entry : projectionDoc.entrySet()) {
            if (Integer.valueOf(1).equals(entry.getValue()) || Boolean.valueOf(true).equals(entry.getValue())) {
                fields.add(entry.getKey());
            }
        }
        return fields;
    }
}
