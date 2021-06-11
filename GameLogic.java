import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * Contains the main logic part of the game, as it processes.
 */
public class GameLogic {
	//Objects for the map, player and bot
	private Map map;
	private HumanPlayer humanPlayer;
	private BotPlayer botPlayer;

    // Keep tracks of how much gold the player has
	private int goldOwned;

    // Keep tracks of whether the game is running
    private boolean gameRunning = false;

	/**
	 * Default constructor
     *
     * @throws Exception: Exceptions thrown by chooseMap().
	 */
	public GameLogic() throws Exception {
        humanPlayer = new HumanPlayer();
        botPlayer = new BotPlayer();
        chooseMap();
	}

    /**
     * Create the default map.
     */
    protected void createDefaultMap(){
        map = new Map();
        System.out.println("Default map \"" + map.getMapName()+ "\" has been chosen!");
    }

    /**
     * Create a map using fileName sent from chooseMap().
     *
     * @param fileName: The name of the map file.
     * @throws Exception: Exceptions thrown by Map().
     */
    protected void createMap(String fileName) throws Exception {
        map = new Map(fileName);
        System.out.println("Map \"" + map.getMapName()+ "\" has been chosen!");
    }

    /**
     * Have the player choose the map that they want to play and creates the map.
     *
     * @throws Exception: Exceptions thrown by humanPlayer.getInputFromConsole() and CreateMap().
     */
    protected void chooseMap() throws Exception {
        boolean mapChosen = false;
        while (!mapChosen){
            // Prompt the player to choose a custom map or the default map
            System.out.println("Type 1 to play your own map or anything else to play the default map");
            String input = humanPlayer.getInputFromConsole();
            if (input.equals("1")) {
                System.out.println("Type the file name of the map you want to play:");
                String fileName = humanPlayer.getInputFromConsole();
                // Try to create the map from fileName input
                try{
                    // Add ".txt" to the end if fileName does not have that already
                    if (!fileName.endsWith(".txt")){
                        createMap(fileName + ".txt");
                    }
                    else{
                        createMap(fileName);
                    }
                    mapChosen = true;
                }
                // Catch Exception when map cannot be created and print error message
                catch(Exception e){
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                    System.err.println("Uh oh! Something went wrong while trying to create the map. Please check" +
                            " the format of your map file and check whether you have typed the correct name.");
                }
            }
            // Create the default map
            else {
                createDefaultMap();
                mapChosen = true;
            }
        }
    }

    /**
	 * Checks if the game is running.
	 * Not used.
     * @return : True if the game is running, false if the game is not running
     */
    protected boolean gameRunning() {
        return gameRunning;
    }

    /**
     * Checks if the player has the same coordinates as the bot.
     * Ends the game if that is the case.
     *
     * @throws IOException: Any IOException that may occur when closing the BufferedReader.
     */
    protected void endGameIfKilled() throws IOException {
        if (Arrays.equals(humanPlayer.getCoords(), botPlayer.getCoords())) {
            gameRunning = false;
            humanPlayer.closeBufferedReader();
            System.out.println("LOSE! You were killed by the terrifying Bot of Doom");
        }
    }

    /**
     * Initialise variables when the game starts and run the game until it ends.
     *
     * @throws IOException: IOExceptions thrown by humanPlayer.getNextAction();
     */
    protected void startGame() throws IOException {
        // Initialise variables
        goldOwned = 0;
        gameRunning = true;

        // Spawn the players
        botPlayer.setCoords(getValidCoords());
        humanPlayer.setCoords(getValidCoords());

        System.out.println("You are now entering: " + map.getMapName());
        // Keep running the game and alternate turns between two players until terminal state reached
        String input;
        while (gameRunning) {
            input = humanPlayer.getNextAction();
            switch (input) {
                case "HELLO":
                    humanPlayer.getResult(hello());
                    break;
                case "GOLD":
                    humanPlayer.getResult(gold());
                    break;
                case "MOVE N":
                    humanPlayer.getResult(move(0,-1, humanPlayer));
                    break;
                case "MOVE E":
                    humanPlayer.getResult(move(1,0, humanPlayer));
                    break;
                case "MOVE S":
                    humanPlayer.getResult(move(0,1, humanPlayer));
                    break;
                case "MOVE W":
                    humanPlayer.getResult(move(-1,0, humanPlayer));
                    break;
                case "PICKUP":
                    humanPlayer.getResult(pickup());
                    break;
                case "LOOK":
                    humanPlayer.getVision(look(humanPlayer));
                    break;
                case "QUIT":
                    humanPlayer.getResult(quitGame());
                    break;
            }
            endGameIfKilled();
            input = botPlayer.getNextAction();
            switch (input) {
                case "MOVE N":
                    botPlayer.getResult(move(0, -1, botPlayer));
                    break;
                case "MOVE E":
                    botPlayer.getResult(move(1, 0, botPlayer));
                    break;
                case "MOVE S":
                    botPlayer.getResult(move(0, 1, botPlayer));
                    break;
                case "MOVE W":
                    botPlayer.getResult(move(-1, 0, botPlayer));
                    break;
                case "LOOK":
                    botPlayer.getVision(look(botPlayer));
                    break;
            }
            endGameIfKilled();
        }
    }

    /**
     * Get valid coordinates in the map to spawn the Player at.
     *
     * @return : The coordinates of the Player to be spawned.
     */

    protected int[] getValidCoords(){
        int[] coords = new int[2];
        Random rand = new Random();
        boolean isValidCoords = false;
        char[][] spawnMap = map.getMap();
        // Keep running until valid coordinates are found
        while (!isValidCoords){
            // Get a random Y and X values for the coordinates respectively
            coords[0] = rand.nextInt(spawnMap.length);
            coords[1] = rand.nextInt(spawnMap[0].length);
            // Coordinates is valid if the character at that coordinates is '.' or 'G' and
            // coordinates are not the coordinates of the bot
            if ((map.getItemAtCoords(coords) == '.' || map.getItemAtCoords(coords) == 'G') &&
                    (!(Arrays.equals(botPlayer.getCoords(),coords)))) {
                isValidCoords = true;
            }
        }
        return coords;
    }
    /**
	 * Returns the gold required to win.
	 *
     * @return : Gold required to win.
     */
    protected String hello() {
        return "Gold to win: " + (map.getGoldRequired());
    }
	
	/**
	 * Returns the gold currently owned by the player.
	 *
     * @return : Gold currently owned.
     */
    protected String gold() {
        return "Gold owned: " + goldOwned;
    }

    /**
     * Checks if movement is legal and updates player's location on the map.
     *
     * @param xChange : The change in the X coordinate of the Player were the Player to move.
     * @param yChange : The change in the Y coordinate of the Player were the Player to move.
     * @param player: The player to move.
     * @return : Protocol if success or not.
     */
    protected String move(int xChange, int yChange, Player player){
        char[][] moveMap = map.getMap();
        int[] newCoords = new int[2];
        boolean moveSuccess = false;
        // If the target location is not a wall, set the player's location to that location
        try{
            if (!(moveMap[player.getCoords()[0] + yChange][player.getCoords()[1] + xChange] == '#')) {
                moveSuccess = true;
                newCoords[0] = player.getCoords()[0] + yChange;
                newCoords[1] = player.getCoords()[1] + xChange;
                player.setCoords(newCoords);
            }
        // If the player tries to move outside the map
        }
        catch(ArrayIndexOutOfBoundsException ignored){
        }
        if (moveSuccess){
            return "Success";
        }
        else{
            return "Fail";
        }
    }

    /**
     * Look at a 5x5 area around the Player.
     *
     * @param player: The player that is looking.
     * @return : A 5x5 array of characters with the Player at the center.
     */
    protected char[][] look(Player player) {
        char[][] vision = new char[5][5];
        char[][] lookMap = map.getMap();
        //Look through the 5x5 vision grid
        for (int i = 0; i < vision.length; i++){
            for(int j = 0; j < vision.length; j++){
                try {
                    // If any coordinates in the vision is the same as the coordinates of the human player
                    if ((player.getCoords()[0] - 2 + i == humanPlayer.getCoords()[0]) &&
                        (player.getCoords()[1] - 2 + j == humanPlayer.getCoords()[1])){
                        vision[i][j] = 'P';
                    }
                    // If any coordinates in the vision is the same as the coordinates of the bot player
                    else if ((player.getCoords()[0] - 2 + i == botPlayer.getCoords()[0]) &&
                             (player.getCoords()[1] - 2 + j == botPlayer.getCoords()[1])){
                        vision[i][j] = 'B';
                    }
                    else{
                        // Coordinates of the vision range from each coordinate of the player - 2 to + 2
                        // Set characters at coordinates in the 5x5 grid vision to be the same as characters at
                        // coordinates in the map relative to the looking Player's coordinates
                        vision[i][j] = lookMap[player.getCoords()[0] - 2 + i][player.getCoords()[1] - 2 + j];
                    }
                }
                // Fill region outside the edge of the map with '#'
                catch(ArrayIndexOutOfBoundsException e){
                    vision[i][j] = '#';
                }
            }
        }
        return vision;
    }

    /**
     * Processes the player's pickup command, updating the map and the player's gold amount.
     *
     * @return : If the player successfully picked-up gold or not.
     */
    protected String pickup() {
        if (map.getItemAtCoords(humanPlayer.getCoords()) == 'G'){
            goldOwned++;
            map.removeItemAtCoords(humanPlayer.getCoords());
            return "Success. Gold owned: " + goldOwned;
        }
        else{
            return "Fail. Gold owned: " + goldOwned;
        }
    }

    /**
     * Quits the game, shutting down the application.
     * Print a winning message if the player satisfies conditions to win the game or a losing message otherwise.
     *
     * @throws IOException: Any IOException that may occur when closing the BufferedReader.
     * @return : If the player won the game or not.
     */
    protected String quitGame() throws IOException {
        if ((map.getItemAtCoords(humanPlayer.getCoords()) == 'E') && (goldOwned == map.getGoldRequired())){
            gameRunning = false;
            humanPlayer.closeBufferedReader();
            return "WIN! You successfully escaped the dungeon with the amount of gold required.";
        }
        else{
            gameRunning = false;
            humanPlayer.closeBufferedReader();
            return "LOSE! You gave up half way, succumbing to your fate in the dungeon.";
        }
    }

    /**
     * Main method of the game.
     *
     * @throws Exception: Exceptions thrown by GameLogic().
     */
	public static void main(String[] args) throws Exception {
		GameLogic logic = new GameLogic();
		logic.startGame();
    }
}