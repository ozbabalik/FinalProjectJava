package application;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class DAO {
	
	public static void updateStates() throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		
		try {
			txn.begin();
//			TypedQuery<Student>	studentQuery=em.createQuery(
//					"SELECT s FROM Student s", Student.class);
//			List<Student> students=studentQuery.getResultList();
			
//			Iterator<Student> studentIterator = students.iterator();
//
//			while(studentIterator.hasNext()) {
//			    System.out.println(studentIterator.next()); 
//			}
			
//			for(Student s : students) {
//				Set<Booking> studentBookings = new HashSet<Booking>();
//				
//				Iterator<Booking> bookingIterator = s.getBookings().iterator();
//				if(!s.getBookings().isEmpty()) {
//				while(bookingIterator.hasNext()) {
//					System.out.println(bookingIterator.next().getBookingState().toString());
//					//Booking booking = bookingIterator.next();
//					if(bookingIterator.next().getBookingState()==BookingStates.running&&bookingIterator.next().getCourse().getCourseEnd().compareTo(LocalDate.now())==1) {
//						bookingIterator.next().setBookingState(BookingStates.completed);
//						//em.merge(s);
//						studentBookings.add(bookingIterator.next());
//						System.out.println(bookingIterator.next().getBookingState().toString());
//					}
//					//studentBookings.add(bookingIterator.next());
//					//System.out.println(bookingIterator.next().getBookingState().toString());
//				}
//				}
//			}
			
//			TypedQuery<Trainer>	trainerQuery=em.createQuery(
//					"SELECT t FROM Trainer t", Trainer.class);
//			List<Trainer> trainers=trainerQuery.getResultList();
			
			TypedQuery<Course>	courseQuery=em.createQuery(
					"SELECT c FROM Course c", Course.class);
			List<Course> courses=courseQuery.getResultList();
			
//			Iterator<Course> courseIterator = courses.iterator();
			
//			for(int i=0; i<courses.get(1).getBookings().size(); i++) {
//				System.out.println(courses.get(1).getBookings().get(i).getStudent().getPersonalData().getFirstname());
//			}
			
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
			
//			while(courseIterator.hasNext()) {
//				//courseIterator.next().getCourseTitle();
//				if(courseIterator.next().getCourseEnd().compareTo(LocalDate.now())==-1) {
//					courseIterator.next().getCourseEnd().toString();
//					courseIterator.next().setCourseState(CourseStates.completed);
//					Iterator<Booking> bookingIterator = courseIterator.next().getBookings().iterator();
//					List<Booking> courseBookings = new ArrayList<Booking>();
//					while(bookingIterator.hasNext()) {
//						if(bookingIterator.next().getBookingState()==BookingStates.running) {
//							bookingIterator.next().setBookingState(BookingStates.completed);
//						}
//						courseBookings.add(bookingIterator.next());
//					}
//					//courseIterator.next().getCourseEnd().toString();
//					courseIterator.next().setBookings(courseBookings);
//					
//					em.merge(courseIterator.next());
//				}
//				for(int i=0; i<courseIterator.next().getBookings().size(); i++) {
//					courseIterator.next().getBookings().get(i).getBookingState().toString();
//				}
				
//			}
			//em.merge(courseIterator.next());
			

			
						
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
	}
	
//	public static void createStudent(PersonalData personalData, Address adress) throws SQLException {
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction txn = em.getTransaction();
//		
//		try {
//			txn.begin();
//			TypedQuery<Student>	studentQuery=em.createQuery(
//					"SELECT e FROM Student e", Student.class);
//			List<Student> students=studentQuery.getResultList();
//			long lastID=0;
//			if(!students.isEmpty()) {
//				lastID=students.get(students.size()-1).getId();
//			}
//			
//			Student newStudent = new Student(personalData, adress);
//			
//			if(students.contains(newStudent)) {
//				System.out.println("The Student already exists");
//			}
//			else {
//				
//			em.persist(newStudent);
//			
//			}
//			
//			//em.flush();
//			
//			
//			String customerNr="KN" + Calendar.getInstance().get(Calendar.YEAR)%100+"-"+String.format("%04d", lastID+1);			
//			newStudent.setCustomerNr(customerNr);
//			em.merge(newStudent);
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
				String customerNr="KN" +"-"+String.format("%04d", lastID+1);			
				student.setCustomerNr(customerNr);
				em.persist(student);
			
			}
			
			//em.flush();
			
			
			
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
	
	public static void updateStudent(Student student, Booking booking) throws SQLException {
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
			
			TypedQuery<Booking>	bookingQuery=em.createQuery(
					"SELECT b FROM Booking b", Booking.class);
			List<Booking> bookings=bookingQuery.getResultList();
			long lastID=0;
			if(!bookings.isEmpty()) {
				lastID=bookings.get(bookings.size()-1).getId();
			}
			
			
			String bookingNr="BN" + Calendar.getInstance().get(Calendar.YEAR)%100+"-"+String.format("%04d", lastID+1);			
			booking.setBookingNr(bookingNr);
			//em.merge(booking);
			student.addBooking(booking);
			
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
	
//	public static void createTrainer(String socialSecurityNr, PersonalData personalData, Address adress, List<Qualification> qualifications) throws SQLException {
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction txn = em.getTransaction();
//		
//		try {
//			txn.begin();
//			TypedQuery<Trainer>	trainerQuery=em.createQuery(
//					"SELECT e FROM Trainer e", Trainer.class);
//			List<Trainer> trainers=trainerQuery.getResultList();
//			long lastID=0;
//			if(!trainers.isEmpty()) {
//				lastID=trainers.get(trainers.size()-1).getId();
//			}
//			
//			Trainer newTrainer = new Trainer(socialSecurityNr, personalData, adress);
//			newTrainer.setQualifications(qualifications);
//			
//			if(trainers.contains(newTrainer)) {
//				System.out.println("The Student already exists");
//			}
//			else {
//				
//			em.persist(newTrainer);
//			
//			}
//			
//			em.flush();
//			
//			
//	//		String staffNr="PN" + Calendar.getInstance().get(Calendar.YEAR)%100+"-"+String.format("%04d", lastID+1);			
//			newTrainer.setStaffNr();
//			em.merge(newTrainer);
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
	
	public static void updateBooking(Booking booking) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("verwasoft");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		//Booking newBooking = ;
		
		try {
			txn.begin();
//			TypedQuery<Booking>	bookingQuery=em.createQuery(
//					"SELECT b FROM Booking b", Booking.class);
//			List<Booking> bookings=bookingQuery.getResultList();
//			long lastID=0;
//			if(!bookings.isEmpty()) {
//				lastID=bookings.get(bookings.size()-1).getId();
//			}
//			
//			
//			
//			if(bookings.contains(newBooking)) {
//				System.out.println("The booking already exists");
//			}
//			else {
//				
//			em.persist(newBooking);
//			
//			}
//			
//			//em.flush();
//			
//			
//			String bookingNr="BN" + Calendar.getInstance().get(Calendar.YEAR)%100+"-"+String.format("%04d", lastID+1);			
//			newBooking.setBookingNr(bookingNr);
			em.merge(booking);
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}
	
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
			
			//em.flush();
			
			
			
			//em.merge(newTrainerAssignment);
			
			txn.commit();
			
		}	catch(Exception e) {
    			if(txn != null) { txn.rollback(); }
    			e.printStackTrace();
		}	finally {
				if(em != null) { em.close(); }
		}
		
		
	}
}


