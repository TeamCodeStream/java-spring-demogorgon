package org.springframework.samples.petclinic.api;

import com.newrelic.api.agent.Trace;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.api.client.BlueFactClient;
import org.springframework.samples.petclinic.api.client.GreenFactClient;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class PetFactService {
	private static final Logger logger = LoggerFactory.getLogger(PetFactService.class);
	private final GreenFactClient greenFactClient;
	private final BlueFactClient blueFactClient;

	public PetFactService(GreenFactClient greenFactClient, BlueFactClient blueFactClient) {
		this.greenFactClient = greenFactClient;
		this.blueFactClient = blueFactClient;
	}

	// 685ms from actuator?
	@Timed
	@Trace
	public PetFactResponse getBluePetFacts() {
		return new PetFactResponse(blueFactClient.fetchBlueFacts());
	}

	public PetFactResponse getGreenPetFacts() {
		final var facts = greenFactClient.fetchGreenFacts();
		logger.info("buildReport2 (heap)");
		buildReport2(facts);
		logger.info("buildReport1 (cpu)");
		buildReport1(facts);
		logger.info("reports done");
		return new PetFactResponse(facts);
	}

	// Burn some cpu
	private void buildReport1(List<String> facts) {
		var bi = BigInteger.valueOf(1);
		for (int i = 0; i < 500000; i++) {
			bi = bi.add(bi);
		}
	}

	private void buildReport2(List<String> facts) {
		for (int outer = 0; outer < 25; outer++) {
			var builder = new StringBuilder();
			for (int i = 0; i < 500000; i++) {
				builder.append("foosball".repeat(50));
			}
		}
	}
}
