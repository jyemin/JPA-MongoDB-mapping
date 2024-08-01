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

        // the following Bson command will be issued:
        // { aggregate: "cities", pipeline: [ { $lookup: { from: "countries", localField: "country_id", foreignField: "_id", as: "c2_0" } }, { $unwind: "$c2_0" }, { $match: { _id: { $eq: ? } } }, { $project: { _id: "$_id", c2_0__id: "$c2_0._id", c2_0_name: "$c2_0.name", name: "$name" } } ], cursor: {} }
        // { aggregate: "cities", pipeline: [ { $lookup: { from: "countries", localField: "country_id", foreignField: "_id", as: "c2_0" } }, { $unwind: "$c2_0" }, { $match: { _id: { $eq: ? } } }, { $project: { f0: "$_id", f1: "$c2_0._id", f2: "$c2_0.name", f3: "$name" } } ], cursor: {} }
        getSessionFactory().inTransaction(session -> session.load(city, 2));

        assertThat(city.country).usingRecursiveComparison().isEqualTo(country);
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
