package org.hibernate.omm.query;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.SessionFactory;
import org.hibernate.omm.extension.ChameleonExtension;
import org.hibernate.omm.extension.SessionFactoryInjected;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nathan Xu
 */
@ExtendWith(ChameleonExtension.class)
class NativeQueryTests {

    @SessionFactoryInjected
    static SessionFactory sessionFactory;

    @Test
    void testNativeQueryWithoutParameter() {
        var id = 1234L;
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
                "{ $match :  { _id: { $eq: ? } } }, " +
                "{ $project: { _id: 1, publishYear: 1, title: 1, author: 1 } }" +
                "] }";
        sessionFactory.inTransaction(session -> {
            var query = session.createNativeQuery(nativeQuery, Book.class);
            query.setParameter(1, id);
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
