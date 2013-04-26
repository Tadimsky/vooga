package arcade.view.forms.payment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import arcade.controller.Controller;
import arcade.exceptions.UndefinedPaymentException;
import arcade.games.GameInfo;
import arcade.view.TextKeywords;
import arcade.view.forms.Form;
import arcade.view.forms.payment.factory.PaymentViewMapFactory;

/**
 * A view for choosing which payment method to use for buying a game.
 * Choosing an option will create the appropriate PaymentView to fill in
 * the payment information.
 * 
 * To add additional options, write the classpath of the PaymentView to create
 * in the arcade/resources/PaymentOptions file, and in the ResourceBundle(s), write
 * the locale-specific translation for that payment option for the keyword of the
 * PaymentView class name.
 * 
 * @author Ellango
 *
 */
@SuppressWarnings({"serial", "unused"})
public class PaymentSelection extends Form {
    private static final String FILE_PATH = "/src/arcade/resources/PaymentOptions";
    private GameInfo myGame;
    private Map<String, Constructor<?>> myPaymentOptions;
    
    /**
     * Constructs the payment selection view with a Controller, ResourceBundle, and 
     * the game about to be bought.
     * @param controller
     * @param resources
     */
    public PaymentSelection (Controller controller, ResourceBundle resources, GameInfo game) {
        super(controller, resources);
        myGame = game;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Creates the instruction to the user to select the preferred payment
     * method.
     */
    private JComponent createPaymentInstruction() {
        return createInstruction(TextKeywords.PAYMENT_INSTRUCTION);
    }

    /**
     * Creates the options to select the payment method.
     * 
     * @return
     */
    private JComponent createPaymentOptions () {
        myPaymentOptions = initializePaymentOptions();
        JPanel panel = new JPanel();
        for (final String paymentOption : myPaymentOptions.keySet()) {
            JRadioButton option = new JRadioButton(paymentOption);
            option.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed (ActionEvent arg0) {
                        try {
                            Constructor<?> constructor = myPaymentOptions.get(paymentOption);
                            constructor.newInstance(getController(), getResources(), myGame);
                            dispose();
                        }
                        catch (InstantiationException e) {
                            throw new UndefinedPaymentException();
                        }
                        catch (IllegalAccessException e) {
                            throw new UndefinedPaymentException();
                        }
                        catch (IllegalArgumentException e) {
                            throw new UndefinedPaymentException();
                        }
                        catch (InvocationTargetException e) {
                            throw new UndefinedPaymentException();
                        }
                }
            });
            option.setOpaque(false);
            panel.add(option);
        }
        return panel;
    }
    
    /**
     * Creates the map of payment options to the payment View constructor to be
     * called if that payment option chosen.  These options are stored in the
     * file /src/arcade/resources/PaymentOptions
     * 
     * @return
     */
    private Map<String, Constructor<?>> initializePaymentOptions() {
        PaymentViewMapFactory<Constructor<?>> factory = new PaymentViewMapFactory<Constructor<?>>(getResources(), FILE_PATH);
        return factory.buildStringMap();
    }

}
