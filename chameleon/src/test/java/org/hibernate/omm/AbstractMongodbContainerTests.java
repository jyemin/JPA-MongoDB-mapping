package org.hibernate.omm;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class AbstractMongodbContainerTests {
	private SessionFactory sessionFactory;

	public abstract List<Class<?>> getAnnotatedClasses();

	protected SessionFactory getSessionFactory() {
		if ( sessionFactory == null ) {
			Configuration cfg = new Configuration();
			getAnnotatedClasses().forEach( cfg::addAnnotatedClass );
			sessionFactory = cfg.buildSessionFactory();
		}
		return sessionFactory;
	}
}
