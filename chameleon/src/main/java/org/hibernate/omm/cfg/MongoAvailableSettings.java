package org.hibernate.omm.cfg;

/**
 * Contains all the OMM specific configuration entries.
 * <p>
 * As long as OMM clients provided the mandatory entries listed in this interface, they are totally set.
 *
 * @author Nathan Xu
 * @since 1.0.0
 */
public interface MongoAvailableSettings {
    String MONGODB_CONNECTION_URL = "mongodb.connection.url";
    String MONGODB_DATABASE = "mongodb.database";

}
