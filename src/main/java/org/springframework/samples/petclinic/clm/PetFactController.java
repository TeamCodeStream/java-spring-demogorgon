package org.springframework.samples.petclinic.clm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.MetricService;
import org.springframework.samples.petclinic.api.PetFactResponse;
import org.springframework.samples.petclinic.api.PetFactService;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.samples.petclinic.Util.doWait;

@RestController
public class PetFactController {
	private final PetFactService petFactService;

	private static final Logger logger = LoggerFactory.getLogger(PetFactController.class);

	public PetFactController(PetFactService petFactService) {
		this.petFactService = petFactService;
	}

	// 736.551164049810504 for http actuator
	@GetMapping(value = "/clm/facts/blue", produces = MediaType.APPLICATION_JSON_VALUE)
	public PetFactResponse getPetFactsBlue() {
		final var sw = new StopWatch();
		sw.start();
		doWait(50);
		logger.info("Getting blue pet facts");
		final var result = petFactService.getBluePetFacts();
		sw.stop();
		final var elapsed = sw.getTotalTimeMillis();
		MetricService.increaseCount("/clm/facts/blue", elapsed);
		return result;
	}

	@GetMapping(value = "/clm/facts/green", produces = MediaType.APPLICATION_JSON_VALUE)
	public PetFactResponse getPetFactsGreen() {
		final var sw = new StopWatch();
		sw.start();
		doWait(50);
		logger.info("Getting green pet facts");
		final var result = petFactService.getGreenPetFacts();
		sw.stop();
		final var elapsed = sw.getTotalTimeMillis();
		MetricService.increaseCount("/clm/facts/green", elapsed);
		return result;
	}
}
