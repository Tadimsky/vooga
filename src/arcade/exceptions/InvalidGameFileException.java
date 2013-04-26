package arcade.exceptions;

import arcade.view.TextKeywords;

/**
 * This exception is thrown if the user fails to select the file that extends
 * the Arcade's Game class when publishing a new game.
 * 
 * @author Ellango
 *
 */
@SuppressWarnings("serial")
public class InvalidGameFileException extends Exception {
    @Override
    public String getLocalizedMessage () {
        return TextKeywords.GAME_FILE_ERROR;
    }
}
