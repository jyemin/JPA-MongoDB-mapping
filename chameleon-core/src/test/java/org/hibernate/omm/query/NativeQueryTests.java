package org.hibernate.omm.query;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.SessionFactory;
import org.hibernate.omm.extension.MongoIntegrationTest;
import org.hibernate.omm.extension.SessionFactoryInjected;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nathan Xu
 */
@MongoIntegrationTest
class NativeQueryTests {

    @SessionFactoryInjected
    static SessionFactory sessionFactory;

    @Test
    void testNativeQueryWithoutParameter() {
        var insertedBook = sessionFactory.fromTransaction(session -> {
            var book = new Book();
            book.id = 1234L;
            book.title = "War and Peace";
            book.author = "Leo Tolstoy";
            book.publishYear = 1869;
            session.persist(book);
            return book;
        });

        var nativeQuery = "{ aggregate: \"books\", pipeline: [" +
                "{ $match :  { _id: { $eq: 1234 } } }, " +
                "{ $project: { _id: 1, publishYear: 1, title: 1, author: 1 } }" +
                "] }";
        sessionFactory.inTransaction(session -> {
            var query = session.createNativeQuery(nativeQuery, Book.class);
            var book = query.getSingleResult();
            assertThat(book).usingRecursiveComparison().isEqualTo(insertedBook);
        });
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
