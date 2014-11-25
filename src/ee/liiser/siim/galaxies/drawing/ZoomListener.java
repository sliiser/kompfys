package ee.liiser.siim.galaxies.drawing;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class ZoomListener implements MouseListener, MouseWheelListener,
		MouseMotionListener {

	private SimpleUniverse universe;
	private TransformGroup viewTransform;
	private int buttonDown;
	private Point lastEvent;

	public ZoomListener(SimpleUniverse universe) {
		this.viewTransform = universe.getViewingPlatform()
				.getViewPlatformTransform();
		this.universe = universe;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		Transform3D trans = new Transform3D();
		viewTransform.getTransform(trans);

		Vector3d translation = new Vector3d();
		trans.get(translation);

		Canvas3D source = (Canvas3D) e.getSource();
		double z = translation.z;
		double x = -(e.getX() - source.getWidth() / 2.0) / source.getWidth();
		double y = (e.getY() - source.getHeight() / 2.0) / source.getHeight();

		double scale = z * universe.getViewer().getView().getFieldOfView();
		x *= scale;
		y *= scale;

		double dz = e.getPreciseWheelRotation();
		double dx = 0;
		double dy = 0;
		if (z != 0) {
			dx = x * dz / z;
			dy = y * dz / z;
		}

		Transform3D zoomAndPan = new Transform3D();
		zoomAndPan.setTranslation(new Vector3d(dx, dy, dz));
		trans.mul(zoomAndPan);
		viewTransform.setTransform(trans);

	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		Transform3D trans = new Transform3D();
		viewTransform.getTransform(trans);

		Canvas3D source = (Canvas3D) e.getSource();

		if (buttonDown == MouseEvent.BUTTON1) {
			// Pan

			Vector3d translation = new Vector3d();
			trans.get(translation);
			double scale = translation.z
					* universe.getViewer().getView().getFieldOfView();
			Vector3d newTranslation = new Vector3d(-scale
					* (e.getX() - lastEvent.x) / source.getWidth(), scale
					* (e.getY() - lastEvent.y) / source.getHeight(), 0);

			Transform3D pan = new Transform3D();
			pan.setTranslation(newTranslation);
			trans.mul(pan);

		} else if (buttonDown == MouseEvent.BUTTON2) {
			// Rotate camera around origin

			trans.invert();

			Transform3D rotX = new Transform3D();
			rotX.rotX(2 * Math.PI * (e.getY() - lastEvent.y)
					/ source.getWidth());
			Transform3D rotY = new Transform3D();
			rotY.rotY(2 * Math.PI * (e.getX() - lastEvent.x)
					/ source.getHeight());

			trans.mul(rotX);
			trans.mul(rotY);

			trans.invert();

		} else if (buttonDown == MouseEvent.BUTTON3) {
			// Rotate camera around itself

			double fov = universe.getViewer().getView().getFieldOfView();

			Transform3D rotX = new Transform3D();
			rotX.rotX(fov * (e.getY() - lastEvent.y) / source.getWidth());
			Transform3D rotY = new Transform3D();
			rotY.rotY(fov * (e.getX() - lastEvent.x) / source.getHeight());

			trans.mul(rotX);
			trans.mul(rotY);

		}

		viewTransform.setTransform(trans);

		lastEvent = e.getPoint();

	}

	@Override
	public void mousePressed(MouseEvent e) {
		buttonDown = e.getButton();
		lastEvent = e.getPoint();

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttonDown = MouseEvent.NOBUTTON;

	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// ignored

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// ignored

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// ignored

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// ignored
	}

}
