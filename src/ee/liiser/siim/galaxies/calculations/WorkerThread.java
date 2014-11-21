package ee.liiser.siim.galaxies.calculations;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.data.Core;
import ee.liiser.siim.galaxies.drawing.Drawable;

public class WorkerThread extends Thread{
	
	Drawable[] stars;
	Core[] cores;
	int min;
	int max;
	Object notifyObject;
	public static float dt = 0.001f;
	
	public WorkerThread(Drawable[] stars, Core[] cores, int min, int max, Object notifyObject) {
		this.stars = stars;
		this.cores = cores;
		this.min = min;
		this.max = max;
		this.notifyObject = notifyObject;
	}
	
	@Override
	public void run() {
		for(; min <max; min++){
			updatePos(stars[min]);
		}
	}

	
	void updatePos(Drawable point) {

//		// Basic Verlet':
//		 Vector3f newPos = new Vector3f();
//		 newPos.scale(2, point.getPosition());
//		 newPos.sub(point.getOldPosition());
//		 Vector3f a = acc(point);
//		 a.scale(dt * dt);
//		 newPos.add(a);
//		 // newPos = 2*pos - oldPos + acc(pos)*dt^2
//		 point.updatePosition(newPos);

		 //Velocity Verlet'
		 Vector3f v05 = new Vector3f();
		 v05.scale(dt/2, acc(point));
		 v05.add(point.getVelocity());
		 //v(t+dt/2) = v + a*dt/2
		
		 Vector3f newPos = new Vector3f();
		 newPos.scale(dt, v05);
		 newPos.add(point.getPosition());
		 point.updatePosition(newPos);
		 //x(t+dt) = x + v05*dt
		
		 Vector3f newVel = new Vector3f();
		 newVel.scale(dt/2, acc(point));
		 newVel.add(v05);
		 point.updateVel(newVel);
		 //v(t+dt) = v05 + a(t+dt)*dt/2

	}

	private Vector3f acc(Drawable point) {
		Vector3f a = new Vector3f();
		for (Core core : cores) {
			if (core == point)
				continue;
			Vector3f relPos = new Vector3f();
			// r.12
			relPos.sub(core.getPosition(), point.getPosition());

			// m * r12 / len(r12)^3
			relPos.scale((float) (core.getMass() / Math.pow(relPos.length(), 3)));

			// a +=
			a.add(relPos);
		}
		return a;
	}
}
