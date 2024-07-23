package org.hibernate.omm.query;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.omm.AbstractMongodbIntegrationTests;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nathan Xu
 */
class NativeQueryTests extends AbstractMongodbIntegrationTests {

    @Test
    void testNativeQueryWithoutParameter() {
        var id = 1234L;
        var insertedBook = getSessionFactory().fromTransaction(session -> {
            var book = new Book();
            book.id = 1234L;
            book.title = "War and Peace";
            book.author = "Leo Tolstoy";
            book.publishYear = 1869;
            session.persist(book);
            return book;
        });

        var nativeQuery = "{ find: \"books\", filter: { _id: { $eq: ? } }, projection: { _id: 1, author: 1, publishYear: 1, title: 1 } }";
        getSessionFactory().inTransaction(session -> {
            var query = session.createNativeQuery(nativeQuery, Book.class);
            query.setParameter(1, id);
            var book = query.getSingleResult();
            assertThat(book).usingRecursiveComparison().isEqualTo(insertedBook);
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

        String title;

        String author;

        int publishYear;

    }
}
