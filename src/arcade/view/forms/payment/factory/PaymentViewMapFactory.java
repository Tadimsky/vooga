package arcade.view.forms.payment.factory;

import java.io.Reader;
import java.util.ResourceBundle;

import arcade.controller.Controller;
import arcade.exceptions.UndefinedPaymentException;
import arcade.games.GameInfo;

/**
 * The purpose of this class is to build a map of payment option strings to
 * constructors for the specific PaymentView.
 * 
 * The casting to Constructor<?> is required in order to reuse the PrototypeMapFactory
 * developed we developed in SLogo.
 * 
 * @author Ellango
 *
 * @param <V> 
 */
public class PaymentViewMapFactory<V> extends PrototypeMapFactory<V> {

    public PaymentViewMapFactory (ResourceBundle resources, String indexFile) {
        super(resources, indexFile);
    }

    public PaymentViewMapFactory (ResourceBundle resources, Reader reader) {
        super(resources, reader);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected V getValue (String className) {
        try {
            Class<?> paymentViewClass = Class.forName(className);
            return (V) paymentViewClass.getConstructor(Controller.class, ResourceBundle.class,
                                                       GameInfo.class);
        }
        catch (ClassNotFoundException e) {
            throw new UndefinedPaymentException();
        }
        catch (NoSuchMethodException e) {
            throw new UndefinedPaymentException();
        }
        catch (SecurityException e) {
            throw new UndefinedPaymentException();
        }
    }

}
