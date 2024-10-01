package org.springframework.samples.petclinic.api.client;

import java.util.Objects;

public class AnimalFact {
	private String animal;
	private String fact;

	public AnimalFact() {
	}

	public String getAnimal() {
		return animal;
	}

	public void setAnimal(String animal) {
		this.animal = animal;
	}

	public String getFact() {
		return fact;
	}

	public void setFact(String fact) {
		this.fact = fact;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AnimalFact that = (AnimalFact) o;
		return Objects.equals(animal, that.animal) && Objects.equals(fact, that.fact);
	}

	@Override
	public int hashCode() {
		return Objects.hash(animal, fact);
	}

	@Override
	public String toString() {
		return "AnimalFact{" +
			"animal='" + animal + '\'' +
			", fact='" + fact + '\'' +
			'}';
	}
}
