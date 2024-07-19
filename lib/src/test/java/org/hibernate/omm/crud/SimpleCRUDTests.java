package org.hibernate.omm.crud;

import java.util.List;

import org.hibernate.omm.AbstractMongodbContainerTests;

import org.junit.jupiter.api.Test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

class SimpleCRUDTests extends AbstractMongodbContainerTests {

	private final Long id = 245L;

	@Test
	void testInsert() {
		getSessionFactory().inTransaction( session -> {
			var book = new Book();
			book.id = id;
			book.title = "War and Peace";
			book.author = "Leo Tolstoy";
			book.publishYear = 1869;
			session.persist( book );
		} );
	}

	@Test
	void testDelete() {
		testInsert();
		getSessionFactory().inTransaction( session -> {
			var book = session.getReference( Book.class,  id );
			session.remove( book );
		} );
	}

	@Test
	void testLoad() {
		testInsert();
		getSessionFactory().inTransaction( session -> {
			Book book = new Book();
			session.load( book, id );
		} );
	}

	@Test
	void testUpdate() {
		testInsert();
		getSessionFactory().inTransaction( session -> {
			Book book = new Book();
			session.load( book, id );
			book.author = "Fyodor Dostoevsky";
			book.title = "Crime and Punishment";
			book.publishYear = 1866;
			session.merge( book );
		} );
	}

	@Override
	public List<Class<?>> getAnnotatedClasses() {
		return List.of( Book.class );
	}

	@Entity(name = "book")
	static class Book {
		@Id
		@Column(name = "_id")
		Long id;

		String title;

		String author;

		int publishYear;

	}
}
