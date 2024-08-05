package org.hibernate.omm.type;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.bson.types.ObjectId;
import org.hibernate.SessionFactory;
import org.hibernate.omm.extension.SessionFactoryExtension;
import org.hibernate.omm.extension.SessionFactoryInjected;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
@ExtendWith(SessionFactoryExtension.class)
class ObjectIdTypeTests {

    final ObjectId objectId = ObjectId.get();

    @SessionFactoryInjected
    private SessionFactory sessionFactory;

    Book insertBook() {
        return sessionFactory.fromTransaction(session -> {
            var book = new Book();
            book.id = objectId;
            book.title = "War and Peace";
            book.author = "Leo Tolstoy";
            book.publishYear = 1869;
            session.persist(book);
            return book;
        });
    }

    @Test
    void testInsert() {

        // the following JSON command will be issued:
        // { insert: "books", documents: [ { author: ?, publishYear: ?, title: ?, _id: ? } ] }
        var insertedBook = insertBook();

        sessionFactory.inTransaction(session -> {
            var query = session.createQuery("from Book where id = :id", Book.class);
            query.setParameter("id", objectId);
            var book = query.getSingleResult();
            assertThat(book).usingRecursiveComparison().isEqualTo(insertedBook);
        });
    }

    @Test
    void testDelete() {

        insertBook();

        // the following JSON command will be issued:
        // { delete: "books", deletes: [ { q: { _id: { $eq: ? } }, limit: 0 } ] }
        sessionFactory.inTransaction(session -> {
            var book = session.getReference(Book.class, objectId);
            session.remove(book);
        });
        sessionFactory.inTransaction(session -> {
            var query = session.createSelectionQuery("from Book where id = :id", Book.class);
            query.setParameter("id", objectId);
            assertThat(query.getResultList()).isEmpty();
        });
    }

    @Test
    void testLoad() {

        var insertedBook = insertBook();

        // the following JSON command will be issued:
        // { aggregate: "books", pipeline: [ { $match: { _id: { $eq: ? } }}, { $project: { _id: 1, author: 1, publishYear: 1, title: 1 } } ], cursor: {} }
        sessionFactory.inTransaction(session -> {
            var book = new Book();
            session.load(book, objectId);
            assertThat(book).usingRecursiveComparison().isEqualTo(insertedBook);
        });
    }

    @Test
    void testQuery() {

        var insertedBook = insertBook();

        // the following JSON command will be issued:
        // { aggregate: "books", pipeline: [ { $match: { _id: { $eq: ? } }}, { $project: { _id: 1, author: 1, publishYear: 1, title: 1 } } ], cursor: {} }
        sessionFactory.inTransaction(session -> {
            var query = session.createQuery("from Book where id = :id", Book.class);
            query.setParameter("id", objectId);
            var book = query.getSingleResult();
            assertThat(book).usingRecursiveComparison().isEqualTo(insertedBook);
        });
    }

    @Test
    void testUpdate() {
        insertBook();
        var newAuthor = "Fyodor Dostoevsky";
        var newTitle = "Crime and Punishment";
        int newPublishYear = 1866;

        // the following JSON command will be issued:
        // { update: "books", updates: [ { q: { _id: { $eq: ? } }, u: { $set: { author: ?, publishYear: ?, title: ? } } } ] }
        sessionFactory.inTransaction(session -> {
            var book = new Book();
            session.load(book, objectId);
            book.author = newAuthor;
            book.title = newTitle;
            book.publishYear = newPublishYear;
            session.persist(book);
        });
        sessionFactory.inSession(session -> {
            var query = session.createQuery("from Book where id = :id", Book.class);
            query.setParameter("id", objectId);
            var book = query.getSingleResult();
            assertThat(book.author).isEqualTo(newAuthor);
            assertThat(book.title).isEqualTo(newTitle);
            assertThat(book.publishYear).isEqualTo(newPublishYear);
        });
    }

    @Entity(name = "Book")
    @Table(name = "books")
    static class Book {

        @Id
        ObjectId id;

        String title;

        String author;

        int publishYear;

    }
}
