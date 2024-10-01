package org.springframework.samples.petclinic.clm;

import com.newrelic.api.agent.Trace;
import org.springframework.stereotype.Service;

@Service
public class ErrorService {

	@Trace
	public void iAmError() {
		throw RandomExceptionGenerator.generateRandomException();
	}
}
