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
public class MongodbAdditionalMappingContributor implements AdditionalMappingContributor {

    @Override
    public String getContributorName() {
        return "mongodb_additional_mapping";
    }

    @Override
    public void contribute(final AdditionalMappingContributions contributions, final InFlightMetadataCollector metadata, final ResourceStreamLocator resourceStreamLocator, final MetadataBuildingContext buildingContext) {
        metadata.getEntityBindings().forEach(MongodbAdditionalMappingContributor::setIdentifierColumnName);
    }

    private static void setIdentifierColumnName(PersistentClass persistentClass) {
        var identifier = persistentClass.getIdentifier();
        if (identifier.getColumnSpan() != 1) {
            throw new NotSupportedRuntimeException("Mongodb collection _id field can't span multiple columns: " + identifier);
        }
        identifier.getColumns().get(0).setName("_id");
    }
}
