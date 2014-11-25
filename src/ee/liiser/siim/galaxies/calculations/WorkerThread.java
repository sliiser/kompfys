package ee.liiser.siim.galaxies.calculations;

import ee.liiser.siim.galaxies.calculations.Calculator.Method;
import ee.liiser.siim.galaxies.data.Core;
import ee.liiser.siim.galaxies.data.Star;

public class WorkerThread extends Thread{
	
	Calculator calc;
	
	public WorkerThread(Core[] cores, Star[] stars, Method method) {
		calc = new Calculator(cores, stars, method);
	}
	
	@Override
	public void run(){
		while(true){
//			try {
//				Thread.sleep(2);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			calc.step();
		}
	}

}
