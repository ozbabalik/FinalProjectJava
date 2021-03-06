package entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import enums.BookingStates;

/**
 * diese Klasse modelliert die Buchungen der Teilnehmer
 */
@Entity
public class Booking {
	
	/** ID-Nummer in der Datenbank. Eindeutig für jede Buchung und generiert automatisch wachsend*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/** Eindeutige Buchungsnummer für die Buchung. Mithilfe der ID bestimmt und wird automatisch erzeugt.*/
	@Column
	private String bookingNr;
	
	/** Das Tagesdatum, an dem die Buchung durchgeführt werden */
	@Column
	private LocalDate bookingDate;
	
	
	/**
	 * @return the bookingDate
	 */
	public LocalDate getBookingDate() {
		return bookingDate;
	}

	/**
	 * @param bookingDate the bookingDate to set
	 */
	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	/**Der Teilnehmer, dem die Bchung gehört*/
	@ManyToOne
	@JoinColumn(name="student", referencedColumnName = "id")
	private Student student;
	
	/**Der Kurs, den der Teilnehmer gebucht hat*/
	@ManyToOne//(cascade={CascadeType.PERSIST})
	@JoinColumn(name="course", referencedColumnName = "id")
	private Course course;
	
	/**der Status der Buchung */
	@Enumerated
	@Column
	private BookingStates bookingState;

	/**
	 * @return the bookingState
	 */
	public BookingStates getBookingState() {
		return bookingState;
	}

	/**
	 * @param bookingState the bookingState to set
	 */
	public void setBookingState(BookingStates bookingState) {
		this.bookingState = bookingState;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the bookingNr
	 */
	public String getBookingNr() {
		return bookingNr;
	}

	/**
	 * @param bookingNr the bookingNr to set
	 */
	public void setBookingNr(String bookingNr) {
		this.bookingNr = bookingNr;
	}

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		
		this.course = course;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		return true;
	}
	

}
