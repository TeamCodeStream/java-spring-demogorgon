package org.springframework.samples.petclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDto {

	private String firstName;

	private String lastName;

	private String telephone;

	private String city;

	private String address;

}
