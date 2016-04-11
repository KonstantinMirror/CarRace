package epamlab;

import org.apache.log4j.Logger;

public class Car implements Runnable {

	private static final long MAX_DISTANCE = 10000;
	Logger log = Logger.getLogger(getClass());
	private long friction;
	private long distance;
	private String name;

	public Car(String name, long friction) {
		this.name = name;
		this.friction = friction;
	}

	@Override
	public void run() {
		
		try {
			StartManager.latch.await();
			while (distance < MAX_DISTANCE) {
				Thread.sleep(friction);
				distance += 100;
				log.info(name + " " + distance);
			}
			Long time = System.currentTimeMillis();
			StartManager.competitors.put(name, time);
		} catch (InterruptedException e) {
			log.error(e);
		} 
	}
}
