package org.springframework.samples.petclinic.loggers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.vet.Vet;

public class VetLogger {
	private static final Logger logger = LoggerFactory.getLogger(VetLogger.class);

	public static void LogSpecialty(Vet vet){
		logger.info("Vet Speciality='{}'", vet.getSpecialties().get(0).getName());
	}
}
