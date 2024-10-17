package org.springframework.samples.petclinic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MetricDumper {
	private static final Logger logger = LoggerFactory.getLogger(MetricDumper.class);

	@Scheduled(fixedDelay = 60000)
	public void dump() {
		logger.info("MetricDumper - Dumping All Metrics");
		MetricService.dumpMetrics();
	}
}
