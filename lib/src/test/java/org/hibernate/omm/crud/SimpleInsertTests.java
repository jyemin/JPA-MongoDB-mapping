package org.hibernate.omm.crud;

import java.util.List;

import org.hibernate.omm.AbstractMongodbContainerTests;

import org.junit.jupiter.api.Test;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

class SimpleInsertTests extends AbstractMongodbContainerTests {

	@Test
	void test() {
		getSessionFactory().inTransaction( session -> {
			var book = new Book();
			book.id = 244L;
			book.title = "War and Peace";
			book.author = "Leo Tolstoy";
			book.publishYear = 1869;
			session.persist( book );
		} );
	}

	@Override
	public List<Class<?>> getAnnotatedClasses() {
		return List.of( Book.class );
	}

	@Entity(name = "book")
	static class Book {
		@Id
		Long id;

		String title;

		String author;

		int publishYear;

	}
}
