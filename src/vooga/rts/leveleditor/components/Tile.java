package vooga.rts.leveleditor.components;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import vooga.rts.resourcemanager.ResourceManager;

public class Tile extends MapComponent {
    
    private static final String BUNDLE_NAME = "TileIndex";
    
    public Tile() {
        super(BUNDLE_NAME);
    }
    
    public Tile(int index) {
        this();
        setType(index);
    }
    
    public Tile(int ID, String name, String imageName) {
        super(ID,name,imageName);
    }
    
    //test
    public Tile(int ID, String name, BufferedImage image) {
        super(ID,name,"");
        myImage = image;
    }
    
    public void refreshImage() throws IOException {
        if(myResource.containsKey(myID+"")) {
            String content = myResource.getString(myID+"");
            String[] buffer = content.split("&");
            myName = buffer[0];
            myImageName = buffer[1];
            //myImage = ResourceManager.getInstance().<BufferedImage>getFile(myImageName, BufferedImage.class);            
            myImage = ImageIO.read(new File(System.getProperty("user.dir")+"./src/vooga/rts/leveleditor/resource/"+myImageName));
        }
    }
    
    public void setType(int id) {
        super.setID(id);
        try {
            refreshImage();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
   
    
}
