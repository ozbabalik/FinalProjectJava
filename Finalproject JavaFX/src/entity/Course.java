package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import enums.CourseCategories;
import enums.CourseStates;

@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "courseNr")
	private String courseNr;

	@Column(name = "courseTitle")
	private String courseTitle;

	@Column(name = "courseDescription")
	private String courseDescription;

	@Column(name = "courseStart")
	private LocalDate courseStart;

	@Column(name = "courseEnd")
	private LocalDate courseEnd;

	@Column(name = "coursePrice")
	private float coursePrice;

	@Enumerated
	private CourseCategories courseCategory;

	@Enumerated
	private CourseStates courseState;

	@OneToMany(mappedBy = "course", cascade = { CascadeType.PERSIST })
	private List<Booking> bookings;

	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Course(String courseNr, String courseTitle, String courseDescription, LocalDate courseStart, LocalDate courseEnd,
			float coursePrice, CourseCategories courseCategory, CourseStates courseState) {
		super();
		this.courseNr = courseNr;
		this.courseTitle = courseTitle;
		this.courseDescription = courseDescription;
		this.courseStart = courseStart;
		this.courseEnd = courseEnd;
		this.coursePrice = coursePrice;
		this.courseCategory = courseCategory;
		this.courseState = courseState;
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
	 * @return the courseNr
	 */
	public String getCourseNr() {
		return courseNr;
	}

	/**
	 * @param courseNr the courseNr to set
	 */
	public void setCourseNr(String courseNr) {
		this.courseNr = courseNr;
	}

	/**
	 * @return the courseTitle
	 */
	public String getCourseTitle() {
		return courseTitle;
	}

	/**
	 * @param courseTitle the courseTitle to set
	 */
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	/**
	 * @return the courseDescription
	 */
	public String getCourseDescription() {
		return courseDescription;
	}

	/**
	 * @param courseDescription the courseDescription to set
	 */
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	/**
	 * @return the courseStart
	 */
	public LocalDate getCourseStart() {
		return courseStart;
	}

	/**
	 * @param string the courseStart to set
	 */
	public void setCourseStart(LocalDate courseStart) {
	//	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	//	LocalDate courseStart = LocalDate.parse(strDate, formatter);
		this.courseStart = courseStart;
	}

	/**
	 * @return the courseEnd
	 */
	public LocalDate getCourseEnd() {
		return courseEnd;
	}

	/**
	 * @param courseEnd the courseEnd to set
	 */
	public void setCourseEnd(LocalDate courseEnd) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//		LocalDate courseEnd = LocalDate.parse(strDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		this.courseEnd = courseEnd;
	}

	/**
	 * @return the coursePrice
	 */
	public float getCoursePrice() {
		return coursePrice;
	}

	/**
	 * @param coursePrice the coursePrice to set
	 */
	public void setCoursePrice(float coursePrice) {
		this.coursePrice = coursePrice;
	}

	/**
	 * @return the courseCategory
	 */
	public CourseCategories getCourseCategory() {
		return courseCategory;
	}

	/**
	 * @param courseCategory the courseCategory to set
	 */
	public void setCourseCategory(CourseCategories courseCategory) {
		this.courseCategory = courseCategory;
	}

	/**
	 * @return the courseState
	 */
	public CourseStates getCourseState() {
		return courseState;
	}

	/**
	 * @param courseState the courseState to set
	 */
	public void setCourseState(CourseStates courseState) {
		this.courseState = courseState;
	}

	/**
	 * @return the bookings
	 */
	public List<Booking> getBookings() {
		return bookings;
	}

	/**
	 * @param bookings the bookings to set
	 */
	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseNr == null) ? 0 : courseNr.hashCode());
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
		Course other = (Course) obj;
		if (courseNr == null) {
			if (other.courseNr != null)
				return false;
		} else if (!courseNr.equals(other.courseNr))
			return false;
		return true;
	}

	/**
	 * @return the students
	 */

}
