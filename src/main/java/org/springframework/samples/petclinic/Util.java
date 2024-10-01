package org.springframework.samples.petclinic;

import java.time.LocalDate;
import java.util.Random;

/**
 * Simple method that waits so the spans take some time to execute.
 */
public class Util {
	private static final Random random = new Random();
	public static void doWait() {
		doWait(200);
	}

	public static void doWait(long timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException ex) {
			// ignore
		}
	}

	public static void waitWithDeviation(int median) {
		double standardDeviation = (double) median / 4;

		long waitTime = Math.round(median + random.nextGaussian() * standardDeviation);

		if (waitTime < 0) {
			waitTime = 0;
		}

		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			// ignore
		}
	}

	public static boolean isEvenDay() {
		return LocalDate.now().getDayOfYear() % 2 == 0;
	}

	public static boolean timeForFakeError() {
		String nowMillisStr = Long.toString(System.currentTimeMillis());
		return nowMillisStr.endsWith("7");
	}
}
