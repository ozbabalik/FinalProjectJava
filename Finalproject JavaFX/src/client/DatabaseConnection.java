package client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DatabaseConnection {
	
	private static DatabaseConnection instance;
	
	public static DatabaseConnection getInstance() {
		if(instance==null) {
			instance=new DatabaseConnection();
			instance.init();
		}
		return instance;
		
	}
	
	private DatabaseConnection() {}
	private void init() {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
	EntityManager em = emf.createEntityManager();
	EntityTransaction txn = em.getTransaction();
	}
}
