package org.hibernate.omm.mapping;

import com.mongodb.client.model.Filters;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.omm.AbstractMongodbIntegrationTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nathan Xu
 */
class EmbeddedArrayFieldTests extends AbstractMongodbIntegrationTests {

    final Integer id = 22433;

    @Test
    @DisplayName("when collection field was persisted, it should be saved as embedded array in MongoDB")
    void test_persist_into_mongo_embedded_array_field() {
        var insertedMovie = getSessionFactory().fromTransaction(session -> {
            var movie = new Movie(id);
            movie.tags = List.of("romantic", "classic");
            session.persist(movie);
            return movie;
        });

        var doc = getMongoDatabase().getCollection("movies")
                .find(Filters.eq(id)).first();

        assertThat(doc).isNotNull();
        assertThat(doc.getList("tags", String.class)).isEqualTo(insertedMovie.tags);
    }

    @Test
    @DisplayName("when collection field was persisted, it should be fetched as expected")
    void test_load_from_mongo_embedded_array_field() {
        var insertedMovie = getSessionFactory().fromTransaction(session -> {
            var movie = new Movie(id);
            movie.tags = List.of("romantic", "classic");
            session.persist(movie);
            return movie;
        });
        getSessionFactory().inTransaction(session -> {
            var loadedMovie = new Movie();
            session.load(loadedMovie, id);
            assertThat(loadedMovie).usingRecursiveComparison().isEqualTo(insertedMovie);
        });
    }

    @Test
    @DisplayName("when collection field was queried by some entry, it should return result as expected")
    void test_query_mongo_embedded_array_field() {
        var romanticTag = "romantic";
        var classicTag = "classic";

        getSessionFactory().inTransaction(session -> {
            var movieWithRomanticTagOnly = new Movie(1);
            movieWithRomanticTagOnly.tags = List.of(romanticTag);
            session.persist(movieWithRomanticTagOnly);

            var movieWithClassicTagOnly = new Movie(2);
            movieWithClassicTagOnly.tags = List.of(classicTag);
            session.persist(movieWithClassicTagOnly);

            var movieWithBothTags = new Movie(3);
            movieWithBothTags.tags = List.of(romanticTag, classicTag);
            session.persist(movieWithBothTags);

            var movieWithNoTags = new Movie(4);
            movieWithNoTags.tags = Collections.emptyList();
            session.persist(movieWithNoTags);
        });

        getSessionFactory().inTransaction(session -> {
            var queryStr = "from Movie m where array_contains(m.tags, :tag)";

            var classicMovies = session.createQuery(queryStr, Movie.class).setParameter("tag", classicTag).list();
            assertThat(classicMovies).extracting(Movie::getId).containsExactly(2, 3);

            var romanticMovies = session.createQuery(queryStr, Movie.class).setParameter("tag", romanticTag).list();
            assertThat(romanticMovies).extracting(Movie::getId).containsExactly(1, 3);
        });
    }

    @Override
    public List<Class<?>> getAnnotatedClasses() {
        return List.of(Movie.class);
    }

    @Entity(name = "Movie")
    @Table(name = "movies")
    static class Movie {
        @Id
        Integer id;

        Movie() {}
        Movie(Integer id) {
            this.id = id;
        }

        Integer getId() {
            return id;
        }

        List<String> tags;

    }
}
