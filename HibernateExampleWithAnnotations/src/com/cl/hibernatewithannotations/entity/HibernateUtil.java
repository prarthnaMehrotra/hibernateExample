package com.cl.hibernatewithannotations.entity;

import java.util.Properties;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	public static SessionFactory getSessionFactory() {
		if(sessionFactory == null) {
			try {
				Configuration cfg = new Configuration();
				Properties dbProps = new Properties();
				dbProps.put(Environment.DRIVER, "org.postgresql.Driver");
				dbProps.put(Environment.URL, "jdbc:postgresql://localhost:5432/StudentDatabase");
				dbProps.put(Environment.USER, "postgres");
				dbProps.put(Environment.PASS, "crimson@123");
				dbProps.put(Environment.SHOW_SQL, true);
				dbProps.put(Environment.FORMAT_SQL, true);
				dbProps.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "Thread");
				cfg.setProperties(dbProps);
				cfg.addAnnotatedClass(StudentInfo.class);
				
				ServiceRegistry serviceRegistry = new 
						StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
				sessionFactory = cfg.buildSessionFactory(serviceRegistry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}

}
