package org.springframework.samples.petclinic.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestClientSsl;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.samples.petclinic.Util.doWait;

@Component
public class GreenFactClient {
	private static final Logger logger = LoggerFactory.getLogger(GreenFactClient.class);
	private final RestClient restClient;
	private static final List<String> animals = Arrays.asList("hamsters", "goats");

	public GreenFactClient(RestClient.Builder restClientBuilder, @Value("${petfacts.baseurl}") String baseUrl) {
		this.restClient = restClientBuilder.baseUrl(baseUrl).build();
		logger.info("GreenFactClient created with baseurl: {}", baseUrl);
	}

	public List<String> fetchGreenFacts() {
		final var facts = new ArrayList<String>();

		for (String animal : animals) {
			doWait(15);
			final var animalFact = this.restClient.get().uri("/{animal}", animal).retrieve().body(AnimalFact.class);
			assert animalFact != null;
			facts.add(animalFact.getFact());
		}

		return facts;
	}
}
