import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Runs the game with a human player and contains code needed to read inputs.
 */
public class HumanPlayer extends Player {
    // Store all the valid commands to be compared against
    private final String[] validCommands = new String[] {"HELLO", "GOLD", "MOVE N", "MOVE S", "MOVE E", "MOVE W",
                                                         "PICKUP", "LOOK", "QUIT"};

    // Coordinates of the human player
    private int[] coords = new int[2];

    //Initialise the BufferedReader
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    /**
     * Default constructor.
     */
    public HumanPlayer(){
    }

    /**
     * Reads player's input from the console.
     *
     * @throws IOException: A generic IOException from reading input from console.
     * @return : A string containing the input the player entered.
     */
    protected String getInputFromConsole() throws IOException {
        // Read commands from console using BufferedReader
        return br.readLine();
    }

    /**
     * Processes the command. It should return a reply in form of a String, as the protocol dictates.
     * Otherwise it should return the string "Invalid".
     *
     * @throws IOException: A generic IOException from reading input from console.
     * @return : Processed output or Invalid if the @param command is wrong.
     */
    public String getNextAction() throws IOException {
        while (true){
            String command = getInputFromConsole();
            //Remove leading and trailing spaces and convert all characters input into uppercase
            command = command.trim().toUpperCase();
            if (Arrays.asList(validCommands).contains(command)){
                return command;
            }
            else {
                System.out.println("Invalid");
            }
        }
    }

    /**
     * Close the BufferedReader
     *
     * @throws IOException: Any IOException that may occur when closing the BufferedReader.
     */
    public void closeBufferedReader() throws IOException {
        br.close();
    }

    /**
     * Print the result sent from GameLogic onto console.
     *
     * @param result: The result sent from GameLogic.
     */
    public void getResult(String result) {
        System.out.println(result);
    }

    /**
     * Print the 5x5 grid area around the Player for the Player to see.
     *
     * @param vision: The 5x5 grid area around the Player sent from GameLogic.
     */
    public void getVision(char[][] vision){
        for(int i = 0; i < vision.length; i++){
            for(int j = 0; j < vision[i].length; j++){
                System.out.print(vision[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Retrieve the coordinates of the human player for GameLogic to use.
     *
     * @return : The coordinates of the human player.
     */
    public int[] getCoords(){
        return coords;
    }

    /**
     * Set the coordinates of the human player to new values.
     * @param newCoords The new coordinates of the human player to be updated sent from GameLogic.
     */
    public void setCoords(int[] newCoords){
        coords[0] = newCoords[0];
        coords[1] = newCoords[1];
    }
}