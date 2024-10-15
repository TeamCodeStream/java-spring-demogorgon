/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Arrays;
import java.util.List;

import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RequestPredicates.pathExtension;
import static org.springframework.web.servlet.function.RouterFunctions.route;

/**
 * PetClinic Spring Boot Application.
 *
 * @author Dave Syer
 */
@SpringBootApplication
@ImportRuntimeHints(PetClinicRuntimeHints.class)
@EnableScheduling
public class PetClinicApplication {
	private static final Logger logger = LoggerFactory.getLogger(PetClinicApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PetClinicApplication.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> spaRouter() {
		logger.info("Setting up SPA router");
		ClassPathResource index = new ClassPathResource("static/react/index.html");
		if (!index.exists()) {
			logger.error("No react app found!!");
			return null;
		}
		List<String> extensions = Arrays.asList("js", "css", "svg", "ico", "png", "jpg", "gif", "eot", "ttf",
			"woff", "woff2");
		RequestPredicate spaPredicate = path("/react/**").and(pathExtension(extensions::contains).negate());
		return route().resource(spaPredicate, index).build();
	}

}

