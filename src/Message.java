import java.util.Random;

public class Message {
    /**
     * Prints the position, heading, and open paths available to the player.
     */
    public static void position() {
        System.out.println(Map.getMinimap());
    }
    /**
     * Prints a list of commands available to the player.
     */
    public static void help() {
        System.out.println("Navigation:" +
                "\n(forward | f)\n\tMove forward in the direction that you are currently facing\n" +
                "\n(left | l)\n\tTurn 90 degrees left\n" +
                "\n(right | r)\n\tTurn 90 degrees right\n" +
                "\n(backward | b)\n\tTurn 180 degrees to face the opposite direction\n" +
                "\n\nCharacter:" +
                "\n(status | inventory | inv)\n\tCheck your current health, stats and inventory\n" +
                "\n(position | pos | minimap | map)\n\tCheck your current position and orientation on the map\n" +
                "\n\nOther:" +
                "\n(tips | tip)\n\tDisplay a useful tip about the game\n" +
                "\n(help | h | ?)\n\tBring up the help menu\n" +
                "\n(exit)\n\tExit the game and kill off your character");
    }
    /**
     * Prints a useful tip at random.
     */
    public static void tip() {
        String tip;
        Random rand = new Random();
        int tipNum = rand.nextInt(10);
        switch(tipNum) {
        case 0:
            tip = "TIP: Return home for heals";
            break;
        case 1:
            tip = "TIP: Monsters will not be encountered unless moving forward, this includes bumping into walls like a retard";
            break;
        case 2:
            tip = "TIP: There is a treasure chest hidden in every area, you will need all the loot you can get for the last boss";
            break;
        case 3:
            tip = "TIP: Level 5 is the max level";
            break;
        case 4:
            tip = "TIP: Running from a battle opens you up to a 10% chance of dying instantly";
            break;
        case 5:
            tip = "TIP: Any items that you find are equipped automatically";
            break;
        case 6:
            tip = "TIP: Dying isn't the end anymore! You just lose all experience gained for your current level! Great!";
            break;
        case 7:
            tip = "TIP: You can run in the middle of battle by typing run and pressing Enter";
            break;
        case 8:
            tip = "TIP: A certain teleporter will return you straight home for healing";
            break;
		case 9:
			tip = "TIP: There is a home base located in the final area somewhere";
			break;
        default:
            tip = "TIP: Something broke, you got the best tip. Typing in 'I am Bradley Johnson' gives you god mode";
            break;
        }
        System.out.println(tip);
    }
	/**
	 * Prints a minimap of the locations that the player has explored
	 */
	public static void minimap() {
		System.out.println(Map.getMinimap());
	}
}
