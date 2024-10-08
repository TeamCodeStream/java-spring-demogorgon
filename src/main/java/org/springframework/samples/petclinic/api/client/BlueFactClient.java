package org.springframework.samples.petclinic.api.client;

import com.newrelic.api.agent.Trace;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.samples.petclinic.Util.doWait;

@Component
public class BlueFactClient {
	private static final Logger logger = LoggerFactory.getLogger(BlueFactClient.class);
	private final RestClient restClient;
	private static final List<String> animals = Arrays.asList("dogs", "cats", "dolphins");

	public BlueFactClient(RestClient.Builder restClientBuilder, @Value("${petfacts.baseurl}") String baseUrl) {
		this.restClient = restClientBuilder.baseUrl(baseUrl).build();
		logger.info("BlueFactClient created with baseurl: {}", baseUrl);
	}

	@Trace
	@Timed
	public List<String> fetchBlueFacts() {
		doWait(60);

		final var facts = new ArrayList<String>();
		for (String animal : animals) {
			final var animalFact = this.restClient.get().uri("/{animal}", animal).retrieve().body(AnimalFact.class);
			assert animalFact != null;
			facts.add(animalFact.getFact());
		}

		return facts;
	}
}
