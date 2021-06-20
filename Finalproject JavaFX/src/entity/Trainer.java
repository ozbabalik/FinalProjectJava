package entity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import enums.AssignmentStates;
/**
 * Diese Klasse modelliert die Trainer 
 *
 */
@Entity
public class Trainer {
	
	/** ID-Nummer in der Datenbank. Eindeutig für jeden Trainer und generiert automatisch wachsend*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/** Eindeutige Personalnr für die Trainer. Mithilfe der ID bestimmt und wird automatisch erzeugt.*/
	@Column
	private String staffNr;
	
	/**Zeigt, ob der Trainer aktiv oder nicht aktiv ist*/
	@Column(name="isActiv")
	private boolean isActiv;
	
	/**
	 * Standard Konstruktor
	 */
	public Trainer() {
	}
	/**
	 * Konstruktor mit den Parametern
	 * @param socialSecurityNr
	 * @param personalData
	 * @param address
	 */
	public Trainer(String socialSecurityNr, PersonalData personalData, Address address) {
		super();
		this.socialSecurityNr = socialSecurityNr;
		this.personalData = personalData;
		this.address = address;
	}

	/**Sozialversicherungsnummer*/
	@Column(name="socialSecurityNr")
	private String socialSecurityNr;
	
	/** Personenbezogene Daten der Trainer*/
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
	
	/** Adressdaten der Trainer*/
	private Address address;

	@CollectionTable
	private List<Qualification> qualifications=new ArrayList<Qualification>();
	
	@OneToMany(mappedBy="trainer", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private Set<TrainerAssignment> assignments=new HashSet<TrainerAssignment>();
	
	public void addAssignment(TrainerAssignment assignment) {
		assignments.add(assignment);
	}
	
	
	/**
	 * @return the qualifications
	 */
	public List<Qualification> getQualifications() {
		return qualifications;
	}

	/**
	 * @param qualifications the qualifications to set
	 */
	public void setQualifications(List<Qualification> qualifications) {
		this.qualifications = qualifications;
	}
	/**
	 * einer Qualifikation wird zum Trainer hinzugefügt
	 * @param q
	 */
	public void addQualification(Qualification q) {
		this.qualifications.add(q);
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((socialSecurityNr == null) ? 0 : socialSecurityNr.hashCode());
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
		Trainer other = (Trainer) obj;
		if (socialSecurityNr == null) {
			if (other.socialSecurityNr != null)
				return false;
		} else if (!socialSecurityNr.equals(other.socialSecurityNr))
			return false;
		return true;
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
	 * @return the staffNr
	 */
	public String getStaffNr() {
		return staffNr;
	}

	/**
	 * @param staffNr the staffNr to set
	 */
	public void setStaffNr() {
		this.staffNr = "PN-"+String.format("%04d", id);
	}

	/**
	 * @return the socialSecurityNr
	 */
	public String getSocialSecurityNr() {
		return socialSecurityNr;
	}

	/**
	 * @param socialSecurityNr the socialSecurityNr to set
	 */
	public void setSocialSecurityNr(String socialSecurityNr) {
		this.socialSecurityNr = socialSecurityNr;
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

	/**
	 * @param staffNr the staffNr to set
	 */
	public void setStaffNr(String staffNr) {
		this.staffNr = staffNr;
	}
	

}
