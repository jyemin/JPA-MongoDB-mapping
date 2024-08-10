package org.hibernate.omm.id;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.SessionFactory;
import org.hibernate.omm.extension.MongoDatabaseInjected;
import org.hibernate.omm.extension.MongoIntegrationTest;
import org.hibernate.omm.extension.SessionFactoryInjected;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nathan Xu
 */
@MongoIntegrationTest
class MongoIdColumnNameTests {

    @SessionFactoryInjected
    static SessionFactory sessionFactory;

    @MongoDatabaseInjected
    static MongoDatabase mongoDatabase;

    final Long id = 21344L;

    @Test
    @DisplayName("when @Id field was not annotated with @Column(name = \"xxx\")")
    void test_implicit_id_column_spec() {

        sessionFactory.inTransaction(session -> {
            var entity = new WithImplicitIdColumnSpec();
            entity.id = id;
            entity.title = "Bible";
            session.persist(entity);
        });

        assertThat(mongoDatabase.getCollection("books").find(Filters.eq(id)).first()).isNotNull();
    }

    @Test
    @DisplayName("when @Id field was annotated with @Column(name = \"xxx\")")
    void test_explicit_id_column_spec() {
        sessionFactory.inTransaction(session -> {
            var entity = new WithExplicitIdColumnSpec();
            entity.id = id;
            entity.title = "Bible";
            session.persist(entity);
        });

        assertThat(mongoDatabase.getCollection("books").find(Filters.eq(id)).first()).isNotNull();
    }


    @Entity(name = "WithImplicitIdColumnSpec")
    @Table(name = "books")
    static class WithImplicitIdColumnSpec {

        @Id
        Long id;

        String title;

    }

    @Entity(name = "WithExplicitIdColumnSpec")
    @Table(name = "books")
    static class WithExplicitIdColumnSpec {

        @Id
        @Column(name = "identifier")
        Long id;

        String title;

    }
}
