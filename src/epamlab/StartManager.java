package epamlab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StartManager {

	public static ConcurrentMap<String, Long> competitors;
	public static CountDownLatch latch;
	private static final long MAX_RUNNING_TIME = 5000;

	public static void startRase(Car... cars) {
		latch = new CountDownLatch(1);
		competitors = new ConcurrentHashMap<>();
		List<Thread> threadsList = new ArrayList<>();
		for (Car car : cars) {
			threadsList.add(new Thread(car));

		}

		for (Thread thread : threadsList) {
			thread.start();
		}
		latch.countDown();
		try {
			Thread.sleep(MAX_RUNNING_TIME);
			for (Thread thread : threadsList) {
				if (thread.isAlive()) {
					thread.interrupt();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static String getWinner() {
		Long minTime = null;
		String nameWinner = null;
		for (String name : competitors.keySet()) {
			Long currentTime = competitors.get(name);
			if (minTime == null || (minTime.compareTo(currentTime) > 0)) {
				nameWinner = name;
				minTime = currentTime;
			}
		}
		return ("Winner is " + nameWinner);
	}
}
