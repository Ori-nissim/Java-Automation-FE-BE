package models;

/**
 * This class hold the information of a LinkedIn User
 */
public class LinkedInUser {
	private String name;
	private String workplace;
	private String city;

	public LinkedInUser() {
	}

	@Override
	public String toString() {
		return "LinkedInUser [name=" + name + ", workplace=" + workplace + ", city=" + city + "]";
	}

	// Setters
	
	public void setName(String name) {
		this.name = name;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public void setCity(String city) {
		this.city = city;
	}

	// Getters
	
	public String getName() {
		return name;
	}

	public String getWorkplace() {
		return workplace;
	}

	public String getCity() {
		return city;
	}

}
