package org.springframework.samples.petclinic.validation;

public class ControllerValidation {
	public static void ValidatePageNumber(int pageNumber) throws IllegalArgumentException {
		BaseValidation.IntegerMustBeGreaterThan(pageNumber, 0, "Page Number");
	}

	public static void ValidateNotNull(Object object, String name) throws IllegalArgumentException {
		if (object == null) {
			throw new IllegalArgumentException(name + " cannot be null");
		}
	}
}
