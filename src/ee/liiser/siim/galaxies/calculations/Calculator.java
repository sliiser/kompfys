package ee.liiser.siim.galaxies.calculations;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.data.BaseObject;
import ee.liiser.siim.galaxies.data.Core;
import ee.liiser.siim.galaxies.data.Star;
import ee.liiser.siim.galaxies.data.basic.BasicCore;
import ee.liiser.siim.galaxies.data.basic.BasicObject;
import ee.liiser.siim.galaxies.data.basic.BasicStar;
import ee.liiser.siim.galaxies.data.velocity.VelocityCore;
import ee.liiser.siim.galaxies.data.velocity.VelocityObject;
import ee.liiser.siim.galaxies.data.velocity.VelocityStar;
import ee.liiser.siim.galaxies.drawing.Drawable;

public class Calculator {

	private Core[] cores;
	private Star[] stars;
	public static float dt = 0.001f;

	public enum Method {
		BASIC_VERLET, VELOCITY_VERLET
	}

	private final Method method;

	public Calculator(Core[] cores, Star[] stars, Method method) {
		this.cores = cores;
		this.stars = stars;
		this.method = method;
	}

	public void step() {
		switch (method) {
		case BASIC_VERLET:
			for (Star star : stars) {
				updatePos((BasicStar) star);
			}
			for (Core core : cores) {
				updatePos((BasicCore) core);
			}
			break;
		case VELOCITY_VERLET:
			for (Star star : stars) {
				updatePos((VelocityStar) star);
			}
			for (Core core : cores) {
				updatePos((VelocityCore) core);
			}
			break;
		default:
			throw new RuntimeException("Disallowed computation method");
		}
	}

	private void updatePos(VelocityObject point) {
		// Velocity Verlet'
		Vector3f v05 = new Vector3f();
		v05.scale(dt / 2, acc(point));
		v05.add(point.getVelocity());
		// v(t+dt/2) = v + a*dt/2

		Vector3f newPos2 = new Vector3f();
		newPos2.scale(dt, v05);
		newPos2.add(point.getPosition());
		point.setPosition(newPos2);
		// x(t+dt) = x + v05*dt

		Vector3f newVel = new Vector3f();
		newVel.scale(dt / 2, acc(point));
		newVel.add(v05);
		point.setVelocity(newVel);
		// v(t+dt) = v05 + a(t+dt)*dt/2
	}

	private void updatePos(BasicObject point) {
		// Basic Verlet':
		Vector3f newPos = new Vector3f();
		newPos.scale(2, point.getPosition());
		newPos.sub(point.getOldPosition());
		Vector3f a = acc(point);
		a.scale(dt * dt);
		newPos.add(a);
		// newPos = 2*pos - oldPos + acc(pos)*dt^2
		point.updatePosition(newPos);

	}

	private Vector3f acc(BaseObject point) {
		Vector3f a = new Vector3f();
		for (Core core : cores) {
			if (core == point)
				continue;
			Vector3f relPos = new Vector3f();
			// r.12
			relPos.sub(((Drawable) core).getPosition(), point.getPosition());

			// m * r12 / len(r12)^3
			relPos.scale((float) (Core.mass / Math.pow(relPos.length(), 3)));

			// a +=
			a.add(relPos);
		}
		return a;
	}
}
