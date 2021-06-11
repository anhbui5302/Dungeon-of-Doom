import java.io.IOException;

/**
 * Contains the behaviours of a generic Player entity
 */
public abstract class Player {
    /**
     * Ask the Player entity what it wants to do on the next turn.
     *
     * @throws IOException: A generic IOException from reading input from console.
     * @return : A command in the form of a string to be processed by GameLogic.
     */
    public abstract String getNextAction() throws IOException;

    /**
     * Used to send a result to the Player entity for its own use.
     *
     * @param result: The result sent from GameLogic.
     */
    public abstract void getResult(String result);

    /**
     * Used to send a 5x5 array, showing the vision of the Player entity for its own use.
     *
     * @param vision: The 5x5 grid area around the Player sent from GameLogic.
     */
    public abstract void getVision(char[][] vision);

    /**
     * Retrieve the coordinates of the Player entity for GameLogic to use.
     *
     * @return : The coordinates of the Player entity.
     */
    public abstract int[] getCoords();

    /**
     * Set the coordinates of the Player entity to new values.
     * @param newCoords The new coordinates of the Player entity sent from GameLogic.
     */
    public abstract void setCoords(int[] newCoords);
}