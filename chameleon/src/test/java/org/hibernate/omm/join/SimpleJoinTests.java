package org.hibernate.omm.join;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.omm.AbstractMongodbIntegrationTests;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleJoinTests extends AbstractMongodbIntegrationTests {

    @Test
    void test() {
        var country = new Country();
        country.id = 1;
        country.name = "China";
        getSessionFactory().inTransaction(session -> {
            City city = new City();
            city.id = 2;
            city.country = country;
            city.name = "Beijing";
            session.persist(country);
            session.persist(city);
        });

        City city = new City();
        getSessionFactory().inTransaction(session -> {
            session.load(city, 2);
        });

        assertThat(city.country).usingRecursiveComparison().isEqualTo(country); // outside of session so lazy loading proxy would fail
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
    }

    @Entity(name = "City")
    @Table(name = "cities")
    static class City {
        @Id
        int id;

        @ManyToOne
        @Fetch(FetchMode.JOIN) // not needed for it is default config; kind of doc
        Country country;

        String name;
    }

}
