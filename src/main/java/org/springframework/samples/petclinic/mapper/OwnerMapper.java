package org.springframework.samples.petclinic.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.samples.petclinic.dto.OwnerDto;
import org.springframework.samples.petclinic.owner.Owner;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnerMapper {

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateOwnerFromDto(OwnerDto dto, @MappingTarget Owner entity);

}
