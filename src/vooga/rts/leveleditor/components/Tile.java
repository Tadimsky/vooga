package vooga.rts.leveleditor.components;


import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tile extends MapComponent {
    
    private static final String BUNDLE_NAME = "TileIndex";
    
    public Tile() {
        super(BUNDLE_NAME);
    }
    
    public Tile(int index) {
        this();
        setType(index);
    }
    
    public void refreshImage() throws IOException {
        if(myResource.containsKey(myID+"")) {
            String content = myResource.getString(myID+"");
            String[] buffer = content.split("&");
            myName = buffer[0];
            myImageName = buffer[1];
            myImage = ImageIO.read(new File(System.getProperty("user.dir") + IMAGE_PATH + myImageName));            
        }
    }
    
    public void setType(int id) {
        super.setType(id);
        try {
            refreshImage();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
