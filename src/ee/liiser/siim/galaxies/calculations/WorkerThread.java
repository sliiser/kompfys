package ee.liiser.siim.galaxies.calculations;

import ee.liiser.siim.galaxies.calculations.Calculator.Method;
import ee.liiser.siim.galaxies.data.Core;
import ee.liiser.siim.galaxies.data.Star;

/**
 * Thread handling the main calculation of the application.
 *
 */
public class WorkerThread extends Thread {

	private Calculator calc;
	private int pauseStep = Integer.MAX_VALUE;
	private int step = 0;

	public WorkerThread(Core[] cores, Star[] stars, Method method) {
		calc = new Calculator(cores, stars, method);
	}

	@Override
	public void run() {
		try {
			synchronized (this) {
				while (true) {
					if (paused())
						wait();
					else {
						calc.step();
						step++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pause() {
		pauseStep = step;
	}

	public synchronized void cont() {
		if (paused()) {
			pauseStep = Integer.MAX_VALUE;
			notify();
		}
	}

	public void step() {
		step(1);
	}

	public void step(int n) {
		if (paused()) {
			pauseStep = step + n;
			synchronized (this) {
				notify();
			}
		} else {
			pause();
		}
	}

	public boolean paused() {
		return pauseStep <= step;
	}

	public void togglePause() {
		if (paused())
			cont();
		else
			pause();

	}

}
