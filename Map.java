import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads and contains in memory the map of the game.
 *
 */
public class Map {

	/* Representation of the map */
	private char[][] map;
	
	/* Map name */
	private String mapName;
	
	/* Gold required for the human player to win */
	private int goldRequired;

	/**
	 * Default constructor, creates the default map "Very small Labyrinth of doom".
	 */
	public Map() {
		mapName = "Small Dungeon of Doom";
		goldRequired = 2;
		map = new char[][]{
				{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
				{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
				{'#','.','.','.','.','.','.','G','.','.','.','.','.','.','.','.','.','E','.','#'},
				{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
				{'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
				{'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
				{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
				{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
				{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
		};
	}
	
	/**
	 * Constructor that accepts a map to read in from.
	 *
	 * @param fileName: The name of the map file.
	 * @throws Exception: Exceptions thrown by readMap.
	 */
	public Map(String fileName) throws Exception {
		readMap(fileName);
	}

    /**
     * @return : Gold required to exit the current map.
     */
    protected int getGoldRequired() {
        return goldRequired;
    }

    /**
     * @return : The map as stored in memory.
     */
    protected char[][] getMap() {
        return map;
    }


    /**
     * @return : The name of the current map.
     */
    protected String getMapName() {
        return mapName;
    }

	/**
	 * See what character is currently stored in a coordinates in the map.
	 *
	 * @param Coords: The coordinates of the character of interest.
	 * @return : the character stored at that coordinates
	 */
	protected char getItemAtCoords(int[] Coords){
		return map[Coords[0]][Coords[1]];
	}

	/**
	 * Remove the character stored in a coordinates in the map, replacing it with '.'.
	 *
	 * @param Coords: The coordinates of the character of interest.
	 */
	protected void removeItemAtCoords(int[] Coords){
		map[Coords[0]][Coords[1]] = '.';
	}

    /**
     * Reads the map from file using BufferedReader.
     *
     * @param fileName: the name of the map file.
	 * @throws IOException: A generic IOException from reading a file.
	 * @throws Exception: An Exception thrown if the map is not rectangular.
     */
    protected void readMap(String fileName) throws IOException, Exception {
    	String validChars = "#.EG";
    	// Read file from FileReader using BufferedReader
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		// Read mapName and goldRequired from the first two lines in the file
		mapName = br.readLine().replace("name ", "");
		try{
			goldRequired = Integer.parseInt(br.readLine().replace("win ", ""));
		} // If goldRequired read from the map file is not an integer
		catch(NumberFormatException e){
			System.out.println("Non-integer detected for the amount of gold needed to win. The default value has been" +
					" set to 1. Please check the format of your map file.");
			goldRequired = 1;
		}
		// Determine the height of the map
		String line;
		int mapHeight = 0;
		while((line = br.readLine()) != null) {
			mapHeight++;
		}
		br.close();
		// Read the file again to get the map
		map = new char[mapHeight][];
		br = new BufferedReader(new FileReader(fileName));
		// Skip the first two lines
		for(int i = 1; i <= 2; i++) {
			br.readLine();
		}
		// Read map from the remaining lines
		for(int i = 0; i < mapHeight; i++){
			map[i] = br.readLine().toCharArray();
			// Check whether all lines read have the same length (the map is rectangular)
			if (map[i].length != map[0].length){
				throw new Exception("\nError! Your map is not rectangular.");
			}
		}
		br.close();
		// Convert illegal characters into walls
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				if (!validChars.contains(Character.toString(map[i][j]))){
					map[i][j] = '#';
				}
			}
		}
    }
}