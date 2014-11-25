package ee.liiser.siim.galaxies.drawing;

import javax.vecmath.Vector3f;

public interface Drawable {
	
	public Vector3f getPosition();
	
	public void setPosition(Vector3f position);

	public float getSize();
}
