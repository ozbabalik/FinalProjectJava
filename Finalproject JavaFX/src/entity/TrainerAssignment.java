package entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import enums.AssignmentStates;

/**
 * Diese Klasse modelliert die Trainerzuordnungen zu den Kursen
 *
 */
@Entity
public class TrainerAssignment {
	/** ID-Nummer in der Datenbank. Eindeutig für jede Zuordnung und generiert automatisch wachsend*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/** Eindeutige Nummer für die Trainerzuordnung. Mithilfe der ID bestimmt und wird automatisch erzeugt.*/
	@Column
	private String assignmentNr;
	/**Tagesdatum der Zuordnung*/
	@Column
	private LocalDate assignmentDate;
	
	

	/**
	 * @return the assignmentDate
	 */
	public LocalDate getAssignmentDate() {
		return assignmentDate;
	}

	/**
	 * @param assignmentDate the assignmentDate to set
	 */
	public void setAssignmentDate(LocalDate assignmentDate) {
		this.assignmentDate = assignmentDate;
	}

	@ManyToOne
	@JoinColumn(name="trainer", referencedColumnName = "id")
	private Trainer trainer;
	
	@OneToOne
	@JoinColumn(name="course", referencedColumnName = "id")
	private Course course;
	
	

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
	 * @return the assignmentNr
	 */
	public String getAssignmentNr() {
		return assignmentNr;
	}

	/**
	 * @param assignmentNr the assignmentNr to set
	 */
	public void setAssignmentNr(String assignmentNr) {
		this.assignmentNr = assignmentNr;
	}

	/**
	 * @return the trainer
	 */
	public Trainer getTrainer() {
		return trainer;
	}

	/**
	 * @param trainer the trainer to set
	 */
	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
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
		result = prime * result + ((trainer == null) ? 0 : trainer.hashCode());
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
		TrainerAssignment other = (TrainerAssignment) obj;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (trainer == null) {
			if (other.trainer != null)
				return false;
		} else if (!trainer.equals(other.trainer))
			return false;
		return true;
	}

}
