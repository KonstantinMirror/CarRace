package epamlab;

import java.util.Date;

public class Car implements Runnable {
	
	private static final long MAX_DISTANCE = 10000;
	// Logger log = Logger.getLogger(getClass());
	private long friction;
	private long distance;
	private String name;

	public Car(String name, long friction) {
		this.name = name;
		this.friction = friction;
	}

	@Override
	public void run() {
		while(!StartManager.startFlag);
		
		try {
			while (distance < MAX_DISTANCE) {
				Thread.sleep(friction);
				distance += 100;
				//log.info(name + " " + distance);
			}
			Date date = new Date();
			StartManager.competitors.put(name, date);
		} catch (InterruptedException e) {
			//log.error(e);
		}
	}

}
