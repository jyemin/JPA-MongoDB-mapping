/*
 *
 * Copyright 2008-present MongoDB, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.hibernate.omm.array;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Struct;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.omm.extension.MongoIntegrationTest;
import org.hibernate.omm.extension.SessionFactoryInjected;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Nathan Xu
 * @author Jeff Yemin
 * @since 1.0.0
 */
@MongoIntegrationTest
class StructTests {

    @SessionFactoryInjected
    SessionFactory sessionFactory;

    @Test
    void test_persist() {
        sessionFactory.inTransaction(session -> {
            var tag = new TagsByAuthor();
            tag.author = "Nathan";
            tag.tags = List.of("comedy", "drama");
            var movie = new Movie();
            movie.title = "Forrest Gump";
            movie.tagsByAuthor = List.of(tag);
            session.persist(movie);
        });
    }

    @Entity(name = "Movie")
    static class Movie {
        @Id @GeneratedValue
        @UuidGenerator
        UUID id;

        String title;

        List<TagsByAuthor> tagsByAuthor;
    }

    @Embeddable
    @Struct(name = "TagsByAuthor")
    static class TagsByAuthor {

        @Column(name = "commenter")
        String author;

        List<String> tags;

        @Override
        public String toString() {
            return "TagsByAuthor{" +
                    "author='" + author + '\'' +
                    ", tags=" + tags +
                    '}';
        }
    }
}
