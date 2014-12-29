package ee.liiser.siim.galaxies;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.calculations.Calculator;
import ee.liiser.siim.galaxies.calculations.Calculator.Method;
import ee.liiser.siim.galaxies.calculations.WorkerThread;
import ee.liiser.siim.galaxies.data.Core;
import ee.liiser.siim.galaxies.data.Galaxy;
import ee.liiser.siim.galaxies.data.ObjectFactory;
import ee.liiser.siim.galaxies.data.Star;
import ee.liiser.siim.galaxies.drawing.Drawable;
import ee.liiser.siim.galaxies.drawing.GraphicsThread;

public class Main {

	private static Star[] stars;
	public static Core[] cores;

	/**
	 * Number of stars per core (galaxy)
	 */
	private static final int STARCOUNT = 500;

	private static final Method METHOD = Method.BASIC_VERLET;

	public static void main(String[] args) {

		ObjectFactory factory = new ObjectFactory(METHOD);

		Galaxy galaxy1 = factory.makeGalaxy(new Vector3f(), new Vector3f(), new Vector3f(0,0,1), 1, STARCOUNT);
//		Galaxy galaxy2 = factory.makeGalaxy(new Vector3f(0,5,-30), new Vector3f(0,0,0.5f), new Vector3f(0,1,0), 1, STARCOUNT);
		Galaxy galaxy2 = factory.makeGalaxy(new Vector3f(0,5,-30), new Vector3f(0,5,-30 - 0.5f*Calculator.dt), new Vector3f(0,1,0), 1, STARCOUNT);
		
		Galaxy[] galaxies = new Galaxy[]{galaxy1, galaxy2};
		
		run(galaxies);
		
	}
	
	private static void run(Galaxy[] galaxies){
		
		cores = new Core[galaxies.length];
		stars = new Star[STARCOUNT * cores.length];
		
		int starIndex = 0;
		for(int i = 0; i < galaxies.length; i++){
			cores[i] = galaxies[i].getCore();
			System.arraycopy(galaxies[i].getStars(), 0, stars, starIndex, galaxies[i].getStars().length);
			starIndex += galaxies[i].getStars().length;
		}

		Drawable[] points = new Drawable[stars.length + cores.length];
		System.arraycopy(cores, 0, points, 0, cores.length);
		System.arraycopy(stars, 0, points, cores.length, stars.length);

		new WorkerThread(cores, stars, METHOD).start();
		new GraphicsThread(points).start();
	}

}
