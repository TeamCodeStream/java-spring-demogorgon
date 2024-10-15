package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.dto.OwnerDto;
import org.springframework.samples.petclinic.mapper.OwnerMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest")
public class OwnerRestController {

	private final OwnerRepository owners;

	private final OwnerMapper mapper;

	public OwnerRestController(OwnerRepository clinicService, OwnerMapper mapper) {
		this.owners = clinicService;
		this.mapper = mapper;
	}

	@GetMapping("/owners")
	private List<Owner> findListForOwnersLastName(String lastName) {
		List<Owner> ownersResult = new ArrayList<>();
		if (lastName != null) {
			ownersResult = owners.findByLastName(lastName);
		}
		return ownersResult.isEmpty() ? owners.findAll() : ownersResult;

	}

	@GetMapping("/owners/{ownerId}")
	public Owner findOwnerById(@PathVariable int ownerId) {
		return owners.findById(ownerId);
	}

	@PostMapping("/owners/new")
	public ResponseEntity processCreationForm(@Valid @RequestBody Owner owner) {
		this.owners.save(owner);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PatchMapping("/owners/{ownerId}")
	public ResponseEntity<Owner> updateOwner(@PathVariable int ownerId, @RequestBody OwnerDto ownerDto) {
		Owner owner = owners.findById(ownerId);
		mapper.updateOwnerFromDto(ownerDto, owner);
		owners.save(owner);

		return new ResponseEntity<>(owner, HttpStatus.OK);
	}

}
