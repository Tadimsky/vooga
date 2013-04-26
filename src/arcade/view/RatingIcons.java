package arcade.view;

import java.io.File;
import java.net.MalformedURLException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 * A helper class to create graphical representations of ratings from integer
 * inputs.
 * 
 */
public class RatingIcons {

    private static final String IMAGES_LOCATION = "src/arcade/resources/images/";
    private static final String IMAGES_NAME = "Stars.gif";
    private ImageIcon[] myRatingIcons;

    /**
     * Initializes the rating icons.
     */
    public RatingIcons () {
        myRatingIcons = new ImageIcon[6];
        for (int i = 0; i <= 5; i++) {
            myRatingIcons[i] = new ImageIcon(MainView.IMAGES_DIRECTORY + i + IMAGES_NAME);
        }
    }

    /**
     * Makes a JLabel containing an icon for the provided rating.
     * 
     * @param rating
     * @return
     */
    public JLabel makeLabel (int rating) {
        return new JLabel(myRatingIcons[rating]);
    }

    /**
     * Makes HTML that can be used to create an image for the provided rating.
     * Useful for displaying the icon within a JEditorPane.
     * 
     * @param rating
     * @return
     */
    public String makeHTMLText (int rating) {
        File file = new File(IMAGES_LOCATION + rating + IMAGES_NAME);
        try {
            @SuppressWarnings("deprecation")
            String URL = file.toURL().toExternalForm();
            return "<html><img src =\"" + URL + "\"/>";
        }
        catch (MalformedURLException e) {
            return makeHTMLText(0);
        }
    }

    // /**
    // * Testing code.
    // * @param args
    // */
    // public static void main (String[] args) {
    // JFrame jf = new JFrame();
    // JEditorPane jep = new JEditorPane();
    // jep.setContentType("text/html");
    // StringBuilder sb = new StringBuilder();
    // for (int i = 0; i <= 5; i++) {
    // sb.append(new RatingIcons().makeHTMLText(i));
    // }
    // jep.setText(sb.toString());
    // jf.getContentPane().add(jep);
    // jf.setVisible(true);
    // }
}
