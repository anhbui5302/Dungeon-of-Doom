# Dungeon-of-Doom
A simple game where the player has to navigate a user-made dungeon with limited vision to collect the treasure while avoiding the Doom Bot residing within. Developed as part of a university coursework in 2020


Welcome to the Dungeon of Doom!
This is a text-based game where you explore a dungeon, collect any gold that you may find and
escape before being found and killed by the terrifying Bot of Doom who is lurking in the darkness. 

INSTALLATION
	
1. Extract the .zip file.
2. Open Command Prompt.
3. Navigate to the folder that you extracted the .zip file to by typing "cd <path>" where <path>
   is the path of the folder.
4. Compile the code for the game by typing "javac GameLogic.java". You must have a jdk version 11
   or higher to be able to run the game.
5. Play the game by typing "java GameLogic".
	
HOW TO PLAY
	
Follow the instruction on the screen to either use your own map or use the default map. Once the 
game starts after the map has been loaded, you and the bot will be spawned in a random location 
in the map. You can now type a command into the Command Prompt to play.

The dungeon is made up and square tiles. You can only move in one of the four cardinal directions. 
You also only have a limited vision of the area around yourself and cannot see the entire map at
once. But whatever limitations you have also apply to the bot, so use it to your advantage!

When the bot sees you in its vision, it will attempt to move towards you, so don't be complacent! 
Once the bot is on the same tile as you are, it is game over!

The game runs in turns and every commands you enter consume your turn, whether it is successful or
not. So, manage your resources well because the bot does not have to pick up gold like you. It can
use this extra turn to move closer to you.

GOAL

Your goal in this game is to move around the dungeon and avoid the bot at all costs. Find and pick
up the gold required to escape the dungeon. Once you have picked up all the gold required, find an
exit tile "E" and escape the dungeon.

AVAILABLE COMMANDS
- "HELLO": Gets the amount of gold required to leave the dungeon.
- "GOLD": Gets the amount of gold you currently have.
- "MOVE <direction>": Move in a certain direction where <direction> is N, E, S, W representing
  each of the cardinal directions North, East, South and West respectively.
- "PICKUP": Attempts to pick up gold at where you are.
- "LOOK": Looks at a 5x5 map of the area around you.
- "QUIT": Quits the game. You will win the game if you satisfy all the requirements, otherwise you 
will lose.

Examples when you use "LOOK":
Example 1:
	
###..
	
#.#.E
	
..P..
	
#....
	
#..G.

Example 2:
	
#####
	
#####
	
..P##
	
...##
	
B..##

Map legend:
- "P": You.
- "B": The bot you are trying to avoid.
- "#": A wall that no one can move through.
- ".": An empty floor that anyone can move through.
- "G": A floor tile that contains gold.
- "E": A floor tile you can use to escape the dungeon.
 
CUSTOM MAPS

To create and play your own map, follow the following steps:
1. Create a blank .txt file in the same folder that you extracted the .zip file (the same folder 
   as the .java files).
2. In the first line of the text file, type "name " followed by the name of the dungeon.
3. In the second line of the text file, type "win " followed by the amount of gold needed to win.
4. In the following lines, use the following symbols "#", ".", "G", "E" to draw a rectangular
   dungeon of your choice.
Note:
- All characters that are not one of the four allowed above will be converted to "#".
- Make sure there is no empty/blank lines in the text file.
- The map must be rectangular or it will not be accepted by the game.
- There must be at least two empty floor tiles "." for the bot and the player to spawn.
- It is recommended to surround your dungeon with a layer of walls "#".

FOR DEVELOPERS

Please do check out my source code of the game for all classes and methods descriptions, which
are written in comments just above their implementations. I have learned alot during the creation
of this game and I hope that my source code can be useful for you.

If you found any bugs or unexpected errors, let me know by sending an email to the address below, 
including the descriptions of the bugs/errors and how to replicate them. Thank you!

CONTACT

You can reach me at lcab21@bath.ac.uk
