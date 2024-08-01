package org.hibernate.omm.crud;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.omm.AbstractMongodbIntegrationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
class DeleteAllTests extends AbstractMongodbIntegrationTests {

    @Test
    void test_delete_all() {
        getSessionFactory().inTransaction(session -> {
            var book1 = new Book(1L);
            var book2 = new Book(2L);
            var book3 = new Book(3L);
            session.persist(book1);
            session.persist(book2);
            session.persist(book3);
        });
        getSessionFactory().inTransaction(session -> {
            var query = session.createMutationQuery("delete from Book");
            Assertions.assertDoesNotThrow(query::executeUpdate);
        });
        getSessionFactory().inTransaction(session -> {
            var query = session.createSelectionQuery("from Book", Book.class);
            assertThat(query.getResultList()).isEmpty();
        });
    }

    @Override
    public List<Class<?>> getAnnotatedClasses() {
        return List.of(Book.class);
    }

    @Entity(name = "Book")
    @Table(name = "books")
    static class Book {
        @Id
        Long id;

        Book(final Long id) {
            this.id = id;
        }
    }
}
