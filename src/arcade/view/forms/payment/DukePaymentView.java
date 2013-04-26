package arcade.view.forms.payment;

import java.util.ResourceBundle;
import javax.swing.JComponent;
import javax.swing.JTextField;

import arcade.controller.Controller;
import arcade.exceptions.InvalidPaymentException;
import arcade.games.GameInfo;
import arcade.view.TextKeywords;


/**
 * The view where a user can enter his/her payment information to buy a game, 
 * tailored for the DukePaymentManager.
 * 
 * @author Ellango
 * 
 */
@SuppressWarnings({"serial", "unused"})
public class DukePaymentView extends PaymentView {
    private static final String PAYMENT_MANAGER_CLASS = "DukePaymentManager";
    private JTextField myNameTextField;
    private JTextField myDukeCardTextField;

    /**
     * Constructs the DukePaymentView with a Controller, ResourceBundle, 
     * and a GameInfo for the game to be bought.
     * 
     * @param controller
     * @param resources
     * @param info
     */
    public DukePaymentView (Controller controller, ResourceBundle resources, GameInfo info) {
       super(controller, resources, info, PAYMENT_MANAGER_CLASS);
    }
    
    /**
     * Create an instruction for the user for how to pay with Duke card.
     * @return
     */
    private JComponent createDukeInstruction() {
        return createInstruction(TextKeywords.DUKE_CARD_INSTRUCTION);
    }
    
    /**
     * Creates the field where the user enters his/her full name.
     * @return
     */
    private JComponent createNameField() {
        myNameTextField = new JTextField();
        return createTextPanel(TextKeywords.FULLNAME, myNameTextField);
    }
    
    /**
     * Creates the field where the user enters his/her Duke Card number.
     */
    private JComponent createDukeCardField() {
        myDukeCardTextField = new JTextField();
        return createTextPanel(TextKeywords.DUKE_CARD_NUMBER, myDukeCardTextField);
    }

    @Override
    protected String[] getPaymentInfo () {
        String[] paymentInfo = {getGame().getPrice() + "",
                                myNameTextField.getText(),
                                myDukeCardTextField.getText()};
        return paymentInfo;
    }

}
