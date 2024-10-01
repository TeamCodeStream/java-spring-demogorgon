package org.springframework.samples.petclinic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetricService {
	private static final Logger logger = LoggerFactory.getLogger(MetricService.class);

	private static final Map<String, List<Long>> reqMap = new HashMap<>();

	public static void increaseCount(String request) {
		MetricService.increaseCount(request, null);
	}

	public static void increaseCount(String request, @Nullable Long elapsed) {
		synchronized (reqMap) {
			final var responseTimes = reqMap.getOrDefault(request, new ArrayList<>());
			if (elapsed == null) {
				responseTimes.add(-1L);
			} else {
				responseTimes.add(elapsed);
			}
			reqMap.put(request, responseTimes);
		}
	}

	public static void dumpMetrics() {
		synchronized (reqMap) {
			for (Map.Entry<String, List<Long>> entry : reqMap.entrySet()) {
				List<Long> responseTimeList = entry.getValue();
				final var count = responseTimeList.size();
				final var avg = Math.round(responseTimeList.stream().mapToLong(l -> l).average().orElse(0));
				logger.info(entry.getKey() + " " + count + " rpm" + " " + avg + " ms");
			}
			reqMap.clear();
		}
	}
}

