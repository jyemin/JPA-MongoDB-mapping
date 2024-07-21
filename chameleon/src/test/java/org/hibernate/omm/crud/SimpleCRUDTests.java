package org.hibernate.omm.crud;

import java.util.List;

import org.hibernate.omm.AbstractMongodbContainerTests;
import org.hibernate.query.Query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleCRUDTests extends AbstractMongodbContainerTests {

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
		return getSessionFactory().fromTransaction( session -> {
			var book = new Book();
			book.id = id;
			book.title = "War and Peace";
			book.author = "Leo Tolstoy";
			book.publishYear = 1869;
			session.persist( book );
			return book;
		} );
	}

	void deleteBook() {
		getSessionFactory().inTransaction( session -> {
			Query query = session.createQuery( "delete Book where id = :id" );
			query.setParameter( "id", id );
			query.executeUpdate();
		} );
	}

	@Test
	void testInsert() {
		var insertedBook = insertBook();
		getSessionFactory().inTransaction( session -> {
			var query = session.createQuery( "from Book where id = :id", Book.class );
			query.setParameter( "id", id );
			var book = query.getSingleResult();
			assertThat( book ).usingRecursiveComparison().isEqualTo( insertedBook );
		} );
	}

	@Test
	void testDelete() {
		insertBook();
		getSessionFactory().inTransaction( session -> {
			var book = session.getReference( Book.class, id );
			session.remove( book );
		} );
		getSessionFactory().inTransaction( session -> {
			var query = session.createQuery( "from Book where id = :id" );
			query.setParameter( "id", id );
			assertThat( query.getResultList() ).isEmpty();
		} );
	}

	@Test
	void testLoad() {
		var insertedBook = insertBook();
		getSessionFactory().inTransaction( session -> {
			var book = new Book();
			session.load( book, id );
			assertThat( book ).usingRecursiveComparison().isEqualTo( insertedBook );
		} );
	}

	@Test
	void testQuery() {
		var insertedBook = insertBook();
		getSessionFactory().inTransaction( session -> {
			var query = session.createQuery( "from Book where id = :id", Book.class );
			query.setParameter( "id", id );
			var book = query.getSingleResult();
			assertThat( book ).usingRecursiveComparison().isEqualTo( insertedBook );
		} );
	}

	@Test
	void testUpdate() {
		insertBook();
		String newAuthor = "Fyodor Dostoevsky";
		String newTitle = "Crime and Punishment";
		int newPublishYear = 1866;
		getSessionFactory().inTransaction( session -> {
			var book = new Book();
			session.load( book, id );
			book.author = newAuthor;
			book.title = newTitle;
			book.publishYear = newPublishYear;
			session.persist( book );
		} );
		getSessionFactory().inSession( session -> {
			var query = session.createQuery( "from Book where id = :id", Book.class );
			query.setParameter( "id", id );
			var book = query.getSingleResult();
			assertThat( book.author ).isEqualTo( newAuthor );
			assertThat( book.title ).isEqualTo( newTitle );
			assertThat( book.publishYear ).isEqualTo( newPublishYear );
		} );
	}

	@Override
	public List<Class<?>> getAnnotatedClasses() {
		return List.of( Book.class );
	}

	@Entity(name = "Book")
	@Table(name = "books")
	static class Book {
		@Id
		@Column(name = "_id")
		Long id;

		String title;

		String author;

		int publishYear;

	}
}
