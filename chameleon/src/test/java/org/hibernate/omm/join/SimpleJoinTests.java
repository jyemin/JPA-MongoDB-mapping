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

/**
 * @author Nathan Xu
 */
class SimpleJoinTests extends AbstractMongodbIntegrationTests {

    @Test
    void test() {
        var country = new Country();
        country.id = 1;
        country.name = "China";
        var province = new Province();
        province.id = 2;
        province.country = country;
        province.name = "Jilin";

        getSessionFactory().inTransaction(session -> {
            City city = new City();
            city.id = 3;
            city.province = province;
            city.name = "Changchun";
            session.persist(country);
            session.persist(province);
            session.persist(city);
        });

        City city = new City();

        // the following Bson command will be issued:
        // { aggregate: "cities", pipeline: [ { $lookup: { from: "province", localField: "province_id", foreignField: "_id", as: "p1_0", pipeline: [ { $lookup: { from: "countries", localField: "country_id", foreignField: "_id", as: "c2_0" } }, { $unwind: "$c2_0" } ] } }, { $unwind: "$p1_0" }, { $match: { _id: { $eq: ? } } }, { $project: { f0: "$_id", f1: "$name", f2: "$p1_0._id", f3: "$p1_0.c2_0._id", f4: "$p1_0.c2_0.name", f5: "$p1_0.name", _id: 0 } } ], cursor: {} }
        getSessionFactory().inTransaction(session -> session.load(city, 3));

        assertThat(city.province).usingRecursiveComparison().isEqualTo(province);
        assertThat(city.province.country).usingRecursiveComparison().isEqualTo(country);
    }

    @Override
    public List<Class<?>> getAnnotatedClasses() {
        return List.of(Country.class, Province.class, City.class);
    }

    @Entity(name = "Country")
    @Table(name = "countries")
    static class Country {
        @Id
        int id;
        String name;
    }

    @Entity(name = "Province")
    @Table(name = "province")
    static class Province {
        @Id
        int id;
        @ManyToOne
        @Fetch(FetchMode.JOIN)
        Country country;
        String name;
    }

    @Entity(name = "City")
    @Table(name = "cities")
    static class City {
        @Id
        int id;

        @ManyToOne
        @Fetch(FetchMode.JOIN) // not needed for it is default config; kind of doc
        Province province;

        String name;
    }

}
