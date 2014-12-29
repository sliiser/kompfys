package ee.liiser.siim.galaxies.calculations;

import ee.liiser.siim.galaxies.calculations.Calculator.Method;
import ee.liiser.siim.galaxies.data.Core;
import ee.liiser.siim.galaxies.data.Star;

/**
 * Thread handling the main calculation of the application.
 *
 */
public class WorkerThread extends Thread{
	
	private Calculator calc;
	
	public WorkerThread(Core[] cores, Star[] stars, Method method) {
		calc = new Calculator(cores, stars, method);
	}
	
	@Override
	public void run(){
		while(true){
			calc.step();
		}
	}

}
