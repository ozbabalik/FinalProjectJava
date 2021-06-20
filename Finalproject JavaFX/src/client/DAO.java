package client;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Address;
import entity.Booking;
import entity.Course;
import entity.PersonalData;
import entity.Qualification;
import entity.Student;
import entity.Trainer;
import entity.TrainerAssignment;
import enums.BookingStates;
import enums.CourseStates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Diese Klasse dient dazu, dass die Applikation auf die Datenbank zugreift.
 * Sie verfügt über alle Datenbankbzeogene funktionen
 *
 */
public class DAO {
	/**
	 * überprüft die aktuelle Status der Kurse, Teilnehmer und Buchungen.
	 * Wenn nötig werden die Status aktualisiert und in die DB gespeichert
	 * @throws SQLException
	 */
	public static void updateStates() throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		
		try {
			txn.begin();
		
			TypedQuery<Course>	courseQuery=em.createQuery(
					"SELECT c FROM Course c", Course.class);
			List<Course> courses=courseQuery.getResultList();
			
			for(int i=0; i<courses.size(); i++) {
				if(courses.get(i).getCourseEnd().compareTo(LocalDate.now())<1&&(courses.get(i).getCourseState()==CourseStates.running||courses.get(i).getCourseState()==CourseStates.scheduled)) {
					courses.get(i).setCourseState(CourseStates.completed);
					for(int j=0; j<courses.get(i).getBookings().size(); j++) {
						if(courses.get(i).getBookings().get(j).getBookingState()==BookingStates.running) {
							courses.get(i).getBookings().get(j).setBookingState(BookingStates.completed);
						}
					}
					em.merge(courses.get(i));
				} else if(courses.get(i).getCourseEnd().compareTo(LocalDate.now())>1&&courses.get(i).getCourseStart().compareTo(LocalDate.now())<1&&courses.get(i).getCourseState()==CourseStates.scheduled) {
					courses.get(i).setCourseState(CourseStates.running);
					for(int j=0; j<courses.get(i).getBookings().size(); j++) {
						if(courses.get(i).getBookings().get(j).getBookingState()==BookingStates.confirmed) {
							courses.get(i).getBookings().get(j).setBookingState(BookingStates.running);
						}
					}
					em.merge(courses.get(i));
				}
				
			}
			

			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
	}
	
	/**
	 * der erstellte Teilnehmer wird in der DB gespeichert
	 * @param student
	 * @throws SQLException
	 */
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
				String customerNr="KN" +"-"+String.format("%04d", lastID+1);			
				student.setCustomerNr(customerNr);
				em.persist(student);									
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}
	
	/**
	 * die Daten eines vorhendenen Teilnehmers in der DB aktualisiert.
	 * @param student
	 * @throws SQLException
	 */
	public static void updateStudent(Student student) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		
		try {
			txn.begin();
			em.merge(student);			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}	
	
//	public static void updateStudent(Student student, Booking booking) throws SQLException {
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction txn = em.getTransaction();
//		
//		try {
//			txn.begin();
//			TypedQuery<Booking>	bookingQuery=em.createQuery(
//					"SELECT b FROM Booking b", Booking.class);
//			List<Booking> bookings=bookingQuery.getResultList();
//			long lastID=0;
//			if(!bookings.isEmpty()) {
//				lastID=bookings.get(bookings.size()-1).getId();
//			}
//			
//			String bookingNr="BN" + Calendar.getInstance().get(Calendar.YEAR)%100+"-"+String.format("%04d", lastID+1);			
//			booking.setBookingNr(bookingNr);
//			student.addBooking(booking);			
//			em.merge(student);
//		
//			txn.commit();
//			
//		}	catch(Exception e) {
//    			if(txn != null) { txn.rollback(); }
//    			e.printStackTrace();
//		}	finally {
//				if(em != null) { em.close(); }
//		}
//		
//		
//	}
	/**
	 * Gibt die aktuelle Teilnehmerlist aus der DB als ObservableList zurück
	 * @return
	 */
	public ObservableList<Student> studentList(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		ObservableList<Student> students=FXCollections.observableArrayList();

		try {
			txn.begin();
			TypedQuery<Student>	studentQuery=em.createQuery(
					"SELECT e FROM Student e ", Student.class);			
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

	/**
	 * ein neu erstellter Trainer wird in der DB gespeichert.
	 * @param newTrainer
	 * @throws SQLException
	 */
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
				String staffNr="PN" + "-"+String.format("%04d", lastID+1);			
				newTrainer.setStaffNr(staffNr);
			em.persist(newTrainer);
			
			}
			
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}
	
	/**
	 * die Daten eines vorhandenes Trainer wird in der DB aktualisiert
	 * @param trainer
	 * @throws SQLException
	 */
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
	/**
	 * Gibt die aktuelle Trainerlist aus der DB als ObservableList zurück	
	 * @return
	 */
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

	/**
	 * ein neu erstellter Kurs wird in der DB gespeichert
	 * @param newCourse
	 * @throws SQLException
	 */
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
				return;
			}
			else {
				
			em.persist(newCourse);
			
			}
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}
	
	/**
	 * die Daten eines vorhandenen Kurses wird in der DB aktualisiert
	 * @param course
	 * @throws SQLException
	 */
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
	/**
	 * Gibt die aktuelle Kurslist aus der DB als ObservableList zurück
	 * @return
	 */
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
					
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		return courses;
		
	};
	
	/**
	 * eine neue Buchung wird in der DB gespeichert.
	 * @param newBooking
	 * @throws SQLException
	 */
	public static void createBooking(Booking newBooking) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		//Booking newBooking = ;
		
		try {
			txn.begin();
			TypedQuery<Booking>	bookingQuery=em.createQuery(
					"SELECT b FROM Booking b", Booking.class);
			List<Booking> bookings=bookingQuery.getResultList();
			long lastID=0;
			if(!bookings.isEmpty()) {
				lastID=bookings.get(bookings.size()-1).getId();
			}
			
			
			
			if(bookings.contains(newBooking)) {
				System.out.println("The booking already exists");
			}
			else {
				String bookingNr="BN" + Calendar.getInstance().get(Calendar.YEAR)%100+"-"+String.format("%04d", lastID+1);			
				newBooking.setBookingNr(bookingNr);
			em.persist(newBooking);
			
			}
			

			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}
	
	/**
	 * die Daten einer vorhandenen Buchung wird in der DB aktualisert
	 * @param booking
	 * @throws SQLException
	 */
	public static void updateBooking(Booking booking) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		//Booking newBooking = ;
		
		try {
			txn.begin();

			em.merge(booking);
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}
	
	/**
	 * eine neue Trainerzuordnung wird in der Datenbank gespeichert
	 * @param newTrainerAssignment
	 * @throws SQLException
	 */
	public static void newTrainerAssignment(TrainerAssignment newTrainerAssignment) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		//Booking newBooking = ;
		
		try {
			txn.begin();
			TypedQuery<TrainerAssignment>	assignmentQuery=em.createQuery(
					"SELECT a FROM TrainerAssignment a", TrainerAssignment.class);
			List<TrainerAssignment> assignments=assignmentQuery.getResultList();
			long lastID=0;
			if(!assignments.isEmpty()) {
				lastID=assignments.get(assignments.size()-1).getId();
			}
			
			
			
			if(assignments.contains(newTrainerAssignment)) {
				return;
			}
			else {
				String assignmentNr="AN" + "-"+String.format("%04d", lastID+1);			
				newTrainerAssignment.setAssignmentNr(assignmentNr);
			em.persist(newTrainerAssignment);
			
			}

			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}
	
	/**
	 * die Daten einer vorhandenen Trainerzuordnung wird in der DB aktualisiert
	 * @param trainerAssignment
	 * @throws SQLException
	 */
	public static void updateTrainerAssignment(TrainerAssignment trainerAssignment) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		//Booking newBooking = ;
		
		try {
			txn.begin();
			
			em.merge(trainerAssignment);
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}
	
	/**
	 * eine vorhandene Trainerzuordnung wird gelöscht
	 * @param trainerAssignment
	 * @throws SQLException
	 */
	public static void removeTrainerAssignment(TrainerAssignment trainerAssignment) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		//Booking newBooking = ;
		
		try {
			txn.begin();
			TrainerAssignment assignment = em.find(TrainerAssignment.class, trainerAssignment.getId());
			em.remove(assignment);
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}
	
	/**
	 * die Liste der Trainerqualifikationen werden mit der angegebenen List aktualisert
	 * @param qList
	 * @throws SQLException
	 */
	public static void updateQualificationList(List<Qualification> qList) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();

		
		try {
			txn.begin();
			
			TypedQuery<Qualification>	qQuery=em.createQuery(
					"SELECT q FROM Qualification q", Qualification.class);
			List<Qualification> qualifications=qQuery.getResultList();
			
			for (Qualification qualification : qList) {
				if(!qualifications.contains(qualification)) {
					em.persist(qualification);
				}
				
			}
			//em.merge(trainerAssignment);
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}
	
}


