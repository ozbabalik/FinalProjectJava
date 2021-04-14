package entity;
import javax.persistence.Embeddable;


@Embeddable
public class Address {
	
//	@Column(name="street")
	private String street;
	
//	@Column(name="houseNr")
	private String houseNr;
	
//	@Column(name="city")
	private String city;
	
//	@Column(name="zipcode")
	private String zipcode;
	
//	@Column(name="country")
	private String country;
	
	
	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * @return the houseNr
	 */
	public String getHouseNr() {
		return houseNr;
	}
	/**
	 * @param houseNr the houseNr to set
	 */
	public void setHouseNr(String houseNr) {
		this.houseNr = houseNr;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}
	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	public Address() {
	}
	public Address(String street, String houseNr, String city, String zipcode) {
		this.street = street;
		this.houseNr = houseNr;
		this.city = city;
		this.zipcode = zipcode;
	}
	@Override
	public String toString() {
		return "Address [street=" + street + ", houseNr=" + houseNr + ", city=" + city + ", zipcode=" + zipcode
				+ ", country=" + country + "]";
	}	
	
}







