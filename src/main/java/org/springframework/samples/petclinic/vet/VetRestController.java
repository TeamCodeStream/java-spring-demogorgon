package org.springframework.samples.petclinic.vet;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class VetRestController {

	private final VetRepository vets;
	private static final Logger logger = LoggerFactory.getLogger(VetRestController.class);

	public VetRestController(VetRepository clinicService) {
		this.vets = clinicService;
	}

	@GetMapping("/vets")
	private Collection<Vet> findListForOwnersLastName() {
		logger.info("Searching ALL Vets");
		return vets.findAll();
	}

}
