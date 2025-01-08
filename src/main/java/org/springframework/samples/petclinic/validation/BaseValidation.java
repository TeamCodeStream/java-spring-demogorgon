package org.springframework.samples.petclinic.validation;

public class BaseValidation {
	public static void IntegerMustBeGreaterThan(Integer a, Integer b, String label) {
		if(a < b){
			throw new IllegalArgumentException(label + " must be greater than " + b);
		}
	}
}
