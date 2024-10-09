package org.springframework.samples.petclinic.mapper;

import org.springframework.samples.petclinic.dto.OwnerDto;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {
	public void updateOwnerFromDto(OwnerDto dto, Owner entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setAddress(dto.getAddress());
		entity.setCity(dto.getCity());
		entity.setTelephone(dto.getTelephone());
	}
}
