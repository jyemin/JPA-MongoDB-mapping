package org.hibernate.omm.mapping;

import com.mongodb.client.model.Filters;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.omm.AbstractMongodbIntegrationTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
            var movie = new Movie();
            movie.id = id;
            movie.title = "Gone with Wind";
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
            var movie = new Movie();
            movie.id = id;
            movie.title = "Gone with Wind";
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

    @Override
    public List<Class<?>> getAnnotatedClasses() {
        return List.of(Movie.class);
    }

    @Entity(name = "Movie")
    @Table(name = "movies")
    static class Movie {
        @Id
        Integer id;

        String title;
        List<String> tags;

    }
}
