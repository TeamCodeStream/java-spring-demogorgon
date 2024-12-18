package org.springframework.samples.petclinic.clm;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.Trace;
import io.micrometer.core.annotation.Timed;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.MetricService;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

import java.util.Date;

import static org.springframework.samples.petclinic.Util.*;

@Controller
public class ClmController {

	private final OwnerRepository ownerRepository;
	private final RestClient restClient;

	private static final Logger logger = LoggerFactory.getLogger(ClmController.class);

	public ClmController(OwnerRepository ownerRepository,  RestClient.Builder restClientBuilder) {
		this.restClient = restClientBuilder.build();
		this.ownerRepository = ownerRepository;
	}

	/**
	 * This method demonstrates an auto instrumented method.
	 */
	@GetMapping("/clm/auto-only")
	public String autoOnly(Model model) {
		MetricService.increaseCount("/clm/auto-only");
		setMessage(model, "Java/org.springframework.samples.petclinic.clm.ClmController/autoOnly");
		logger.info("/clm/auto-only");
		doWait();
		return "welcome";
	}

	/**
	 * Besides the auto instrumentation, this also demonstrates a manual trace created by an annotation.
	 */
	@GetMapping("/clm/annotation")
	public String annotation(Model model) {
		MetricService.increaseCount("/clm/annotation");
		logger.info("/clm/annotation");
		setMessage(model, "Java/org.springframework.samples.petclinic.clm.ClmController/annotation");
		annotatedMethod();
		waitWithDeviation(200);
		return "welcome";
	}

	@Trace
	private void annotatedMethod() {
		if (timeForFakeError()) {
			waitWithDeviation(2000);
		}
		waitWithDeviation(200);
	}

	/**
	 * Besides the auto instrumentation, this also demonstrates a manual trace created using the API.
	 */
	@GetMapping("/clm/api")
	public String api(Model model) {
		MetricService.increaseCount("/clm/api");
		logger.info("/clm/api");
		setMessage(model, "Java/org.springframework.samples.petclinic.clm.ClmController/api");
		apiMethod();
		doWait();
		return "welcome";
	}

	@Timed
	public void apiMethod() {
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("segmentCreatedByTheApi");
		doWait();
		segment.end();
	}

	/**
	 * Besides the auto instrumentation, this also demonstrates a trace created by XML instrumentation.
	 */
	@GetMapping("/clm/xml")
	public String xml(Model model) {
		logger.info("/clm/xml");
		MetricService.increaseCount("/clm/xml");
		setMessage(model, "Java/org.springframework.samples.petclinic.clm.ClmController/xml");
		xmlMethod();
		doWait();
		return "welcome";
	}

	@Timed
	public void xmlMethod() {
		doWait(270);
	}

	/**
	 * Besides the auto instrumentation, this also demonstrates a trace in a static method.
	 */
	@GetMapping("/clm/static")
	public String staticRequest(Model model) {
		MetricService.increaseCount("/clm/static");
		logger.info("/clm/static");
		setMessage(model, "Java/org.springframework.samples.petclinic.clm.ClmController/static");
		staticMethod();
		doWait();
		return "welcome";
	}

	@Trace
	private static void staticMethod() {
		doWait();
	}


	/**
	 * Besides the auto instrumentation, this also demonstrates a trace that contains an Http request.
	 */
	// 253.02907133243607 from actuator
	@GetMapping("/clm/http")
	public String http(Model model) {
		MetricService.increaseCount("/clm/http");
		logger.info("/clm/http");
		setMessage(model, "Java/org.springframework.samples.petclinic.clm.ClmController/http");
		httpMethod();
		doWait();
		return "welcome";
	}

	@Trace
	private void httpMethod() {
		final var sw = new StopWatch();
		logger.info("httpMethod()");
		sw.start();
		try{
		final var status = this.restClient.get().uri("https://google.com").retrieve().toBodilessEntity();
		System.out.printf("Status Code: %s", status.getStatusCode());
		} catch (Exception e) {
			MetricService.increaseCount("httpMethod() error");
			throw new RuntimeException(e);
		}
		sw.stop();
		final var elapsed = sw.getTotalTimeMillis();
		MetricService.increaseCount("httpMethod()", elapsed);
	}


	/**
	 * Besides the auto instrumentation, this also demonstrates a trace that has a db call.
	 */
	@GetMapping("/clm/db")
	public String db(Model model) {
		MetricService.increaseCount("/clm/db");
		logger.info("/clm/db");
		setMessage(model, "Java/org.springframework.samples.petclinic.clm.ClmController/db");
		dbMethod();
		doWait();
		return "welcome";
	}

	@Trace
	private void dbMethod() {
		ownerRepository.findAll(Pageable.ofSize(1000000));
	}


	private void setMessage(Model model, String spanName) {
		model.addAttribute("message",
			new Date() + ": Look for a span named \"" + spanName + "\" and its children.");
	}

	@PostConstruct
	@Trace(dispatcher = true)
	void init() {
		doWait();
	}
}
