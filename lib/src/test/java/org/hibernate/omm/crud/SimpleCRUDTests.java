package org.hibernate.omm.crud;

import java.util.List;
import java.util.Random;

import org.hibernate.omm.AbstractMongodbContainerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleCRUDTests extends AbstractMongodbContainerTests {

	private Book insertedBook;

	@Test
	void testInsert() {
		getSessionFactory().inTransaction( session -> {
			var book = new Book();
			book.id = new Random().nextLong();
			book.title = "War and Peace";
			book.author = "Leo Tolstoy";
			book.publishYear = 1869;
			book.tags = List.of( "russian", "classic" );
			session.persist( book );
		} );
	}

	@Test
	void testDelete() {
		getSessionFactory().inTransaction( session -> {
			var book = session.getReference( Book.class,  insertedBook.id );
			session.remove( book );
		} );
	}

	@Test
	void testLoad() {
		getSessionFactory().inTransaction( session -> {
			var book = new Book();
			session.load( book, -2587981967077003745L );
			System.out.println( book );
			//assertThat( book ).usingRecursiveComparison().isEqualTo( insertedBook );
		} );
	}

	@Test
	void testQuery() {
		getSessionFactory().inTransaction( session -> {
			var query = session.createQuery( "from Book where id = :id", Book.class );
			query.setParameter( "id", 245L );
			var book = query.getSingleResult();
			assertThat( book ).usingRecursiveComparison().isEqualTo( insertedBook );
		} );
	}

	@Test
	void testUpdate() {
		getSessionFactory().inTransaction( session -> {
			var book = new Book();
			session.load( book, insertedBook.id );
			book.author = "Fyodor Dostoevsky";
			book.title = "Crime and Punishment";
			book.publishYear = 1866;
			session.persist( book );
		} );
	}

	Book insertBook() {
		return getSessionFactory().fromSession( session -> {
			var book = new Book();
			book.id = new Random().nextLong();
			book.title = "War and Peace";
			book.author = "Leo Tolstoy";
			book.publishYear = 1869;
			//book.tags = new String[] { "russian", "classic" };
			session.persist( book );
			return book;
		} );
	}

	@Override
	public List<Class<?>> getAnnotatedClasses() {
		return List.of( Book.class );
	}

	@Entity(name = "Book")
	@Table(name = "book")
	static class Book {
		@Id
		@Column(name = "_id")
		Long id;

		String title;

		String author;

		int publishYear;

		List<String> tags;

	}
}
