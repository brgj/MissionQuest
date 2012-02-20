import java.util.Random;

public class Map {
    /**
     * The maximum coordinate in any direction.
     */
    private static final int MAX_VAL = 20;
    private static int currX = 0;
    private static int currY = 0;
    private static String compass = "EAST";
    private static int[][] map = new int[MAX_VAL + 1][MAX_VAL + 1];
    private static char[][] minimap = new char[MAX_VAL + 1][MAX_VAL + 1];
    private static Random rand = new Random();
    /**               LEVEL 1                         LEVEL 2
     *      0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20
     * 0  { H, 0, 0, 0, 0, 0, 0, 0, 0, 0, W, 0, 0, 0, 0, 0, W, 0, 0, 0, T } 0
     * 1  { 0, W, W, W, W, W, 0, W, W, 0, W, 0, W, 0, W, 0, W, 0, W, W, 0 } 1
     * 2  { 0, W, 0, 0, $, W, 0, W, W, 0, W, 0, W, 0, W, 0, 0, 0, W, W, 0 } 2
     * 3  { 0, W, 0, W, W, W, 0, W, W, 0, W, 0, W, 0, W, W, W, W, W, W, 0 } 3
     * 4  { 0, W, 0, W, 0, W, 0, W, W, 0, W, 0, W, 0, 0, 0, 0, 0, 0, W, 0 } 4
     * 5  { 0, W, 0, W, 0, W, 0, W, W, 0, W, 0, W, W, W, W, 0, W, W, W, 0 } 5
     * 6  { 0, W, 0, W, 0, W, 0, 0, 0, 0, W, 0, 0, 0, 0, 0, 0, W, 0, 0, 0 } 6
     * 7  { 0, W, 0, W, 0, W, 0, W, W, 0, W, 0, W, W, W, W, 0, 0, 0, W, W } 7
     * 8  { 0, W, 0, W, 0, W, 0, W, W, 0, W, 0, W, W, W, W, 0, W, 0, 0, 0 } 8
     * 9  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, D, 0, 0, 0, 0, 0, 0, W, W, W, $ } 9
     * 10 { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W } 10
     * 11 { T, 0, 0, 0, 0, 0, 0, 0, 0, 0, W, $, 0, W, ?, ?, ?, ?, W, 0, 0 } 11
     * 12 { W, W, 0, W, W, W, 0, W, W, W, W, 0, 0, W, ?, ?, ?, ?, W, 0, 0 } 12
     * 13 { $, W, 0, 0, 0, W, 0, 0, 0, 0, W, 0, 0, W, ?, ?, ?, ?, W, 0, 0 } 13
     * 14 { 0, W, W, W, 0, W, 0, W, W, 0, W, 0, 0, W, W, B, B, W, W, 0, 0 } 14
     * 15 { 0, 0, 0, 0, 0, W, 0, W, W, 0, W, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } 15
     * 16 { W, W, W, W, W, W, 0, W, W, 0, W, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } 16
     * 17 { W, W, 0, 0, 0, 0, 0, 0, W, 0, W, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } 17
     * 18 { 0, 0, 0, W, W, W, W, 0, W, K, W, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } 18
     * 19 { 0, W, 0, W, W, W, W, W, W, W, W, W, W, W, W, 0, 0, W, W, W, W } 19
     * 20 { 0, W, 0, 0, 0, 0, 0, 0, 0, 0, L, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } 20
     *      0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20
     *               LEVEL 3                          LEVEL 4
     * Put a key somewhere in level 3 for the level 4 door and a boss in level 4
     * if(x < 10 && y < 10) Level1 = true;
     * if(x > 10 && y < 10) Level2 = true;
     * if(x < 10 && y > 10) Level3 = true;
     * if(x > 10 && y > 10) Level4 = true;
	 * 
	 * Random map generation:
	 * Everything starts as 0
	 * row10 = -1
	 * row[10] = -1
	 * Maybe not...
	 * Everything starts as -1
	 * Make paths that lead to the exit
	 * Algorithm: 
	 * loop 10 times in random directions
	 * start paths at exit and start
	 * paths loop until at least 1 path has been made from current position
	 * if new position = 0 set new marker at the location(Maybe use ArrayList)
	 * 
	 * row[10] has a 2 randomly placed between row 0 and 9 inclusive
	 * row[10] has a -2 randomly placed between row 11 and 20 inclusive
	 * -1 cannot be touching a 2 or -2 if not on the supporting wall
	 * 
	 * row0[0] = 1
	 * 
     */
    public static void drawMap() {
        int[] row0 = { 1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row1 = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row2 = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row3 = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row4 = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row5 = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row6 = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row7 = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row8 = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row9 = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row10= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row11= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row12= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row13= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row14= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row15= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row16= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row17= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row18= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row19= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int[] row20= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
        /*
         *-2 is locked door
         *-1 is wall
         * 0 is open
         * 1 is home
         * 2 is unlocked door	 
         * 3 is treasure
         * 4 is teleporter
         * 5 is key
		 * 6 is dead body
         * 7 is boss door
         * 8 is boss area
         */
        map[0] = row0;
        map[1] = row1;
        map[2] = row2;
        map[3] = row3;
        map[4] = row4;
        map[5] = row5;
        map[6] = row6;
        map[7] = row7;
        map[8] = row8;
        map[9] = row9;
        map[10] = row10;
        map[11] = row11;
        map[12] = row12;
        map[13] = row13;
        map[14] = row14;
        map[15] = row15;
        map[16] = row16;
        map[17] = row17;
        map[18] = row18;
        map[19] = row19;
        map[20] = row20;
        //Setting a random door y position
        int path2y = rand.nextInt(10);
        //The dividing wall x position
        int path2x = 10;
        //Start position
        int path1x = 0;
        int path1y = 0;
        //Set next room start position to first room end position
        int path3y = path2y;
        //Putting door at end point
        map[path2y][path2x--] = 2;
        //Placing an open space in front of door
        map[path2y][path2x] = 0;
        //Variable to hold random number to check for open spot
        int rNext;
        //Looping to generate open tiles
        for(int i = 0; i < 10; i++) {
			boolean open = false;
			do {
				rNext = rand.nextInt(4);
				switch(rNext) {
					case 0:
						if(path2x < 9)
							path2x++;
						break;
					case 1:
						if(path2y < 9)
							path2y++;
						break;
					case 2:
						if(path2x > 0)
							path2x--;
						break;
					default:
						if(path2y > 0)
							path2y--;
						break;
				}
				if(map[path2y][path2x] != 0)
					open = true;
			} while(open != true);
				open = false;
			do {
				rNext = rand.nextInt(4);
				switch(rNext) {
					case 0:
						if(path1x < 9)
							path1x++;
						break;
					case 1:
						if(path1y < 9)
							path1y++;
						break;
					case 2:
						if(path1x > 0)
							path1x--;
						break;
					default:
						if(path1y > 0)
							path1y--;
						break;
					}
					if(map[path1y][path1x] != 0)
						open = true;
				} while(open != true);
				map[path1y][path1x] = 0;
			} //TODO Fix this so the paths connect
			while(path1y != path2y || path1x != path2x) {
				System.out.println("HIHIHIHIHIHI");
				if(path1y < path2y) {
					path1y++;
					System.out.println("y++");
				}
				else if(path1y > path2y) {
					path1y--;
					System.out.println("y--");
				}
				else if(path1x < path2x) {
					path1x++;
					System.out.println("x++");
				}
				else if(path1x > path2x) {
					path1x--;
					System.out.println("x--");
				}
				map[path1y][path1x] = 0;
			}

        //Initializing the minimap
        for (int i = 0; i < 21; i++) {
            for(int j = 0; j < 21; j++) {
                minimap[i][j] = '?';
            }
        }
        //Setting the initial player position
        minimap[0][0] = '>';
    }
    /**
     * Changes the value of one tile such as a door or chest after its contents should have changed
     * @param x the x value of the tile being changed
     * @param y the y value of the tile being changed
     * @param newVal the new value for the location
     */
    public static void setTile(int x, int y, int newVal) {
        map[y][x] = newVal;
    }
    /**
     * Changes the value of all tiles of a specific id, used for unlocking doors
     * @param oldVal the old value of the tile
     * @param newVal the new value of the tile
     */
    public static void setTiles(int oldVal, int newVal) {
        for(int i = 0; i <= MAX_VAL; i++)
            for(int j = 0; j <= MAX_VAL; j++)
                if(map[i][j] == oldVal)
                    map[i][j] = newVal;
    }
    /**
     * Teleports the player when they are on a teleporter block.
     * Where they go depends on which teleporter is used.
     */
    public static void teleport() {
        if(map[currY][currX] == 4) {
            minimap[currY][currX] = 'T';
            if(currY < 10) {
                currY = 11;
                currX = 0;
            } else {
                currY = 0;
                currX = 0;
            }
            minimap[currY][currX] = '>';
            setHeading("EAST");
        }
    }
	/**
	 * Teleports the player back home, called when player dies
	 */
	public static void goHome() {
		setTiles(6, 0);
		getPaths();
		map[currY][currX] = 6;
		minimap[currY][currX] = 'X';
		currY = 0;
		currX = 0;
		minimap[currY][currX] = '>';
		setHeading("EAST");
	}
	
    /**
     * @return the direction that the player is currently facing
     */
    public static String getHeading() {
        return compass;
    }
    /**
     * @param newHeading updates the direction that the player is currently facing.
     * Only called by navigate, made private to avoid external conflict.
     */
    private static void setHeading(String newHeading) {
        compass = newHeading;
    }
    /**
     *@return the currently open paths for the player to use and updates the minimap while checking
     */
    public static String getPaths() {
        String response = "\n***NAV OPTIONS***";
        if(getHeading().equals("EAST")) {
            if(currX != MAX_VAL)
                if(map[currY][currX + 1] >= 0) {
                    response += "\n*    FORWARD    *";
                    if(map[currY][currX + 1] == 0)
                        minimap[currY][currX + 1] = ' ';
                    else if(map[currY][currX + 1] == 1)
                        minimap[currY][currX + 1] = 'H';
                    else if(map[currY][currX + 1] == 2)
                        minimap[currY][currX + 1] = 'D';
                    else if(map[currY][currX + 1] == 3)
                        minimap[currY][currX + 1] = '$';
                    else if(map[currY][currX + 1] == 4)
                        minimap[currY][currX + 1] = 'T';
                    else if(map[currY][currX + 1] == 5)
                        minimap[currY][currX + 1] = 'K';
                    else if(map[currY][currX + 1] == 6)
                        minimap[currY][currX + 1] = 'X';
                    else if(map[currY][currX + 1] == 7)
                        minimap[currY][currX + 1] = 'B';
                    else
                        minimap[currY][currX + 1] = '?';
                } else
                    if(map[currY][currX + 1] == -2)
                        minimap[currY][currX + 1] = 'L';
                    else if(map[currY][currX + 1] == -1)
                        minimap[currY][currX + 1] = 'W';
                    else
                        minimap[currY][currX + 1] = '?';
            if(currY != MAX_VAL)
                if(map[currY + 1][currX] >= 0) {
                    response += "\n*    RIGHT      *";
                    if(map[currY + 1][currX] == 0)
                        minimap[currY + 1][currX] = ' ';
                    else if(map[currY + 1][currX] == 1)
                        minimap[currY + 1][currX] = 'H';
                    else if(map[currY + 1][currX] == 2)
                        minimap[currY + 1][currX] = 'D';
                    else if(map[currY + 1][currX] == 3)
                        minimap[currY + 1][currX] = '$';
                    else if(map[currY + 1][currX] == 4)
                        minimap[currY + 1][currX] = 'T';
                    else if(map[currY + 1][currX] == 5)
                        minimap[currY + 1][currX] = 'K';
                    else if(map[currY + 1][currX] == 6)
                        minimap[currY + 1][currX] = 'X';
                    else if(map[currY + 1][currX] == 7)
                        minimap[currY + 1][currX] = 'B';
                    else
                        minimap[currY + 1][currX] = '?';
                } else
                    if(map[currY + 1][currX] == -2)
                        minimap[currY + 1][currX] = 'L';
                    else if(map[currY + 1][currX] == -1)
                        minimap[currY + 1][currX] = 'W';
                    else
                        minimap[currY + 1][currX] = '?';
            if(currY != 0) 
                if(map[currY - 1][currX] >= 0) {
                    response += "\n*    LEFT       *";
                    if(map[currY - 1][currX] == 0)
                        minimap[currY - 1][currX] = ' ';
                    else if(map[currY - 1][currX] == 1)
                        minimap[currY - 1][currX] = 'H';
                    else if(map[currY - 1][currX] == 2)
                        minimap[currY - 1][currX] = 'D';
                    else if(map[currY - 1][currX] == 3)
                        minimap[currY - 1][currX] = '$';
                    else if(map[currY - 1][currX] == 4)
                        minimap[currY - 1][currX] = 'T';
                    else if(map[currY - 1][currX] == 5)
                        minimap[currY - 1][currX] = 'K';
                    else if(map[currY - 1][currX] == 6)
                        minimap[currY - 1][currX] = 'X';
                    else if(map[currY - 1][currX] == 7)
                        minimap[currY - 1][currX] = 'B';
                    else
                        minimap[currY - 1][currX] = '?';
                } else
                    if(map[currY - 1][currX] == -2)
                        minimap[currY - 1][currX] = 'L';
                    else if(map[currY - 1][currX] == -1)
                        minimap[currY - 1][currX] = 'W';
                    else
                        minimap[currY - 1][currX] = '?';
            if(currX != 0)
                if(map[currY][currX - 1] >= 0) {
                    response += "\n*    BACKWARD   *";
                    if(map[currY][currX - 1] == 0)
                        minimap[currY][currX - 1] = ' ';
                    else if(map[currY][currX - 1] == 1)
                        minimap[currY][currX - 1] = 'H';
                    else if(map[currY][currX - 1] == 2)
                        minimap[currY][currX - 1] = 'D';
                    else if(map[currY][currX - 1] == 3)
                        minimap[currY][currX - 1] = '$';
                    else if(map[currY][currX - 1] == 4)
                        minimap[currY][currX - 1] = 'T';
                    else if(map[currY][currX - 1] == 5)
                        minimap[currY][currX - 1] = 'K';
                    else if(map[currY][currX - 1] == 6)
                        minimap[currY][currX - 1] = 'X';
                    else if(map[currY][currX - 1] == 7)
                        minimap[currY][currX - 1] = 'B';
                    else
                        minimap[currY][currX - 1] = '?';
                } else
                    if(map[currY][currX - 1] == -2)
                        minimap[currY][currX - 1] = 'L';
                    else if(map[currY][currX - 1] == -1)
                        minimap[currY][currX - 1] = 'W';
                    else
                        minimap[currY][currX - 1] = '?';
        } else if(getHeading().equals("SOUTH")) {
            if(currY != MAX_VAL)
                if(map[currY + 1][currX] >= 0) {
                    response += "\n*    FORWARD    *";
                    if(map[currY + 1][currX] == 0)
                        minimap[currY + 1][currX] = ' ';
                    else if(map[currY + 1][currX] == 1)
                        minimap[currY + 1][currX] = 'H';
                    else if(map[currY + 1][currX] == 2)
                        minimap[currY + 1][currX] = 'D';
                    else if(map[currY + 1][currX] == 3)
                        minimap[currY + 1][currX] = '$';
                    else if(map[currY + 1][currX] == 4)
                        minimap[currY + 1][currX] = 'T';
                    else if(map[currY + 1][currX] == 5)
                        minimap[currY + 1][currX] = 'K';
                    else if(map[currY + 1][currX] == 6)
                        minimap[currY + 1][currX] = 'X';
                    else if(map[currY + 1][currX] == 7)
                        minimap[currY + 1][currX] = 'B';
                    else
                        minimap[currY + 1][currX] = '?';
                } else
                    if(map[currY + 1][currX] == -2)
                        minimap[currY + 1][currX] = 'L';
                    else if(map[currY + 1][currX] == -1)
                        minimap[currY + 1][currX] = 'W';
                    else
                        minimap[currY + 1][currX] = '?';
            if(currX != 0)
                if(map[currY][currX - 1] >= 0) {
                    response += "\n*    RIGHT      *";
                    if(map[currY][currX - 1] == 0)
                        minimap[currY][currX - 1] = ' ';
                    else if(map[currY][currX - 1] == 1)
                        minimap[currY][currX - 1] = 'H';
                    else if(map[currY][currX - 1] == 2)
                        minimap[currY][currX - 1] = 'D';
                    else if(map[currY][currX - 1] == 3)
                        minimap[currY][currX - 1] = '$';
                    else if(map[currY][currX - 1] == 4)
                        minimap[currY][currX - 1] = 'T';
                    else if(map[currY][currX - 1] == 5)
                        minimap[currY][currX - 1] = 'K';
                    else if(map[currY][currX - 1] == 6)
                        minimap[currY][currX - 1] = 'X';
                    else if(map[currY][currX - 1] == 7)
                        minimap[currY][currX - 1] = 'B';
                    else
                        minimap[currY][currX - 1] = '?';
                } else
                    if(map[currY][currX - 1] == -2)
                        minimap[currY][currX - 1] = 'L';
                    else if(map[currY][currX - 1] == -1)
                        minimap[currY][currX - 1] = 'W';
                    else
                        minimap[currY][currX - 1] = '?';
            if(currX != MAX_VAL)
                if(map[currY][currX + 1] >= 0) {
                    response += "\n*    LEFT       *";
                    if(map[currY][currX + 1] == 0)
                        minimap[currY][currX + 1] = ' ';
                    else if(map[currY][currX + 1] == 1)
                        minimap[currY][currX + 1] = 'H';
                    else if(map[currY][currX + 1] == 2)
                        minimap[currY][currX + 1] = 'D';
                    else if(map[currY][currX + 1] == 3)
                        minimap[currY][currX + 1] = '$';
                    else if(map[currY][currX + 1] == 4)
                        minimap[currY][currX + 1] = 'T';
                    else if(map[currY][currX + 1] == 5)
                        minimap[currY][currX + 1] = 'K';
                    else if(map[currY][currX + 1] == 6)
                        minimap[currY][currX + 1] = 'X';
                    else if(map[currY][currX + 1] == 7)
                        minimap[currY][currX + 1] = 'B';
                    else
                        minimap[currY][currX + 1] = '?';
                } else
                    if(map[currY][currX + 1] == -2)
                        minimap[currY][currX + 1] = 'L';
                    else if(map[currY][currX + 1] == -1)
                        minimap[currY][currX + 1] = 'W';
                    else
                        minimap[currY][currX + 1] = '?';
            if(currY != 0)
                if(map[currY - 1][currX] >= 0) {
                    response += "\n*    BACKWARD   *";
                    if(map[currY - 1][currX] == 0)
                        minimap[currY - 1][currX] = ' ';
                    else if(map[currY - 1][currX] == 1)
                        minimap[currY - 1][currX] = 'H';
                    else if(map[currY - 1][currX] == 2)
                        minimap[currY - 1][currX] = 'D';
                    else if(map[currY - 1][currX] == 3)
                        minimap[currY - 1][currX] = '$';
                    else if(map[currY - 1][currX] == 4)
                        minimap[currY - 1][currX] = 'T';
                    else if(map[currY - 1][currX] == 5)
                        minimap[currY - 1][currX] = 'K';
                    else if(map[currY - 1][currX] == 6)
                        minimap[currY - 1][currX] = 'X';
                    else if(map[currY - 1][currX] == 7)
                        minimap[currY - 1][currX] = 'B';
                    else
                        minimap[currY - 1][currX] = '?';
                } else
                    if(map[currY - 1][currX] == -2)
                        minimap[currY - 1][currX] = 'L';
                    else if(map[currY - 1][currX] == -1)
                        minimap[currY - 1][currX] = 'W';
                    else
                        minimap[currY - 1][currX] = '?';
        } else if(getHeading().equals("WEST")) {
            if(currX != 0)
                if(map[currY][currX - 1] >= 0) {
                    response += "\n*    FORWARD    *";
                    if(map[currY][currX - 1] == 0)
                        minimap[currY][currX - 1] = ' ';
                    else if(map[currY][currX - 1] == 1)
                        minimap[currY][currX - 1] = 'H';
                    else if(map[currY][currX - 1] == 2)
                        minimap[currY][currX - 1] = 'D';
                    else if(map[currY][currX - 1] == 3)
                        minimap[currY][currX - 1] = '$';
                    else if(map[currY][currX - 1] == 4)
                        minimap[currY][currX - 1] = 'T';
                    else if(map[currY][currX - 1] == 5)
                        minimap[currY][currX - 1] = 'K';
                    else if(map[currY][currX - 1] == 6)
                        minimap[currY][currX - 1] = 'X';
                    else if(map[currY][currX - 1] == 7)
                        minimap[currY][currX - 1] = 'B';
                    else
                        minimap[currY][currX - 1] = '?';
                } else
                    if(map[currY][currX - 1] == -2)
                        minimap[currY][currX - 1] = 'L';
                    else if(map[currY][currX - 1] == -1)
                        minimap[currY][currX - 1] = 'W';
                    else
                        minimap[currY][currX - 1] = '?';
            if(currY != 0)
                if(map[currY - 1][currX] >= 0) {
                    response += "\n*    RIGHT      *";
                    if(map[currY - 1][currX] == 0)
                        minimap[currY - 1][currX] = ' ';
                    else if(map[currY - 1][currX] == 1)
                        minimap[currY - 1][currX] = 'H';
                    else if(map[currY - 1][currX] == 2)
                        minimap[currY - 1][currX] = 'D';
                    else if(map[currY - 1][currX] == 3)
                        minimap[currY - 1][currX] = '$';
                    else if(map[currY - 1][currX] == 4)
                        minimap[currY - 1][currX] = 'T';
                    else if(map[currY - 1][currX] == 5)
                        minimap[currY - 1][currX] = 'K';
                    else if(map[currY - 1][currX] == 6)
                        minimap[currY - 1][currX] = 'X';
                    else if(map[currY - 1][currX] == 7)
                        minimap[currY - 1][currX] = 'B';
                    else
                        minimap[currY - 1][currX] = '?';
                } else
                    if(map[currY - 1][currX] == -2)
                        minimap[currY - 1][currX] = 'L';
                    else if(map[currY - 1][currX] == -1)
                        minimap[currY - 1][currX] = 'W';
                    else
                        minimap[currY - 1][currX] = '?';
            if(currY != MAX_VAL)
                if(map[currY + 1][currX] >= 0) {
                    response += "\n*    LEFT       *";
                    if(map[currY + 1][currX] == 0)
                        minimap[currY + 1][currX] = ' ';
                    else if(map[currY + 1][currX] == 1)
                        minimap[currY + 1][currX] = 'H';
                    else if(map[currY + 1][currX] == 2)
                        minimap[currY + 1][currX] = 'D';
                    else if(map[currY + 1][currX] == 3)
                        minimap[currY + 1][currX] = '$';
                    else if(map[currY + 1][currX] == 4)
                        minimap[currY + 1][currX] = 'T';
                    else if(map[currY + 1][currX] == 5)
                        minimap[currY + 1][currX] = 'K';
                    else if(map[currY + 1][currX] == 6)
                        minimap[currY + 1][currX] = 'X';
                    else if(map[currY + 1][currX] == 7)
                        minimap[currY + 1][currX] = 'B';
                    else
                        minimap[currY + 1][currX] = '?';
                } else
                    if(map[currY + 1][currX] == -2)
                        minimap[currY + 1][currX] = 'L';
                    else if(map[currY + 1][currX] == -1)
                        minimap[currY + 1][currX] = 'W';
                    else
                        minimap[currY + 1][currX] = '?';
            if(currX != MAX_VAL)
                if(map[currY][currX + 1] >= 0) { 
                    response += "\n*    BACKWARD   *";
                    if(map[currY][currX + 1] == 0)
                        minimap[currY][currX + 1] = ' ';
                    else if(map[currY][currX + 1] == 1)
                        minimap[currY][currX + 1] = 'H';
                    else if(map[currY][currX + 1] == 2)
                        minimap[currY][currX + 1] = 'D';
                    else if(map[currY][currX + 1] == 3)
                        minimap[currY][currX + 1] = '$';
                    else if(map[currY][currX + 1] == 4)
                        minimap[currY][currX + 1] = 'T';
                    else if(map[currY][currX + 1] == 5)
                        minimap[currY][currX + 1] = 'K';
                    else if(map[currY][currX + 1] == 6)
                        minimap[currY][currX + 1] = 'X';
                    else if(map[currY][currX + 1] == 7)
                        minimap[currY][currX + 1] = 'B';
                    else
                        minimap[currY][currX + 1] = '?';
                } else
                    if(map[currY][currX + 1] == -2)
                        minimap[currY][currX + 1] = 'L';
                    else if(map[currY][currX + 1] == -1)
                        minimap[currY][currX + 1] = 'W';
                    else
                        minimap[currY][currX + 1] = '?';
        } else {
            if(currY != 0)
                if(map[currY - 1][currX] >= 0) {
                    response += "\n*    FORWARD    *";
                    if(map[currY - 1][currX] == 0)
                        minimap[currY - 1][currX] = ' ';
                    else if(map[currY - 1][currX] == 1)
                        minimap[currY - 1][currX] = 'H';
                    else if(map[currY - 1][currX] == 2)
                        minimap[currY - 1][currX] = 'D';
                    else if(map[currY - 1][currX] == 3)
                        minimap[currY - 1][currX] = '$';
                    else if(map[currY - 1][currX] == 4)
                        minimap[currY - 1][currX] = 'T';
                    else if(map[currY - 1][currX] == 5)
                        minimap[currY - 1][currX] = 'K';
                    else if(map[currY - 1][currX] == 6)
                        minimap[currY - 1][currX] = 'X';
                    else if(map[currY - 1][currX] == 7)
                        minimap[currY - 1][currX] = 'B';
                    else
                        minimap[currY - 1][currX] = '?';
                } else
                    if(map[currY - 1][currX] == -2)
                        minimap[currY - 1][currX] = 'L';
                    else if(map[currY - 1][currX] == -1)
                        minimap[currY - 1][currX] = 'W';
                    else
                        minimap[currY - 1][currX] = '?';
            if(currX != MAX_VAL)
                if(map[currY][currX + 1] >= 0) {
                    response += "\n*    RIGHT      *";
                    if(map[currY][currX + 1] == 0)
                        minimap[currY][currX + 1] = ' ';
                    else if(map[currY][currX + 1] == 1)
                        minimap[currY][currX + 1] = 'H';
                    else if(map[currY][currX + 1] == 2)
                        minimap[currY][currX + 1] = 'D';
                    else if(map[currY][currX + 1] == 3)
                        minimap[currY][currX + 1] = '$';
                    else if(map[currY][currX + 1] == 4)
                        minimap[currY][currX + 1] = 'T';
                    else if(map[currY][currX + 1] == 5)
                        minimap[currY][currX + 1] = 'K';
                    else if(map[currY][currX + 1] == 6)
                        minimap[currY][currX + 1] = 'X';
                    else if(map[currY][currX + 1] == 7)
                        minimap[currY][currX + 1] = 'B';
                    else
                        minimap[currY][currX + 1] = '?';
                } else
                    if(map[currY][currX + 1] == -2)
                        minimap[currY][currX + 1] = 'L';
                    else if(map[currY][currX + 1] == -1)
                        minimap[currY][currX + 1] = 'W';
                    else
                        minimap[currY][currX + 1] = '?';
            if(currX != 0)
                if(map[currY][currX - 1] >= 0) {
                    response += "\n*    LEFT       *";
                    if(map[currY][currX - 1] == 0)
                        minimap[currY][currX - 1] = ' ';
                    else if(map[currY][currX - 1] == 1)
                        minimap[currY][currX - 1] = 'H';
                    else if(map[currY][currX - 1] == 2)
                        minimap[currY][currX - 1] = 'D';
                    else if(map[currY][currX - 1] == 3)
                        minimap[currY][currX - 1] = '$';
                    else if(map[currY][currX - 1] == 4)
                        minimap[currY][currX - 1] = 'T';
                    else if(map[currY][currX - 1] == 5)
                        minimap[currY][currX - 1] = 'K';
                    else if(map[currY][currX - 1] == 6)
                        minimap[currY][currX - 1] = 'X';
                    else if(map[currY][currX - 1] == 7)
                        minimap[currY][currX - 1] = 'B';
                    else
                        minimap[currY][currX - 1] = '?';
                } else
                    if(map[currY][currX - 1] == -2)
                        minimap[currY][currX - 1] = 'L';
                    else if(map[currY][currX - 1] == -1)
                        minimap[currY][currX - 1] = 'W';
                    else
                        minimap[currY][currX - 1] = '?';
            if(currY != MAX_VAL)
                if(map[currY + 1][currX] >= 0) {
                    response += "\n*    BACKWARD   *";
                    if(map[currY + 1][currX] == 0)
                        minimap[currY + 1][currX] = ' ';
                    else if(map[currY + 1][currX] == 1)
                        minimap[currY + 1][currX] = 'H';
                    else if(map[currY + 1][currX] == 2)
                        minimap[currY + 1][currX] = 'D';
                    else if(map[currY + 1][currX] == 3)
                        minimap[currY + 1][currX] = '$';
                    else if(map[currY + 1][currX] == 4)
                        minimap[currY + 1][currX] = 'T';
                    else if(map[currY + 1][currX] == 5)
                        minimap[currY + 1][currX] = 'K';
                    else if(map[currY + 1][currX] == 6)
                        minimap[currY + 1][currX] = 'X';
                    else if(map[currY + 1][currX] == 7)
                        minimap[currY + 1][currX] = 'B';
                    else
                        minimap[currY + 1][currX] = '?';
                } else
                    if(map[currY + 1][currX] == -2)
                        minimap[currY + 1][currX] = 'L';
                    else if(map[currY + 1][currX] == -1)
                        minimap[currY + 1][currX] = 'W';
                    else
                        minimap[currY + 1][currX] = '?';
        }
        response += "\n*****************";
        return response;
    }
    /**Gets the current heading and checks to see if the next block is a wall or open.
     * Sets a new position or heading if direction is valid.
     * @param direction the user entered direction
     */
    public static void navigate(String direction) {
        if(getHeading().equals("EAST")) {
            if(direction.matches("(?i)(forward|f)")) {
                if(currX != MAX_VAL && map[currY][currX + 1] >= 0)
                    currX++;
                else
                    System.out.println("Ouch, hit a wall");
            } else if(direction.matches("(?i)(right|r)")) {
                setHeading("SOUTH");
            } else if(direction.matches("(?i)(left|l)")) {
                setHeading("NORTH");
            } else if(direction.matches("(?i)(backward|b)")) {
                setHeading("WEST");
            }
        } else if(getHeading().equals("SOUTH")) {
            if(direction.matches("(?i)(forward|f)")) {
                if(currY != MAX_VAL && map[currY + 1][currX] != -1)
                    currY++;
                else
                    System.out.println("Ouch, hit a wall");
            } else if(direction.matches("(?i)(right|r)")) {
                setHeading("WEST");
            } else if(direction.matches("(?i)(left|l)")) {
                setHeading("EAST");
            } else if(direction.matches("(?i)(backward|b)")) {
                setHeading("NORTH");
            }
        } else if(getHeading().equals("WEST")) {
            if(direction.matches("(?i)(forward|f)")) {
                if(currX != 0 && map[currY][currX - 1] >= 0)
                    currX--;
                else
                    System.out.println("Ouch, hit a wall");
            } else if(direction.matches("(?i)(right|r)")) {
                setHeading("NORTH");
            } else if(direction.matches("(?i)(left|l)")) {
                setHeading("SOUTH");
            } else if(direction.matches("(?i)(backward|b)")) {
                setHeading("EAST");
            }
        } else {
            if(direction.matches("(?i)(forward|f)")) {
                if(currY != 0 && map[currY - 1][currX] >= 0)
                    currY--;
                else
                    System.out.println("Ouch, hit a wall");
            } else if(direction.matches("(?i)(right|r)")) {
                setHeading("EAST");
            } else if(direction.matches("(?i)(left|l)")) {
                setHeading("WEST");
            } else if(direction.matches("(?i)(backward|b)")) {
                setHeading("SOUTH");
            }
        }
        if(getHeading().equals("EAST"))
            minimap[currY][currX] = '>';
        else if(getHeading().equals("SOUTH"))
            minimap[currY][currX] = 'v';
        else if(getHeading().equals("WEST"))
            minimap[currY][currX] = '<';
        else
            minimap[currY][currX] = '^';

        

        if(direction.matches("(?i)(forward|f)"))
            MissionQuest.tileEffect(map[currY][currX]);
        else {
            System.out.println("\n\n\n\n");
            Message.tip();
        }
    }
    /**
     * @return the current y position
     */
    public static int getY() {
        return currY;
    }
    /**
     * @return the current x position
     */
    public static int getX() {
        return currX;
    }
    /**
     * @return a string representation of the explored map
     */
    public static String getMinimap() {
        String paths = getPaths();
        String str = "----------------------N----------------------\n";
        for (int i = 0; i <= MAX_VAL; i++) {
            if(i == (int)(MAX_VAL / 2))
                str += "W ";
            else
                str += "| ";
            for(char val: minimap[i]) {
                str += val + " ";
            }
            if(i == (int)(MAX_VAL / 2))
                str += "E";
            else
                str += "|";
            if(i == 10)
                str += "     *LEGEND*";
            else if(i == 11)
                str += " '" + minimap[currY][currX] + "' = You(at " + currX + ", " + currY + ")";
			else if(i == 12)
				str += " 'X' = Your body";
            else if(i == 13)
                str += " 'H' = Home";
            else if(i == 14)
                str += " '?' = Unknown";
            else if(i == 15)
                str += " ' ' = Open Path";
            else if(i == 16)
                str += " 'W' = Wall";
            else if(i == 17)
                str += " 'D' = Door";
            else if(i == 18)
                str += " 'L' = Locked Door";
            else if(i == 19)
                str += " 'T' = Teleporter";
            else if(i == 20)
                str += " '$' = Treasure";
            str += "\n";
        }
        str += "----------------------S----------------------";
        str += paths;
        return str;
    }

}
