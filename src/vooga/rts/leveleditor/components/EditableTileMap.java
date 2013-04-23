package vooga.rts.leveleditor.components;

import java.awt.Dimension;
import vooga.rts.map.TileMap;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;

public class EditableTileMap extends TileMap {

    public EditableTileMap (Dimension tileSize, int width, int height) {
        super(tileSize, width, height);
        initialize();
    }

    public void initialize() {
        int x = this.getMyHeight();
        int y = this.getMyWidth();
        for (int i = 0 ; i < x ; i++) {
            for(int j = 0 ; j < y ; j++) {
                setTile(i,j,new EditableTile(i,j,getMyTileSize()));
            }
        }
    }    
    
    public void addTile(int i, int j, int id, String name , String imageName, Pixmap image) {
        setTile(i,j,new EditableTile(image,i,j,getMyTileSize(),id,name,imageName,false));
    }
    
    public void removeTile(int i, int j) {
        this.setTile(i, j, new EditableTile(i,j,getMyTileSize()));
    }
    
    public void removeTile(Location3D center) {
        int i = (int)(center.getY()/getMyTileSize().getHeight());
        int j = (int)(center.getX()/getMyTileSize().getWidth());
        removeTile(i,j);
    }
    
    public void removeAllTiles() {
        initialize();
    }
    
    @Override
    public EditableTile getTile(int i , int j) {
        return this.getTile(i, j);    
    }
    
}
