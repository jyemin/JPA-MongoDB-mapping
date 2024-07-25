package org.hibernate.omm.crud;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.omm.AbstractMongodbIntegrationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class DeleteAllTests extends AbstractMongodbIntegrationTests {

    @Test
    void test_delete_all() {
        getSessionFactory().inTransaction(session -> {
            var query = session.createMutationQuery("delete from Book");
            Assertions.assertDoesNotThrow(query::executeUpdate);
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
    }
}
