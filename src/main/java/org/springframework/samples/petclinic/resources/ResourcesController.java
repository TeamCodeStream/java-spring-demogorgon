package org.springframework.samples.petclinic.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ResourcesController {

	/*
		SUPER MAJOR HACK TO REWRITE STATIC RESOURCE PATHS FOR THE
		REACT FRONTEND
	 */
	@GetMapping("assets/{path}")
	public String rewriteStaticPath(@PathVariable String path){
		return "redirect:/resources/react/assets/" + path;
	}
}
