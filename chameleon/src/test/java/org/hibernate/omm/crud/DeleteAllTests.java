package org.hibernate.omm.crud;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.SessionFactory;
import org.hibernate.omm.extension.ChameleonExtension;
import org.hibernate.omm.extension.SessionFactoryInjected;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
@ExtendWith(ChameleonExtension.class)
class DeleteAllTests {

    @SessionFactoryInjected
    SessionFactory sessionFactory;

    @Test
    void test_delete_all() {
        sessionFactory.inTransaction(session -> {
            var book1 = new Book(1L);
            var book2 = new Book(2L);
            var book3 = new Book(3L);
            session.persist(book1);
            session.persist(book2);
            session.persist(book3);
        });
        sessionFactory.inTransaction(session -> {
            var query = session.createMutationQuery("delete from Book");
            Assertions.assertDoesNotThrow(query::executeUpdate);
        });
        sessionFactory.inTransaction(session -> {
            var query = session.createSelectionQuery("from Book", Book.class);
            assertThat(query.getResultList()).isEmpty();
        });
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
