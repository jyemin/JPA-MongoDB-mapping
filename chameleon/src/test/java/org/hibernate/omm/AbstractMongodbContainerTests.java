package org.hibernate.omm;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.omm.cfg.MongodbAvailableSettings;

public abstract class AbstractMongodbContainerTests {
	private SessionFactory sessionFactory;

	public abstract List<Class<?>> getAnnotatedClasses();

	protected SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			Configuration cfg = new Configuration();
			getAnnotatedClasses().forEach( cfg::addAnnotatedClass );
			cfg.setProperty( AvailableSettings.DIALECT, "org.hibernate.omm.dialect.MongodbDialect" );
			cfg.setProperty(
					AvailableSettings.CONNECTION_PROVIDER,
					"org.hibernate.omm.jdbc.MongodbConnectionProvider"
			);
			cfg.setProperty(
					MongodbAvailableSettings.MONGODB_CONNECTION_URL, "mongodb://localhost/?directConnection=false"
			);
			cfg.setProperty( MongodbAvailableSettings.MONGODB_DATABASE, "sample_training" );
			sessionFactory = cfg.buildSessionFactory();
		}
		return sessionFactory;
	}
}
