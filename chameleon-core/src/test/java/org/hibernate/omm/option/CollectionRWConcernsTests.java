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

package org.hibernate.omm.option;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.hibernate.SessionFactory;
import org.hibernate.omm.annotations.ReadConcern;
import org.hibernate.omm.annotations.WriteConcern;
import org.hibernate.omm.extension.CommandRecorderInjected;
import org.hibernate.omm.extension.MongoIntegrationTest;
import org.hibernate.omm.extension.SessionFactoryInjected;
import org.hibernate.omm.service.CommandRecorder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
@MongoIntegrationTest
class CollectionRWConcernsTests {

    @SessionFactoryInjected
    SessionFactory sessionFactory;

    @CommandRecorderInjected
    CommandRecorder commandRecorder;

    @Test
    void test_read_write_concerns() {
        sessionFactory.inTransaction(session -> {
            final var book = new Book();
            book.id = 1;
            book.title = "A Study in Scarlet";
            book.author = "Arthur Conan Doyle";
            session.persist(book);
        });

        sessionFactory.inTransaction(session -> {
            final var book = new Book();
            session.load(book, 1);
        });

        assertThat(commandRecorder.getCommandsRecorded()).satisfiesExactly(
                firstCommand -> assertThat(firstCommand)
                        .doesNotContainKey("readConcern")
                        .containsEntry("writeConcern", BsonDocument.parse("{w: 1}")),
                secondCommand -> assertThat(secondCommand)
                        .doesNotContainKey("writeConcern")
                        .containsEntry("readConcern", new BsonDocument("level", new BsonString("local")))
        );
    }

    @Entity(name = "Book")
    @Table(name = "books")
    @ReadConcern("local")
    @WriteConcern("w1")
    static class Book {
        @Id
        int id;

        String title;

        String author;
    }
}
