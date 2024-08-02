package org.hibernate.omm.mapping;

import com.mongodb.client.model.Filters;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.bson.Document;
import org.hibernate.omm.AbstractMongodbIntegrationTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nathan Xu
 */
class ComplexEmbeddedArrayFieldTests extends AbstractMongodbIntegrationTests {

    @Test
    @DisplayName("when complex collection field is persisted, it should be saved as embedded array in MongoDB")
    void test_persist_into_mongo_embedded_complex_array_field() {
        getSessionFactory().fromTransaction(session -> {
            var movie = new Movie(1, List.of(new Tag("first")));
            session.persist(movie);
            return movie;
        });

        Document doc = getMongoDatabase().getCollection("movies")
                .find(Filters.eq(1)).first();

        assertThat(doc).isEqualTo(Document.parse("""
                {
                   _id: 1,
                   tags: [{tag: "first"}]
                }"""));
    }

    @Override
    public List<Class<?>> getAnnotatedClasses() {
        return List.of(Movie.class, Tag.class);
    }

    @Entity(name = "Movie")
    @Table(name = "movies")
    static class Movie {
        public Movie() {
        }

        public Movie(final Integer id, final List<Tag> tags) {
            this.id = id;
            this.tags = tags;
        }

        @Id
        Integer id;

        @ElementCollection
        List<Tag> tags;
    }

    @Embeddable
    static class Tag {
        public Tag() {
        }

        public Tag(final String tag) {
            this.tag = tag;
        }

        String tag;
    }
}
