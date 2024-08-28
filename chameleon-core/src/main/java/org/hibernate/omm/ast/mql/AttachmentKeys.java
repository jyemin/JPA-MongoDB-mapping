package org.hibernate.omm.ast.mql;

import org.bson.BsonValue;
import org.hibernate.omm.mongoast.AstSortField;
import org.hibernate.omm.mongoast.filters.AstFilter;
import org.hibernate.omm.mongoast.stages.AstProjectStageSpecification;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.mongodb.assertions.Assertions.assertTrue;

public class AttachmentKeys {

    private static final AttachmentKey<String> COLLECTION_NAME = new DefaultAttachmentKey<>("collectionName");
    private static final AttachmentKey<List<AstProjectStageSpecification>> PROJECT_STAGE_SPECIFICATIONS =
            new DefaultAttachmentKey<>("projectStageSpecifications");
    private static final AttachmentKey<String> FIELD_NAME = new DefaultAttachmentKey<>("fieldName");
    private static final AttachmentKey<BsonValue> FIELD_VALUE = new DefaultAttachmentKey<>("fieldValue");
    private static final AttachmentKey<AstFilter> FILTER = new DefaultAttachmentKey<>("filter");
    private static final AttachmentKey<List<AstSortField>> SORT_FIELDS = new DefaultAttachmentKey<>("sortFields");
    private static final AttachmentKey<AstSortField> SORT_FIELD = new DefaultAttachmentKey<>("sortField");

    public static AttachmentKey<String> collectionName() {
       return COLLECTION_NAME;
    }

    public static AttachmentKey<List<AstProjectStageSpecification>> projectStageSpecifications() {
        return PROJECT_STAGE_SPECIFICATIONS;
    }

    public static AttachmentKey<String> fieldName() {
        return FIELD_NAME;
    }

    public static AttachmentKey<BsonValue> fieldValue() {
        return FIELD_VALUE;
    }

    public static AttachmentKey<AstFilter> filter() {
        return FILTER;
    }

    public static AttachmentKey<List<AstSortField>> sortFields() {
        return SORT_FIELDS;
    }

    public static AttachmentKey<AstSortField> sortField() {
        return SORT_FIELD;
    }

    private static final class DefaultAttachmentKey<V> implements AttachmentKey<V> {
        private static final Set<String> AVOID_KEY_DUPLICATION = new HashSet<>();

        private final String key;

        private DefaultAttachmentKey(final String key) {
            assertTrue(AVOID_KEY_DUPLICATION.add(key));
            this.key = key;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            DefaultAttachmentKey<?> that = (DefaultAttachmentKey<?>) o;
            return key.equals(that.key);
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        @Override
        public String toString() {
            return key;
        }
    }

}
