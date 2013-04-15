package vooga.rts.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import vooga.rts.IGameLoop;
import vooga.rts.input.PositionObject;

public abstract class AbstractController extends Observable implements
		IGameLoop {

	public void onLeftMouseDown(PositionObject o) {
	};

	public void onLeftMouseUp(PositionObject o) {
	};

	public void onRightMouseDown(PositionObject o) {
	};

	public void onRightMouseUp(PositionObject o) {
	};

	public void onMouseDrag(PositionObject o) {
	};

	public abstract void activate();

	public abstract MainState getGameState();

	public void onMouseMove(PositionObject o) {
	}

}
