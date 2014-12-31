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
import ee.liiser.siim.galaxies.drawing.PauseListener;

/**
 * Main entry-point of the application and the main class
 *
 */
public class Main {

	/**
	 * An array to hold all stars in the system
	 */
	private static Star[] stars;
	/**
	 * An array to hold all galaxy cores in the system
	 */
	//FIXME should not be public
	public static Core[] cores;

	/**
	 * Number of stars per core (galaxy)
	 */
	private static final int STARCOUNT = 500;

	/**
	 * Calculation method to use. Value should be one of
	 * {@link Calculator.Method}
	 */
	private static final Method METHOD = Method.BASIC_VERLET;

	public static void main(String[] args) {

		ObjectFactory factory = new ObjectFactory(METHOD);

		Galaxy galaxy1 = factory.makeGalaxy(new Vector3f(), new Vector3f(), new Vector3f(0,0,1), 1, STARCOUNT);
		//Example galaxy for the Velocity method
		Galaxy galaxy2 = factory.makeGalaxy(new Vector3f(0,5,-30), new Vector3f(0,0,0.5f), new Vector3f(0,1,0), 1, STARCOUNT);
		
		Galaxy[] galaxies = new Galaxy[]{galaxy1, galaxy2};
		
		run(galaxies);
		
	}

	/**
	 * Main entry point of the application. Call this with an array of galaxies
	 * to simulate their movement
	 * 
	 * @param galaxies is the array of galaxies to be simulated
	 */
	public static void run(Galaxy[] galaxies) {

		cores = new Core[galaxies.length];
		stars = new Star[STARCOUNT * cores.length];

		int starIndex = 0;
		for (int i = 0; i < galaxies.length; i++) {
			cores[i] = galaxies[i].getCore();
			System.arraycopy(galaxies[i].getStars(), 0, stars, starIndex,
					galaxies[i].getStars().length);
			starIndex += galaxies[i].getStars().length;
		}

		Drawable[] points = new Drawable[stars.length + cores.length];
		System.arraycopy(cores, 0, points, 0, cores.length);
		System.arraycopy(stars, 0, points, cores.length, stars.length);

		WorkerThread t = new WorkerThread(cores, stars, METHOD);
		new GraphicsThread(points, new PauseListener(t)).start();
		t.start();
	}

}
