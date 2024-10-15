package org.springframework.samples.petclinic.clm;

import com.newrelic.api.agent.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class RandomExceptionGenerator {

	private static final Logger logger = LoggerFactory.getLogger(RandomExceptionGenerator.class);

	private static final String[] words = {
		"Quantum", "Flux", "Anomaly", "Paradox", "Entropy", "Singularity", "Nebula", "Vortex", "Pulsar", "Quasar",
		"Eclipse", "Galaxy", "Meteor", "Orbit", "Blackhole", "Asteroid", "Comet", "Supernova", "Cosmos", "Universe",
		"Regurgitate", "Flummox", "Bamboozle", "Discombobulate", "Brouhaha", "Kerfuffle", "Hullabaloo", "Shenanigans"
	};

	private static final List<Class<? extends RuntimeException>> COMMON_EXCEPTIONS = List.of(
		IllegalArgumentException.class,
		IllegalStateException.class,
		NullPointerException.class,
		IndexOutOfBoundsException.class,
		UnsupportedOperationException.class
	);

	private static final AtomicReference<RuntimeException> currentException = new AtomicReference<>();
	private static final ReentrantLock lock = new ReentrantLock();
	private static long lastChangeTime = 0;

	@Trace
	public static RuntimeException generateRandomException() {
		long currentTime = System.currentTimeMillis();
		lock.lock();
		try {
			if (currentException.get() == null || (currentTime - lastChangeTime) >= 15 * 60 * 1000) {
				currentException.set(createNewException());
				lastChangeTime = currentTime;
			}
		} finally {
			lock.unlock();
		}
		return currentException.get();
	}

	private static RuntimeException createNewException() {
		Random random = new Random();
		int index = random.nextInt(COMMON_EXCEPTIONS.size());
		String message = generateRandomMessage(random);
		try {
			return COMMON_EXCEPTIONS.get(index).getConstructor(String.class).newInstance(message);
		} catch (Exception e) {
			// Fallback in case reflection fails
			return new RuntimeException("Failed to create a new exception instance.", e);
		}
	}

	private static String generateRandomMessage(Random random) {
		StringBuilder message = new StringBuilder();
		int length = 3 + random.nextInt(5); // Generate a message of 3 to 7 words
		for (int i = 0; i < length; i++) {
			message.append(words[random.nextInt(words.length)]);
			if (i < length - 1) {
				message.append(" ");
			}
		}
		String finalMessage = message.toString();
		logger.info("Generated Random Message: {}", finalMessage);

		return finalMessage;
	}
}
