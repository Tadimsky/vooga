package vooga.rts.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vooga.rts.resourcemanager.ResourceManager;


/**
 * This class handles with the animation of images either through a sprite sheet or some other file format
 * 
 * @author Junho oh
 *
 */
public class Animation {
    private Pixmap mySheet;
    private String myFileName;
    public Animation (String fileName) {
        myFileName = fileName;
        //loadFile(myFileName);
    }
    public String getFileName() {
    	return myFileName;
    }
    private void loadFile(String fileName) {
        mySheet = new Pixmap(ResourceManager.getInstance()
                            .<BufferedImage> getFile("fileName", BufferedImage.class));
        //mySheet.pa
    }
    public void listFilesForFolder(File folder) {
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }
    public void sortByName(File[] files) {
    	List<ArrayList<String>> toReturn = new ArrayList<ArrayList<String>>(); 
    	for(int i=0;i<files.length-1;i++) {
    		String poseName = files[i].getName().substring(0, 8);
    		ArrayList<String> pose = new ArrayList<String>();
    		while(poseName.equals(files[i+1].getName().substring(0, 8)) && i<files.length) {
    			pose.add(files[i].getName());
    			i++;
    		}
    		toReturn.add(pose);
    	}
    }
    public static void main (String args []) {
    	Animation a = new Animation("animation/crocy");
    	
    	File directory = new File(System.getProperty("user.dir") + "\\bin\\vooga\\rts\\resources\\animation\\crocy\\");
    	File[] files = directory.listFiles();
    	
    	//System.out.println(files);
    	//a.listFilesForFolder(b);
    }
}
