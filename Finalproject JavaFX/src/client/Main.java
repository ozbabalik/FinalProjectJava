package client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Course;
import entity.PersonalData;
import entity.Qualification;
import entity.Student;
import entity.Trainer;
import enums.BookingStates;


public class Main {
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		
        		try {
        			txn.begin();
//        			Student newStudent=new Student();
//        			
//        	
//        			PersonalData p1=new PersonalData();
//        			p1.setFirstname("Hanser");
//        			p1.setLastname("Teller");
//        			p1.setEmail("emin@live.at");
//        			String date="01.01.1970";
//					p1.setDateOfBirth(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
//					
//					newStudent.setPersonalData(p1);
//					em.persist(newStudent);
//					em.flush();
//					System.out.println(newStudent.getId());
//					String svr="2345010180";
//        			TypedQuery<Qualification>	qualQuery=em.createQuery(
//        					"SELECT e FROM Qualification e", Qualification.class);
//        			List<Qualification> quallist=qualQuery.getResultList();   
//        			
//					TypedQuery<Trainer>	trainerQuery=em.createQuery(
//					"SELECT e FROM Trainer e", Trainer.class);
//					List<Trainer> trainers=trainerQuery.getResultList();        			
//        			trainers.get(1).addQualification(quallist.get(4));
//					System.out.println(trainers.get(1).getQualifications().get(2));
//					em.merge(trainers.get(1));
	
//        			Qualification qual1 = new Qualification("ÖSDZA1", "ÖSD Zertifikat A1");
//        			Qualification qual2 = new Qualification("ÖSDZA2", "ÖSD Zertifikat A2");
//        			Qualification qual3 = new Qualification("ÖSDZB1", "ÖSD Zertifikat B1");
//        			Qualification qual4 = new Qualification("ÖSDZB2", "ÖSD Zertifikat B2");
//        			Qualification qual5 = new Qualification("ÖSDZC1", "ÖSD Zertifikat C1");
//        			Qualification qual6 = new Qualification("ÖSDZC2", "ÖSD Zertifikat C2");
//        			em.persist(qual1);
//        			em.persist(qual2);
//        			em.persist(qual3);
//        			em.persist(qual4);
//        			em.persist(qual5);
//        			em.persist(qual6);
       			
        			
//					TypedQuery<Trainer>	trainerQuery=em.createQuery(
//        					"SELECT e FROM Trainer e", Trainer.class);
//        			List<Trainer> trainers=trainerQuery.getResultList();
//        			
//        			for (Trainer trainer : trainers) {
//						System.out.println(trainer.getPersonalData().getFirstname()+" "+ trainer.getPersonalData().getLastname());;
//					}

//  			
//        			Trainer s1 = new Trainer(); 
//        			s1.setPersonalData(p1);
//        			s1.setSocialSecurityNr(svr);
//        			List<Qualification> q = new ArrayList<Qualification>();
//        			q.add(new Qualification("qual1", "Qualification 1"));
//        			q.add(new Qualification("qual2", "Qualification 2"));
//        			s1.setQualifications(q);

        			
//        			if(trainers.contains(s1)) {
//        				System.out.println("vorhanden");
//      			  }
//      			  else
//      				em.persist(s1); 
//      				  
        			
//        			
//        			TypedQuery<Student>	studentQuery=em.createQuery(
//        					"SELECT e FROM Student e", Student.class);
//        			List<Student> students=studentQuery.getResultList();
//        			TypedQuery<Course>	courseQuery=em.createQuery(
//        					"SELECT e FROM Course e", Course.class);
//        			List<Course> courses=courseQuery.getResultList();
//        			students.get(0).book(courses.get(0), BookingStates.confirmed);
//        			em.merge(students.get(2));
//			  PersonalData p2=new PersonalData();
//			  p2.setFirstname("Veli");
//			  p2.setLastname("Salih");
//			  p2.setDateOfBirth("01.01.1980");
//			  Address ad1= new Address();
//			  ad1.setStreet("Sonnleithnergasse");
//			  ad1.setHouseNr("43");
//			  ad1.setCity("Wien");
//			  ad1.setZipcode("1100");
//			  ad1.setCountry("Österreich");
//			  Course c1=new Course();
//			  em.persist(c1);
//			  DAO sm = new DAO();
//			  sm.createStudent(p2, ad1);
			  
			 		  
				   
			 
        	
        			
        	
        			//em.persist(s1);
        			//em.persist(s2);
        			    		
	        		txn.commit();
        		}	catch(Exception e) {
	        			if(txn != null) { txn.rollback(); }
	        			e.printStackTrace();
        		}	finally {
        				if(em != null) { em.close(); }
        		}
        
	}
}
