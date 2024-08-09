package org.hibernate.omm.mapping;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Formula;
import org.hibernate.omm.extension.ChameleonExtension;
import org.hibernate.omm.extension.MongoDatabaseInjected;
import org.hibernate.omm.extension.SessionFactoryInjected;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
@ExtendWith(ChameleonExtension.class)
class FormulaTests {

    @SessionFactoryInjected
    SessionFactory sessionFactory;

    @MongoDatabaseInjected
    MongoDatabase mongoDatabase;

    @Test
    void test() {
        var id = 1234L;
        var credit = 200.0;
        var rate = 0.01;
        sessionFactory.inTransaction(session -> {
            var account = new Account(id);
            account.credit = credit;
            account.rate = rate;
            session.persist(account);
        });

        var doc = mongoDatabase.getCollection("accounts")
                .find(Filters.eq(id)).first();

        assertThat(doc).isNotNull();
        assertThat(doc.keySet()).doesNotContain("interest");

        sessionFactory.inTransaction(session -> {
            var loadedAccount = new Account();
            session.load(loadedAccount, id);
            assertThat(loadedAccount.interest).isEqualTo(credit * rate);
        });
    }

    @Entity(name = "Account")
    @Table(name = "accounts")
    static class Account {

        @Id
        Long id;

        Double credit;

        Double rate;

        @Formula("{ $multiply: [ \"$credit\", \"$rate\" ] }")
        Double interest;

        Account() {}
        Account(Long id) {
            this.id = id;
        }
    }
}
