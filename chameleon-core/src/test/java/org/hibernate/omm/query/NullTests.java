package org.hibernate.omm.query;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.SessionFactory;
import org.hibernate.omm.extension.MongoIntegrationTest;
import org.hibernate.omm.extension.SessionFactoryInjected;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jeff Yemin
 * @author Nathan Xu
 * @since 1.0.0
 */
@MongoIntegrationTest
class NullTests {

    @SessionFactoryInjected
    SessionFactory sessionFactory;

    @Test
    void test() {
        var foo = new Foo();
        foo.id = 1L;
        foo.possiblyNull = "s";

        var foo2 = new Foo();
        foo2.id = 2L;
        foo2.possiblyNull = null;

        sessionFactory.inTransaction(session -> {
            session.persist(foo);
            session.persist(foo2);
        });

        sessionFactory.inSession(session -> {
            var query = session.createQuery("from Foo where possiblyNull is null", Foo.class);
            var res = query.getSingleResult();
            assertThat(res).usingRecursiveComparison().isEqualTo(foo2);

            query = session.createQuery("from Foo where possiblyNull is not null", Foo.class);
            res = query.getSingleResult();
            assertThat(res).usingRecursiveComparison().isEqualTo(foo);
        });
    }

    @Entity(name = "Foo")
    static class Foo {
        @Id
        Long id;
        String possiblyNull;
   }
}
