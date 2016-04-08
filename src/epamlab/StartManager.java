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

	public static void main(String[] args) {
		List<Thread> threadsList = new ArrayList<>();
		threadsList.add(new Thread(new Car("Piter", 10)));
		threadsList.add(new Thread(new Car("Alex", 9)));
		threadsList.add(new Thread(new Car("Mikle", 9)));
		Lock lock = readWriteLock.writeLock();
		lock.lock();
		for (Thread thread : threadsList) {
			thread.start();
		}
		lock.unlock();
		try {
			Thread.sleep(5000);
			for (Thread thread : threadsList) {
				if (thread.isAlive()) {
					thread.interrupt();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Date minTime = null;
		String nameWinner = null;
		for (String name : competitors.keySet()) {
			Date currentDate = competitors.get(name);
			if (minTime == null || (minTime.compareTo(currentDate) > 0)) {
				nameWinner = name;
				minTime = currentDate;
			}
		}
		System.out.println("Winner is " + nameWinner);

	}
}
