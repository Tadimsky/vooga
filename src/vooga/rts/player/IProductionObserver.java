package vooga.rts.player;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;

public interface IProductionObserver {
	public void addProduction(Unit newProduction);
}
