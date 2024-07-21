package org.hibernate.omm;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.omm.id.MongodbIdentifierColumnNamingStrategy;

public abstract class AbstractMongodbIntegrationTests {
	private SessionFactory sessionFactory;

	public abstract List<Class<?>> getAnnotatedClasses();

	protected SessionFactory getSessionFactory() {
		if ( sessionFactory == null ) {
			Configuration cfg = new Configuration();
			cfg.setImplicitNamingStrategy( new MongodbIdentifierColumnNamingStrategy() );
			getAnnotatedClasses().forEach( cfg::addAnnotatedClass );
			sessionFactory = cfg.buildSessionFactory();
		}
		return sessionFactory;
	}
}
