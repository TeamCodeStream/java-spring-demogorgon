package org.springframework.samples.petclinic.vet;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class VetRestController {

	private final VetRepository vets;

	public VetRestController(VetRepository clinicService) {
		this.vets = clinicService;
	}

	@GetMapping("/vets")
	private Collection<Vet> findListForOwnersLastName() {
		return vets.findAll();
	}

}
