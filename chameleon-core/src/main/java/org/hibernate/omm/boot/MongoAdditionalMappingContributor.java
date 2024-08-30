/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.omm.boot;

import com.mongodb.ReadConcernLevel;
import org.hibernate.boot.ResourceStreamLocator;
import org.hibernate.boot.spi.AdditionalMappingContributions;
import org.hibernate.boot.spi.AdditionalMappingContributor;
import org.hibernate.boot.spi.InFlightMetadataCollector;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.omm.annotations.ReadConcern;
import org.hibernate.omm.annotations.WriteConcern;
import org.hibernate.omm.exception.NotSupportedRuntimeException;
import org.hibernate.omm.service.CollectionRWConcerns;

import java.util.HashMap;

/**
 * @author Nathan Xu
 * @since 1.0
 */
public class MongoAdditionalMappingContributor implements AdditionalMappingContributor {
    private static final String ID_FIELD = "_id";

    public static CollectionRWConcerns collectionRWConcerns;

    @Override
    public String getContributorName() {
        return "mongo_additional_mapping";
    }

    @Override
    public void contribute(final AdditionalMappingContributions contributions, final InFlightMetadataCollector metadata, final ResourceStreamLocator resourceStreamLocator, final MetadataBuildingContext buildingContext) {
        metadata.getEntityBindings().forEach(MongoAdditionalMappingContributor::setIdentifierColumnName);
        final var readConcernsMap = new HashMap<String, com.mongodb.ReadConcern>();
        final var writeConcernsMap = new HashMap<String, com.mongodb.WriteConcern>();
        metadata.getEntityBindings().forEach(persistentClass -> {
            final var mappedClass = persistentClass.getMappedClass();
            final var collectionName = persistentClass.getTable().getName();
            final var readConcernAnnotation = mappedClass.getAnnotation(ReadConcern.class);
            final var writeConcernAnnotation = mappedClass.getAnnotation(WriteConcern.class);
            if (readConcernAnnotation != null) {
                readConcernsMap.put(collectionName, new com.mongodb.ReadConcern(ReadConcernLevel.fromString(readConcernAnnotation.value())));
            }
            if (writeConcernAnnotation != null) {
                writeConcernsMap.put(collectionName, com.mongodb.WriteConcern.valueOf(writeConcernAnnotation.value()));
            }
        });
        collectionRWConcerns = new CollectionRWConcerns(readConcernsMap, writeConcernsMap);
    }

    private static void setIdentifierColumnName(final PersistentClass persistentClass) {
        var identifier = persistentClass.getIdentifier();
        if (identifier.getColumnSpan() != 1) {
            throw new NotSupportedRuntimeException("Mongodb collection _id field can't span multiple columns for identifier: " + identifier);
        }
        identifier.getColumns().get(0).setName(ID_FIELD);
    }

}
