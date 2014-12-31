package ee.liiser.siim.galaxies.calculations;

import javax.vecmath.Vector3d;

import ee.liiser.siim.galaxies.data.BaseObject;
import ee.liiser.siim.galaxies.data.Core;
import ee.liiser.siim.galaxies.data.Star;
import ee.liiser.siim.galaxies.data.basic.BasicObject;
import ee.liiser.siim.galaxies.data.velocity.VelocityObject;
import ee.liiser.siim.galaxies.drawing.Drawable;

/**
 * The class handling calculating the positions and movement of stars and galaxies
 * 
 * Holds algorithms for all (currently 2) methods
 */
public class Calculator {

	private Core[] cores;
	private Star[] stars;
	
	/**
	 * Timestep of the calculation.
	 * Decrease this to improve accuracy, increase this to improve performance
	 */
	public static double dt = 0.001;

	/**
	 * Enum holding the possible calculation methods
	 */
	public enum Method {
		BASIC_VERLET, VELOCITY_VERLET
	}

	private final Method method;

	Calculator(Core[] cores, Star[] stars, Method method) {
		this.cores = cores;
		this.stars = stars;
		this.method = method;
	}

	/**
	 * Advances all cores and stars forward by 1 timestep
	 */
	void step() {
		switch (method) {
		case BASIC_VERLET:
			for (Star star : stars) {
				updatePos((BasicObject) star);
			}
			for (Core core : cores) {
				updatePos((BasicObject) core);
			}
			break;
		case VELOCITY_VERLET:
			for (Star star : stars) {
				updatePos((VelocityObject) star);
			}
			for (Core core : cores) {
				updatePos((VelocityObject) core);
			}
			break;
		default:
			throw new RuntimeException("Disallowed computation method");
		}
	}

	/**
	 * Method that uses the velocity method to advance the position of the given point
	 * 
	 * @param point whose position should be updated
	 */
	private void updatePos(VelocityObject point) {
		// Velocity Verlet'
		Vector3d v05 = new Vector3d();
		v05.scale(dt / 2, acc(point));
		v05.add(point.getVelocity());
		// v(t+dt/2) = v + a*dt/2

		Vector3d newPos = new Vector3d();
		newPos.scale(dt, v05);
		newPos.add(point.getPosition());
		point.setPosition(newPos);
		// x(t+dt) = x + v05*dt

		Vector3d newVel = new Vector3d();
		newVel.scale(dt / 2, acc(point));
		newVel.add(v05);
		point.setVelocity(newVel);
		// v(t+dt) = v05 + a(t+dt)*dt/2
	}

	/**
	 * Method that uses the basic Verlet' method to advance the position of the
	 * given point
	 * 
	 * @param point
	 *            whose position should be updated
	 */
	private void updatePos(BasicObject point) {
		// Basic Verlet':
		Vector3d newPos = new Vector3d();
		newPos.scale(2, point.getPosition());
		newPos.sub(point.getOldPosition());
		Vector3d a = acc(point);
		a.scale(dt * dt);
		newPos.add(a);
		// newPos = 2*pos - oldPos + acc(pos)*dt^2
		point.updatePosition(newPos);

	}

	/**
	 * A method that finds the acceleration acting upon a point.
	 * Only uses galaxy cores and not other stars when calculating gravity.
	 * 
	 * @param point to find the acceleration for
	 * @return the acceleration acting upon the point
	 */
	private Vector3d acc(BaseObject point) {
		Vector3d a = new Vector3d();
		for (Core core : cores) {
			if (core == point)
				continue;
			Vector3d relPos = new Vector3d();
			// r.12
			relPos.sub(((Drawable) core).getPosition(), point.getPosition());

			// m * r12 / len(r12)^3
			relPos.scale((double) (core.getMass() / Math.pow(relPos.length(), 3)));

			// a +=
			a.add(relPos);
		}
		return a;
	}
}
