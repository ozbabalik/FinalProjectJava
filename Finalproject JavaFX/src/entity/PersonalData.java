package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;

import enums.Genders;
import enums.Titles;


/**
 * Diese Klasse modelliert die Personenbezogene Daten von einer Person
 *
 */
@Embeddable
public class PersonalData {
	/**Standard Konstruktor */
	public PersonalData() {
		super();
	}

	/**Akadmischer Titel der Person*/
	@Column
	private Titles title;

	/**Geschlecht der Person*/
	@Column
	private Genders gender;

	/**Vorname der Person*/
	@Column
	private String firstname;

	/** Nachname der Person*/
	@Column
	private String lastname;

	/**Geburtsdatum der Person*/
	@Column(name="dateOfBirth", columnDefinition = "DATE")
	private LocalDate dateOfBirth;

	/**Telefonnummer der Person*/
	@Column(name="telefon")
	private String telefon;
	
	/**EMail-Adresse der Person*/
	@Email
	@Column(name="email")
	private String email;

	
	/**
	 * @return the title
	 */
	public Titles getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return "PersonalData [title=" + title + ", gender=" + gender + ", firstname=" + firstname + ", lastname="
				+ lastname + ", dateOfBirth=" + dateOfBirth + ", telefon=" + telefon + ", email=" + email + "]";
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(Titles title) {
		this.title = title;
	}

	/**
	 * @return the gender
	 */
	public Genders getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Genders gender) {
		this.gender = gender;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
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
		PersonalData other = (PersonalData) obj;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		return true;
	}

	/**
	 * @return the dateOfBirth
	 */
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(LocalDate dateOfBirth) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//		LocalDate dateOfBirth = LocalDate.parse(date, formatter);
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the telefon
	 */
	public String getTelefon() {
		return telefon;
	}

	/**
	 * @param telefon the telefon to set
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
