package org.hibernate.omm.crud;


import java.util.Objects;

import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.junit.jupiter.api.Test;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@DomainModel(annotatedClasses = SimpleInsertTests.Book.class)
@SessionFactory
class SimpleInsertTests {
	@Test
	void test(SessionFactoryScope scope) {
		scope.inTransaction(
				session -> {
					var book = new Book();
					book.id = 1L;
					book.name = "War and Peace";
					book.author = "Leo Tolstoy";
					book.publishYear = 1867;
					session.persist( book );
				}
		);
	}

	@Entity(name = "book")
	@Table(name = "just_for_testing")
	static class Book {
		@Id
		Long id;

		String name;

		String author;

		int publishYear;

	}
}
