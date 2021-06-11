import java.util.Arrays;
import java.util.Random;

/**
 * Runs the game with a bot player and contains code needed for the bot to detect and chase after the human player.
 */
public class BotPlayer extends Player {
    // The coordinates of the bot
    private int[] coords = new int[2];

    // The 5x5 grid area around the bot the last time it looked
    private char[][] lastVisionLooked = new char[5][5];
    //Empty array for comparison
    private char[][] emptyArrayForComparison = new char[5][5];

    // Keep tracks of whether the human player is seen
    private boolean seenHuman = false;

    // The coordinates of the bot and where the human was last seen in the 5x5 grid
    private int[] coordsInVision = new int[2];
    private int[] humanCoordsInVision = new int[2];

    /**
     * Default constructor.
     */
    public BotPlayer(){
    }

    /**
     * Check whether the bot can move in a direction.
     *
     * @param xChange : The change in the X coordinate of the bot were the bot to move.
     * @param yChange : The change in the Y coordinate of the bot were the bot to move.
     * @return True if the bot can move in the target direction, false if not.
     */
    protected boolean canMove (int xChange, int yChange){
        boolean canBotMove = false;
        // If the target location is not a wall, the bot can move to that location
        try{
            if (!(lastVisionLooked[coordsInVision[0] + yChange][coordsInVision[1]+ xChange] == '#')){
                canBotMove = true;
            }
        } //If the bot tries to move outside the 5x5 grid
        catch (ArrayIndexOutOfBoundsException ignored){
        }
        return canBotMove;
    }

    /**
     * Move towards the last seen coordinates of the player on the 5x5 grid.
     *
     * @return The command for the action to be sent to getNextAction()
     */
    protected String moveTowardsPlayer(){
        // The default action is to look if the bot cannot move in the desired location
        String action = "LOOK";
        // If the last seen coordinates of the human player is to the left of the bot and it can move to the left
        if ((humanCoordsInVision[1] < coordsInVision[1]) && (canMove(-1, 0))){
            action = "MOVE W";
        }
        // If the last seen coordinates of the human player is to the right of the bot and it can move to the right
        if ((humanCoordsInVision[1] > coordsInVision[1]) && (canMove(1, 0))){
            action = "MOVE E";
        }
        // If the last seen coordinates of the human player is above the bot
        if (humanCoordsInVision[0] < coordsInVision[0]) {
            // If the bot can move up
            if (canMove(0, -1)) {
                action = "MOVE N";
            }
            // If the last seen coordinates of the human player is also to the left of the bot and it can move left
            if ((humanCoordsInVision[1] < coordsInVision[1]) && (canMove(-1, 0))){
                action = "MOVE W";
            }
            // If the last seen coordinates of the human player is also to the right of the bot and it can move right
            if ((humanCoordsInVision[1] > coordsInVision[1]) && (canMove(1, 0))){
                action = "MOVE E";
            }
        }
        // If the last seen coordinates of the human player is below the bot
        if (humanCoordsInVision[0] > coordsInVision[0]) {
            // If the bot can move down
            if (canMove(0, 1)) {
                action = "MOVE S";
            }
            // If the last seen coordinates of the human player is also to the left of the bot and it can move left
            if ((humanCoordsInVision[1] < coordsInVision[1]) && (canMove(-1, 0))){
                action = "MOVE W";
            }
            // If the last seen coordinates of the human player is also to the right of the bot and it can move right
            if ((humanCoordsInVision[1] > coordsInVision[1]) && (canMove(1, 0))){
                action = "MOVE E";
            }
        }
        return action;
    }
    /**
     * Determine which action the bot will randomly make.
     *
     * @return : The command for the action to be sent to getNextAction()
     */
    protected String getRandomAction(){
        Random rand = new Random();
        int randomInt;
        String action = "LOOK";
        // Generate a random integer between 0 and 1 (inclusive)
        randomInt = rand.nextInt(2);
        // Look if the result is 0 and move if the result is 1
        if (randomInt == 0){
            action = "LOOK";
        }
        else{
            // Generate a random integer between 0 and 3 (inclusive)
            randomInt = rand.nextInt(4);
            // Choose a direction to move based on which integer was generated
            // Move in that direction if the target location is not a wall
            switch(randomInt) {
                case 0:
                    if (canMove(0, -1)) {
                        action = "MOVE N";
                    }
                    break;
                case 1:
                    if (canMove(1, 0)) {
                        action = "MOVE E";
                    }
                    break;
                case 2:
                    if (canMove(0, 1)) {
                        action = "MOVE S";
                    }
                    break;
                case 3:
                    if (canMove(-1, 0)){
                        action = "MOVE W";
                    }
                    break;
            }
        }
    return action;
    }

    /**
     * Determine the next action the bot will make.
     *
     * @return : The string to be sent to GameLogic
     */
    public String getNextAction(){
        // Make a random action if the bot has not seen the human player on the 5x5 grid
        String action = getRandomAction();
        // Move towards the human player if the bot has seen it in the 5x5 grid
        if (seenHuman){
            action = moveTowardsPlayer();
        }
        // Always look if the 5x5 vision grid is empty i.e. at the start of the game
        if (Arrays.deepEquals(lastVisionLooked, emptyArrayForComparison)){
            action = "LOOK";
        }
        // Update bot coordinates after action is taken
        updateBotCoords(action);
        return action;
    }

    /**
     * Update the coordinates of the bot in the 5x5 grid.
     *
     * @param direction: the direction the bot headed in
     */
    protected void updateBotCoords(String direction){
        switch (direction){
            case "MOVE N":
                coordsInVision[0] = coordsInVision[0] - 1;
                break;
            case "MOVE E":
                coordsInVision[1] = coordsInVision[1] + 1;
                break;
            case "MOVE S":
                coordsInVision[0] = coordsInVision[0] + 1;
                break;
            case "MOVE W":
                coordsInVision[1] = coordsInVision[1] - 1;
                break;
        }
    }

    /**
     * Determine what to do based on result of the action. Not used.
     *
     * @param result: The result sent from GameLogic.
     */
    public void getResult(String result) {
    }

    /**
     * Store the 5x5 grid in an array, check for the human player's presence and reset the coordinates of the bot
     * in the grid.
     *
     * @param vision: The 5x5 grid area around the Player sent from GameLogic.
     */
    public void getVision(char[][] vision) {
        lastVisionLooked = vision;
        seenHuman = false;
        // Check the 5x5 grid to see if the human player is in it
        for (int i = 0; i < lastVisionLooked.length; i++){
            for (int j = 0; j < lastVisionLooked.length; j++){
                // Set seenHuman to true if the human player is in the 5x5 grid
                // and update the human player's coordinates in the 5x5 grid
                if (lastVisionLooked[i][j] == 'P'){
                    seenHuman = true;
                    humanCoordsInVision[0] = i;
                    humanCoordsInVision[1] = j;
                }
            }
        }
        // Set the coordinates of the bot in the 5x5 grid to the middle.
        coordsInVision[0] = 2;
        coordsInVision[1] = 2;
    }
    /**
     * Retrieve the coordinates of the bot player for GameLogic to use.
     *
     * @return : The coordinates of the bot player.
     */
    public int[] getCoords(){
        return coords;
    }

    /**
     * Set the coordinates of the bot player to new values.
     * @param newCoords The new coordinates of the bot player to be updated sent from GameLogic.
     */
    public void setCoords(int[] newCoords){
        coords[0] = newCoords[0];
        coords[1] = newCoords[1];
    }
}