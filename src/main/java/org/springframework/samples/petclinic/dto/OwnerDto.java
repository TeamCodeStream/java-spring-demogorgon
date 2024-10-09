package org.springframework.samples.petclinic.dto;

public class OwnerDto {

	private String firstName;
	private String lastName;
	private String telephone;
	private String city;
	private String address;

	@Override public String toString() {
		return "OwnerDto(firstName="+ firstName +", lastName="+ lastName +", telephone="+ telephone +", " +
			"city="+city+",address="+address+")";
	}

	public OwnerDto(String firstName, String lastName, String telephone, String city, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.telephone = telephone;
		this.city = city;
		this.address = address;
	}

	public OwnerDto(){}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


}
