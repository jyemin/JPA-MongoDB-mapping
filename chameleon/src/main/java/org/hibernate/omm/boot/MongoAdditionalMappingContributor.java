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

import org.hibernate.boot.ResourceStreamLocator;
import org.hibernate.boot.spi.AdditionalMappingContributions;
import org.hibernate.boot.spi.AdditionalMappingContributor;
import org.hibernate.boot.spi.InFlightMetadataCollector;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.omm.exception.NotSupportedRuntimeException;

/**
 * @author Nathan Xu
 * @since 1.0
 */
public class MongoAdditionalMappingContributor implements AdditionalMappingContributor {
    private static final String ID_FIELD = "_id";

    @Override
    public String getContributorName() {
        return "mongo_additional_mapping";
    }

    @Override
    public void contribute(final AdditionalMappingContributions contributions, final InFlightMetadataCollector metadata, final ResourceStreamLocator resourceStreamLocator, final MetadataBuildingContext buildingContext) {
        metadata.getEntityBindings().forEach(MongoAdditionalMappingContributor::setIdentifierColumnName);
    }

    private static void setIdentifierColumnName(final PersistentClass persistentClass) {
        var identifier = persistentClass.getIdentifier();
        if (identifier.getColumnSpan() != 1) {
            throw new NotSupportedRuntimeException("Mongodb collection _id field can't span multiple columns for identifier: " + identifier);
        }
        identifier.getColumns().get(0).setName(ID_FIELD);
    }
}
