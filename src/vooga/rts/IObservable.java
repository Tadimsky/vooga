package vooga.rts;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.player.IProductionObserver;

public interface IObservable {
	public void register(IProductionObserver o);
	public void unregister(IProductionObserver o);
	public void notifyProductionObserver(Unit u);
}
