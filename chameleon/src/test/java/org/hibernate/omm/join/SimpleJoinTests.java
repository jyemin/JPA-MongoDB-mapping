package org.hibernate.omm.join;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.omm.AbstractMongodbIntegrationTests;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SimpleJoinTests extends AbstractMongodbIntegrationTests {

    @Test
    void test() {
        getSessionFactory().inTransaction(session -> {
            Country country = new Country(1);
            country.name = "China";
            City city = new City(2);
            city.country = country;
            city.name = "Beijing";
            session.persist(country);
            session.persist(city);
        });
        getSessionFactory().inTransaction(session -> {
            City city = new City(2);
            session.load(city, 2);
        });
    }
    @Override
    public List<Class<?>> getAnnotatedClasses() {
        return List.of(Country.class, City.class);
    }

    @Entity(name = "Country")
    @Table(name = "countries")
    static class Country {
        @Id
        int id;
        String name;

        Country(int id) {
            this.id = id;
        }
    }

    @Entity(name = "City")
    @Table(name = "cities")
    static class City {
        @Id
        int id;

        @ManyToOne
        Country country;

        String name;

        City(int id) {
            this.id = id;
        }
    }

}
