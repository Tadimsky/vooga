package vooga.towerdefense.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import vooga.towerdefense.controller.Controller;

/**
 * This class represents a game loop. It is responsible for starting, stopping,
 * and resuming the animation of the game.
 * 
 * @author Erick Gonzalez
 */
public class GameLoop {
	// animate 25 times per second if possible
	private static final int FRAMES_PER_SECOND = 25;
	// better way to think about timed events (in milliseconds)
	private static final int ONE_SECOND = 1000;
	private static final int DEFAULT_DELAY = ONE_SECOND / FRAMES_PER_SECOND;

	private Controller myController;
	private Timer myTimer;

	/**
	 * 
	 * @param controller
	 *            a controller
	 */
	public GameLoop(Controller controller) {
		// TODO: functions to construct model from file. Probably put that in
		myController = controller;
		initTimer();
	}

	/**
	 * Starts the game loop.
	 */
	public void start() {
		myTimer.start();
	}

	public void stop() {
		myTimer.stop();
	}

	private void initTimer() {
		myTimer = new Timer(DEFAULT_DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myController.update(DEFAULT_DELAY);
				myController.displayMap();
			}
		});
	}

}
