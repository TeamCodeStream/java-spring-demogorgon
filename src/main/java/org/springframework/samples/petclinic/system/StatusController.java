package org.springframework.samples.petclinic.system;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class StatusController {
	@GetMapping("/status/check")
	@ResponseStatus(HttpStatus.OK)
	public void Check() {
		// all good
	}

}
