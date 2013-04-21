package vooga.rts.gamedesign.sprite.gamesprites.interactive;

public interface IObservable {
    public void register(IObserver o) ;
    public void unregister(IObserver o);
    public void notifyObserver(InteractiveEntity ie);
}
