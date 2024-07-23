package org.hibernate.omm.util;

import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentUtilTests {

    @Nested
    @DisplayName("when 'find' document has 'projection' field")
    class ProjectionFieldsTest {

        @Nested
        @DisplayName("when '_id' field is absent from 'projection' doc")
        class IdentifierFieldAbsent {

            @Test
            @DisplayName("then '_id' should be included in query result by default")
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
        @DisplayName("when '_id' field is present in 'projection' doc")
        class IdentifierFieldPresent {

            @ParameterizedTest(name = "when its value is {0}")
            @ValueSource(ints = {1, 0})
            void test_explicit_id_inclusion(int idIncluded) {

                // given
                var projectionMap = new LinkedHashMap<String, Integer>();
                projectionMap.put("name", 1);
                projectionMap.put("address", 0);
                projectionMap.put("_id", idIncluded);
                projectionMap.put("age", 0);
                projectionMap.put("gender", 0);
                projectionMap.put("country", 1);

                var projectionDocument = new Document();
                projectionDocument.putAll(projectionMap);

                // when
                var fieldsIncluded = DocumentUtil.getProjectionFieldsIncluded(projectionDocument);

                // then
                if (idIncluded == 1) {
                    assertThat(fieldsIncluded).containsExactly("name", "_id", "country");
                } else {
                    assertThat(fieldsIncluded).containsExactly("name", "country");
                }

            }

        }

    }

}
