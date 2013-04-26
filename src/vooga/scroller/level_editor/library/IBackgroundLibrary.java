package vooga.scroller.level_editor.library;

import java.awt.Image;
import java.util.Map;
import javax.swing.Icon;
import vooga.scroller.util.IBackgroundView;

public interface IBackgroundLibrary {

    public Map<Integer, IBackgroundView> getBackgrounds();
}
