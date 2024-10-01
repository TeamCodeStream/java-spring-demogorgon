package org.springframework.samples.petclinic.api;

import java.util.List;

public class PetFactResponse {
	private final List<String> petFacts;

	public PetFactResponse(List<String> petFacts) {
        this.petFacts = petFacts;
    }

	public List<String> getPetFacts() {
		return petFacts;
	}
}
