package epamlab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StartManager {

	public static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	public static ConcurrentMap<String, Date> competitors = new ConcurrentHashMap<>();
	private static final long MAX_RUNNING_TIME = 5000;


	public static void startRase(Car... cars) {
		List<Thread> threadsList = new ArrayList<>();
		for (Car car : cars) {
			threadsList.add(new Thread(car));

		}
		Lock lock = readWriteLock.writeLock();
		lock.lock();
		for (Thread thread : threadsList) {
			thread.start();
		}
		lock.unlock();
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
		Date minTime = null;
		String nameWinner = null;
		for (String name : competitors.keySet()) {
			Date currentDate = competitors.get(name);
			if (minTime == null || (minTime.compareTo(currentDate) > 0)) {
				nameWinner = name;
				minTime = currentDate;
			}
		}
		return ("Winner is " + nameWinner);
	}
}
