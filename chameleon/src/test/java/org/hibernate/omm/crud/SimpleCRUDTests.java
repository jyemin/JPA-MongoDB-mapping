package org.hibernate.omm.crud;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.omm.AbstractMongodbIntegrationTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleCRUDTests extends AbstractMongodbIntegrationTests {

    private final Long id = 1234L;

    @BeforeEach
    void setUp() {
        deleteBook();
    }

    @AfterEach
    void tearDown() {
        deleteBook();
    }

    Book insertBook() {
        return getSessionFactory().fromTransaction(session -> {
            var book = new Book();
            book.id = id;
            book.title = "War and Peace";
            book.author = "Leo Tolstoy";
            book.publishYear = 1869;
            session.persist(book);
            return book;
        });
    }

    void deleteBook() {
        getSessionFactory().inTransaction(session -> {
            var query = session.createQuery("delete Book where id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
        });
    }

    @Test
    void testInsert() {

        // the following JSON command will be issued:
        // { insert: "books", documents: [ { author: ?, publishYear: ?, title: ?, _id: ? } ] }
        var insertedBook = insertBook();

        getSessionFactory().inTransaction(session -> {
            var query = session.createQuery("from Book where id = :id", Book.class);
            query.setParameter("id", id);
            var book = query.getSingleResult();
            assertThat(book).usingRecursiveComparison().isEqualTo(insertedBook);
        });
    }

    @Test
    void testDelete() {

        insertBook();

        // the following JSON command will be issued:
        // { delete: "books", deletes: [ { q: { _id: { $eq: ? } }, limit: 0 } ] }
        getSessionFactory().inTransaction(session -> {
            var book = session.getReference(Book.class, id);
            session.remove(book);
        });
        getSessionFactory().inTransaction(session -> {
            var query = session.createQuery("from Book where id = :id");
            query.setParameter("id", id);
            assertThat(query.getResultList()).isEmpty();
        });
    }

    @Test
    void testLoad() {

        var insertedBook = insertBook();

        // the following JSON command will be issued:
        // { find: "books", filter: { _id: { $eq: ? } }, projection: { _id: 1, author: 1, publishYear: 1, title: 1 } }
        getSessionFactory().inTransaction(session -> {
            var book = new Book();
            session.load(book, id);
            assertThat(book).usingRecursiveComparison().isEqualTo(insertedBook);
        });
    }

    @Test
    void testQuery() {

        var insertedBook = insertBook();

        // the following JSON command will be issued:
        // { find: "books", filter: { _id: { $eq: ? } }, projection: { _id: 1, author: 1, publishYear: 1, title: 1 } }
        getSessionFactory().inTransaction(session -> {
            var query = session.createQuery("from Book where id = :id", Book.class);
            query.setParameter("id", id);
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
        getSessionFactory().inTransaction(session -> {
            var book = new Book();
            session.load(book, id);
            book.author = newAuthor;
            book.title = newTitle;
            book.publishYear = newPublishYear;
            session.persist(book);
        });
        getSessionFactory().inSession(session -> {
            var query = session.createQuery("from Book where id = :id", Book.class);
            query.setParameter("id", id);
            var book = query.getSingleResult();
            assertThat(book.author).isEqualTo(newAuthor);
            assertThat(book.title).isEqualTo(newTitle);
            assertThat(book.publishYear).isEqualTo(newPublishYear);
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
