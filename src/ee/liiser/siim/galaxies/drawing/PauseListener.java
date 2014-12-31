package ee.liiser.siim.galaxies.drawing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

import ee.liiser.siim.galaxies.calculations.WorkerThread;

public class PauseListener implements KeyListener, ActionListener {

	private WorkerThread thread;
	private Timer stepTimer;
	private boolean shiftDown;
	private boolean controlDown;

	public PauseListener(WorkerThread workerThread) {
		this.thread = workerThread;
		stepTimer = new Timer(20, this);
		stepTimer.setInitialDelay(200);
	}
	
	public void setThread(WorkerThread thread){
		this.thread = thread;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case (KeyEvent.VK_SHIFT):
			shiftDown = true;
			break;
		case (KeyEvent.VK_CONTROL):
			controlDown = true;
			break;
		case (KeyEvent.VK_SPACE):
			// Only toggle on release
			break;
		case (KeyEvent.VK_RIGHT):
			stepTimer.start();
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case (KeyEvent.VK_SHIFT):
			shiftDown = false;
			break;
		case (KeyEvent.VK_CONTROL):
			controlDown = false;
			break;
		case (KeyEvent.VK_SPACE):
			if (thread != null)
				thread.togglePause();
			break;
		case (KeyEvent.VK_RIGHT):
			stepTimer.stop();
			step();
			break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		step();

	}

	private void step() {
		if (thread != null) {
			if (shiftDown) {
				thread.step(10);
			} else if (controlDown) {
				thread.step(100);
			} else {
				thread.step();
			}
		}
	}

}
