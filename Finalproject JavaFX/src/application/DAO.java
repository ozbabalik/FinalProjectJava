package application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Address;
import entity.Course;
import entity.PersonalData;
import entity.Qualification;
import entity.Student;
import entity.Trainer;
import enums.CourseStates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class DAO {
	
	public static void createStudent(PersonalData personalData, Address adress) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		
		try {
			txn.begin();
			TypedQuery<Student>	studentQuery=em.createQuery(
					"SELECT e FROM Student e", Student.class);
			List<Student> students=studentQuery.getResultList();
			long lastID=0;
			if(!students.isEmpty()) {
				lastID=students.get(students.size()-1).getId();
			}
			
			Student newStudent = new Student(personalData, adress);
			
			if(students.contains(newStudent)) {
				System.out.println("The Student already exists");
			}
			else {
				
			em.persist(newStudent);
			
			}
			
			//em.flush();
			
			
			String customerNr="CN" + Calendar.getInstance().get(Calendar.YEAR)%100+"-"+String.format("%04d", lastID+1);			
			newStudent.setCustomerNr(customerNr);
			em.merge(newStudent);
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}
	
	public static void createStudent(Student student) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		
		try {
			txn.begin();
			TypedQuery<Student>	studentQuery=em.createQuery(
					"SELECT e FROM Student e", Student.class);
			List<Student> students=studentQuery.getResultList();
			long lastID=0;
			if(!students.isEmpty()) {
				lastID=students.get(students.size()-1).getId();
			}
			
			//Student newStudent = new Student(personalData, adress);
			
			if(students.contains(student)) {
				System.out.println("The Student already exists");
			}
			else {
				
			em.persist(student);
			
			}
			
			//em.flush();
			
			
			String customerNr="CN" + Calendar.getInstance().get(Calendar.YEAR)%100+"-"+String.format("%04d", lastID+1);			
			student.setCustomerNr(customerNr);
			em.merge(student);
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}
	public static void updateStudent(Student student) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		
		try {
			txn.begin();
//			TypedQuery<Student>	studentQuery=em.createQuery(
//					"SELECT e FROM Student e", Student.class);
//			List<Student> students=studentQuery.getResultList();
//			Student tempStudent = em.find(Student.class, id);
//			tempStudent.setPersonalData(personalData);
//			tempStudent.setAddress(adress);
			em.merge(student);
			
			
			
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}	
	public ObservableList<Student> studentList(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		ObservableList<Student> students=FXCollections.observableArrayList();

		try {
			txn.begin();
			TypedQuery<Student>	studentQuery=em.createQuery(
					"SELECT e FROM Student e", Student.class);
			
			students=FXCollections.observableArrayList(studentQuery.getResultList());
			
			
			
			
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		return students;
		
	};
	public void showStudentDetails(Student student) {
		try {
			Stage studentform = new Stage();
			VBox root = (VBox)FXMLLoader.load(getClass().getResource("Studentform.fxml"));
			
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			studentform.setTitle("Studentform");
			studentform.setScene(scene);
			studentform.show();
			studentform.setResizable(false);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createTrainer(String socialSecurityNr, PersonalData personalData, Address adress, List<Qualification> qualifications) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		
		try {
			txn.begin();
			TypedQuery<Trainer>	trainerQuery=em.createQuery(
					"SELECT e FROM Trainer e", Trainer.class);
			List<Trainer> trainers=trainerQuery.getResultList();
			long lastID=0;
			if(!trainers.isEmpty()) {
				lastID=trainers.get(trainers.size()-1).getId();
			}
			
			Trainer newTrainer = new Trainer(socialSecurityNr, personalData, adress);
			newTrainer.setQualifications(qualifications);
			
			if(trainers.contains(newTrainer)) {
				System.out.println("The Student already exists");
			}
			else {
				
			em.persist(newTrainer);
			
			}
			
			em.flush();
			
			
	//		String staffNr="PN" + Calendar.getInstance().get(Calendar.YEAR)%100+"-"+String.format("%04d", lastID+1);			
			newTrainer.setStaffNr();
			em.merge(newTrainer);
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}
	
	public static void createTrainer(Trainer newTrainer) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		
		try {
			txn.begin();
			TypedQuery<Trainer>	trainerQuery=em.createQuery(
					"SELECT e FROM Trainer e", Trainer.class);
			List<Trainer> trainers=trainerQuery.getResultList();
			long lastID=0;
			if(!trainers.isEmpty()) {
				lastID=trainers.get(trainers.size()-1).getId();
			}
			
			
			if(trainers.contains(newTrainer)) {
				System.out.println("The Student already exists");
			}
			else {
				
			em.persist(newTrainer);
			
			}
			
			em.flush();
			
			
	//		String staffNr="PN" + Calendar.getInstance().get(Calendar.YEAR)%100+"-"+String.format("%04d", lastID+1);			
			newTrainer.setStaffNr();
			em.merge(newTrainer);
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}
	
	public static void updateTrainer(Trainer trainer) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		
		try {
			txn.begin();

			em.merge(trainer);
		
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}	
		
	public ObservableList<Trainer> trainerList(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		ObservableList<Trainer> trainers=FXCollections.observableArrayList();

		//List<Trainer> trainers=new ArrayList<Trainer>();
		try {
			txn.begin();
			TypedQuery<Trainer>	trainerQuery=em.createQuery(
					"SELECT e FROM Trainer e", Trainer.class);
			trainers=FXCollections.observableArrayList(trainerQuery.getResultList());			
			
			
			
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		return trainers;
		
	};
	public void showTrainerDetails(Trainer trainer) {
		try {
			Stage studentform = new Stage();
			VBox root = (VBox)FXMLLoader.load(getClass().getResource("Trainerform.fxml"));
			
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			studentform.setTitle("Trainerform");
			studentform.setScene(scene);
			studentform.show();
			studentform.setResizable(false);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createCourse(Course newCourse) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		
		try {
			txn.begin();
			TypedQuery<Course>	courseQuery=em.createQuery(
					"SELECT e FROM Course e", Course.class);
			List<Course> courses=courseQuery.getResultList();
			
			
			if(courses.contains(newCourse)) {
				System.out.println("The Student already exists");
			}
			else {
				
			em.persist(newCourse);
			
			}
			
			//em.flush();
			
			
			
			em.merge(newCourse);
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}
	
	public static void updateCourse(Course course) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		
		try {
			txn.begin();
			
			em.merge(course);
					
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
	}
	
	public ObservableList<Course> courseList(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		ObservableList<Course> courses=FXCollections.observableArrayList();
		try {
			txn.begin();
			TypedQuery<Course>	courseQuery=em.createQuery(
					"SELECT e FROM Course e", Course.class);
			courses=FXCollections.observableArrayList(courseQuery.getResultList());			
			
	//		private ObservableList<Course> courseList = FXCollections.observableArrayList();
			
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		return courses;
		
	};
}


