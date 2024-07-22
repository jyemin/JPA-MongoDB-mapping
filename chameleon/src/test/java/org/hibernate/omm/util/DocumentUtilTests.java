package org.hibernate.omm.util;

import org.bson.Document;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentUtilTests {

    @Nested
    class ProjectionFieldsTest {

        @Nested
        class IdentifierFieldAbsent {

            @Test
            void test_id_should_be_included() {

                // given
                var projectionMap = new LinkedHashMap<String, Integer>();
                projectionMap.put("name", 1);
                projectionMap.put("address", 0);
                projectionMap.put("age", 0);
                projectionMap.put("gender", 0);
                projectionMap.put("country", 1);

                var projectionDocument = new Document();
                projectionDocument.putAll(projectionMap);

                // when
                var fieldsIncluded = DocumentUtil.getProjectionFieldsIncluded(projectionDocument);

                // then
                assertThat(fieldsIncluded).containsExactly("_id", "name", "country");
            }

        }

        @Nested
        class IdentifierFieldPresent {

            @ParameterizedTest
            @ValueSource(booleans = {true, false})
            void test_explicit_id_inclusion(boolean idIncluded) {

                // given
                var projectionMap = new LinkedHashMap<String, Integer>();
                projectionMap.put("name", 1);
                projectionMap.put("address", 0);
                projectionMap.put("_id", idIncluded ? 1 : 0);
                projectionMap.put("age", 0);
                projectionMap.put("gender", 0);
                projectionMap.put("country", 1);

                var projectionDocument = new Document();
                projectionDocument.putAll(projectionMap);

                // when
                var fieldsIncluded = DocumentUtil.getProjectionFieldsIncluded(projectionDocument);

                // then
                if (idIncluded) {
                    assertThat(fieldsIncluded).containsExactly("name", "_id", "country");
                } else {
                    assertThat(fieldsIncluded).containsExactly("name", "country");
                }

            }

        }

    }

}
