package ee.liiser.siim.galaxies;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.vecmath.Vector3d;

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
	// FIXME should not be public
	public static Core[] cores;

	/**
	 * Number of stars per core (galaxy)
	 */
	private static final int STARCOUNT = 500;

	/**
	 * Calculation method to use. Value should be one of
	 * {@link Calculator.Method}
	 */
	private static Method METHOD = Method.VELOCITY_VERLET;

	public static void main(String[] args) {
		Galaxy[] galaxies = null;

		if (args.length >= 2)
			setConf(args);

		ObjectFactory factory = new ObjectFactory(METHOD);

		if (args.length >= 1) {
			galaxies = makeGalaxies(args, factory);
		} else {
			galaxies = makeDefaultGalaxies(factory);
		}
		run(galaxies);

	}
	
	/**
	 * Main entry point of the application. Call this with an array of galaxies
	 * to simulate their movement
	 * 
	 * @param galaxies
	 *            is the array of galaxies to be simulated
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

	private static Galaxy[] makeDefaultGalaxies(ObjectFactory factory) {
		// Default galaxy configuration
		Galaxy galaxy1 = factory.makeGalaxy(new Vector3d(), new Vector3d(),
				new Vector3d(0, 0, 1), 1, STARCOUNT);
		Galaxy galaxy2 = factory.makeGalaxy(new Vector3d(0, 5, -30),
				new Vector3d(0, 0, 0.5), new Vector3d(0, 1, 0), 1, STARCOUNT);

		return new Galaxy[] { galaxy1, galaxy2 };
	}

	private static Galaxy[] makeGalaxies(String[] args, ObjectFactory factory) {

		try {
			BufferedReader r = new BufferedReader(new FileReader(args[0]));
			ArrayList<Galaxy> list = new ArrayList<Galaxy>();
			while (true) {
				String line = r.readLine();
				if (line == null)
					break;
				if (line.isEmpty())
					break;
				if (line.startsWith("#"))
					continue;

				list.add(factory.makeGalaxy(line));

			}
			r.close();
			Galaxy[] galaxies = new Galaxy[list.size()];
			galaxies = list.toArray(galaxies);
			return galaxies;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	private static void setConf(String[] args) {
		try {
			BufferedReader r = new BufferedReader(new FileReader(args[1]));
			String line;
			do {
				line = r.readLine();
			} while (line != null && line.startsWith("#"));
			r.close();
			String[] params = line.split(",");
			if (params[0].toLowerCase().equals("velocity")) {
				METHOD = Method.VELOCITY_VERLET;
			} else if (params[0].toLowerCase().equals("basic")) {
				METHOD = Method.BASIC_VERLET;
			} else {
				throw new IllegalArgumentException(params[0]
						+ " is not a method in " + args[1]);
			}
			Calculator.dt = Double.parseDouble(params[1]);
			GraphicsThread.fps = Double.parseDouble(params[2]);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
