package org.springframework.samples.petclinic.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newrelic.api.agent.Trace;
import io.micrometer.core.annotation.Timed;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.samples.petclinic.Util.doWait;

@Component
public class GreenFactClient {
	private static final Logger logger = LoggerFactory.getLogger(BlueFactClient.class);
	private final ObjectMapper objectMapper;
	private final String baseUrl;
	private static final List<String> animals = Arrays.asList("hamsters", "goats");

	public GreenFactClient(ObjectMapper objectMapper, @Value("${petfacts.baseurl}") String baseUrl) {
		this.objectMapper = objectMapper;
		this.baseUrl = baseUrl;
		logger.info("GreenFactClient created with baseurl: " + baseUrl);
	}

	public List<String> fetchGreenFacts() {
		final var facts = new ArrayList<String>();
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			for (String animal : animals) {
				doWait(15);
				HttpGet get = new HttpGet(baseUrl + "/" + animal);
				try (CloseableHttpResponse response = httpClient.execute(get)) {
					final var animalFact = objectMapper.readValue(response.getEntity().getContent(), AnimalFact.class);
					facts.add(animalFact.getFact());
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return facts;
	}
}
