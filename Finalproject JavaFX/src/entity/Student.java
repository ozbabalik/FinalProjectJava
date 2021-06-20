package entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import enums.BookingStates;
/**
 * Diese Klasse modelliert die Kursteilnehmer 
 *
 */
@Entity
public class Student {
	
	/**Standard Konstruktor*/
	public Student() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Konstruktor mit den Parametern
	 * @param personalData
	 * @param address
	 */
	public Student(PersonalData personalData, Address address) {
		super();
		this.personalData = personalData;
		this.address = address;

	}


	/** ID-Nummer in der Datenbank. Eindeutig für jeden Kursteilnehmer und generiert automatisch wachsend*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	/** Eindeutige Kundennummer für die Teilnehmer. Mithilfe der ID bestimmt und wird automatisch erzeugt.*/
	@Column(name="customerNr", nullable=true)
	private String customerNr;
	
	/**Zeigt, ob der Teilnehmer aktiv oder nicht aktiv ist*/
	@Column(name="isActiv", nullable=false)
	private boolean isActiv;
	
	/**Personenbezogene Daten der Teilnehmer*/
	@Embedded
	private PersonalData personalData;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="street", column=@Column(name="street")),
		@AttributeOverride(name="houseNr", column=@Column(name="houseNr")),
		@AttributeOverride(name="city", column=@Column(name="city")),
		@AttributeOverride(name="zipcode", column=@Column(name="zipcode")),
		@AttributeOverride(name="country", column=@Column(name="country")),
		
	})
	
	/**Adressdaten der Teilnehmer*/
	private Address address;
	
	
	
	@OneToMany(mappedBy="student", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private Set<Booking> bookings=new HashSet<Booking>();
	
//	/**
//	 * Der Teilnehmer bucht einen Kurs mit den Parametern
//	 * @param course
//	 * @param bookingDate
//	 * @param bookingState
//	 */
//	public void book(Course course, LocalDate bookingDate, BookingStates bookingState) {
//		Booking booking=new Booking();
//		booking.setCourse(course);
//		booking.setStudent(this);
//		booking.setBookingState(bookingState);
//		booking.setBookingDate(bookingDate);
//		this.addBooking(booking);
//	}
	
//	public void book(Booking booking) {
//		
//		this.addBooking(booking);
//	}
//	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((personalData == null) ? 0 : personalData.hashCode());
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
		Student other = (Student) obj;
		if (personalData == null) {
			if (other.personalData != null)
				return false;
		} else if (!personalData.equals(other.personalData))
			return false;
		return true;
	}

//	public void cacelBooking(Booking booking) {
//		booking.setBookingState(BookingStates.canceled);
//	}

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
	 * @return the customerNr
	 */
	public String getCustomerNr() {
		return customerNr;
	}

	/**
	 * @param customerNr the customerNr to set
	 */
	public void setCustomerNr(String customerNr) {
		this.customerNr = customerNr;
	}

	/**
	 * @return the personalData
	 */
	public PersonalData getPersonalData() {
		return personalData;
	}

	/**
	 * @param personalData the personalData to set
	 */
	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}


	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	

	/**
	 * @return the bookings
	 */
	public Set<Booking> getBookings() {
		return bookings;
	}

	/**
	 * 
	 * @param bookings
	 */
	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}

	/**
	 * eine Buchung wird für den Teilnehmer hizugefügt
	 * @param booking
	 */
	public void addBooking(Booking booking) {
		this.bookings.add(booking);
	}

	/**
	 * @return the isActiv
	 */
	public boolean isActiv() {
		return isActiv;
	}

	/**
	 * @param isActiv the isActiv to set
	 */
	public void setActiv(boolean isActiv) {
		this.isActiv = isActiv;
	}


}
