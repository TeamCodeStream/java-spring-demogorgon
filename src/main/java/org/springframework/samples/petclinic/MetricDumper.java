package org.springframework.samples.petclinic;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MetricDumper {
	@Scheduled(fixedDelay = 60000)
	public void dump() {
		MetricService.dumpMetrics();
	}
}
