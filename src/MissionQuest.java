import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class MissionQuest {
    static Random rand = new Random();
    static Scanner scan = new Scanner(System.in);
    static Timer time;

    static Character player;
    static Enemy enemy;

    public static void encounter(Enemy.Class c, boolean noEscape) {
        double pSMod; //Represents the player's speed modifier
        double eSMod; //Represents the enemy's speed modifier
        String response;
        time = new Timer();

        enemy = new Enemy(c);
        //prompts for user to run or fight

        System.out.println("A wild " + enemy.getClassType() + " has appeared!" 
                + "\nWill you fight or run?");

        do {
            response = scan.nextLine();
            if(response.equalsIgnoreCase("run") && noEscape)
                System.out.println("There's no escape!");
        } while(!response.equalsIgnoreCase("fight") && (!response.equalsIgnoreCase("run") || noEscape));

        //run is 90% chance to escape 10% chance to die
        if(response.equalsIgnoreCase("run") && !noEscape)
            runAway();
        else {
            pSMod = Math.pow(2, (1 + (2 *((double)player.getSpeed() / 10))));
            eSMod = Math.pow(2, (1 + (2 *((double)enemy.getSpeed() / 10))));
            System.out.println("Battle Begin");
            time.schedule(new BattlePhase(pSMod, eSMod), 0, 20);
            // Fix cheap hack that stops it from continuing until enter is pressed - DONE
            // Learn more about threads - DONE

            do {
                response = scan.nextLine();
                if(response.equalsIgnoreCase("run")) {
                    if(noEscape)
                        System.out.println("There's no escape!");
                    else {
                        enemy.hit(999);
                        runAway();
                    }
                }
            } while(!enemy.isDead() && !player.isDead());

            //Figure out what to do for combat - DONE
            //player.attack() should check for a hit and randomize damage - DONE
            //Should be called like enemy.hit(player.attack()) - DONE
            //If it misses, it returns 0 - DONE
            //Put a system.out.print inside attack saying if it hit or missed - DONE

        }

        //fight should loop a bunch of times without user input
        /*combat should work like this:
         * rogue 8spd vs ogre 2spd
         * rogueTurn = 15 - rogue.getSpeed();
         * ogreTurn = 15 - ogre.getSpeed();
         * while(rogue !dead && ogre !dead) {
         *     if(rogueTurn == 0) {
         *        rogue.attack();
         *        rogueTurn = 15 - rogue.getSpeed();
         *     }
         *     if(ogreTurn == 0) {
         *        ogre.attack();
         *        ogreTurn = 15 - ogre.getSpeed();
         *     }
         *     rogueTurn--;
         *     ogreTurn--;
         * }
         * This actually promotes exponential growth ridiculously, kinda imba
         * revise...
         */
    }

    public static void encounter() {
        encounter(null, false);
    }

    private static void runAway() {
        if(rand.nextInt(10) > 1)
            System.out.println("Got away safely!");
        else {
            player.hit(999);
            System.out.println("You died...");
        }
        time.cancel();
    }

    private static class BattlePhase extends TimerTask {
        private final int battleTimer = 320; //Represents the time it takes for one attack
        //Slowest attack speed is ~2.3, highest is 32
        //Time for each loop of BattlePhase is 20 ms
        //Longest time to attack is ~2.8 secs, shortest potential time is .2 secs
        private double pSwing;
        private double eSwing;
        private final double pSMod;
        private final double eSMod;

        private BattlePhase(double pSMod, double eSMod) {
            this.pSwing = pSMod;
            this.eSwing = eSMod;
            this.pSMod = pSMod;
            this.eSMod = eSMod;
        }
        @Override
        public void run() {
            if(pSwing >= eSwing) {
                if(pSwing >= battleTimer) {
                    enemy.hit(player.attack());
                    pSwing -= battleTimer;

                    if(enemy.isDead()) {
                        System.out.println("You killed the enemy " + enemy.getClassType() + "!");
                        System.out.println("You gained " + enemy.getExperience() + " exp points!");
                        player.addExp(enemy.getExperience());
                        System.out.print("Press Enter to continue...");
                        time.cancel();
                        return;
                    }
                }

                if(eSwing >= battleTimer) {
                    player.hit(enemy.attack());
                    eSwing -= battleTimer;

                    if(player.isDead()) {
                        System.out.println("You died...");
                        time.cancel();
                        return;
                    }
                }
            } else {

                if(eSwing >= battleTimer) {
                    player.hit(enemy.attack());
                    eSwing -= battleTimer;

                    if(player.isDead()) {
                        System.out.println("You died...");
                        time.cancel();
                        return;
                    }
                }

                if(pSwing >= battleTimer) {
                    enemy.hit(player.attack());
                    pSwing -= battleTimer;

                    if(enemy.isDead()) {
                        System.out.println("You killed the enemy " + enemy.getClassType() + "!");
                        System.out.println("You gained " + enemy.getExperience() + " exp points!");
                        player.addExp(enemy.getExperience());
                        System.out.print("Press Enter to continue...");
                        time.cancel();
                        return;
                    }
                }
            }
            pSwing += pSMod;
            eSwing += eSMod;
        }
    }

    public static void tileEffect(int tileNum) {
        //It knows what space it's on, it just won't do most things because it's retarded.. Figure this out.. - DONE
        String str;
        if(tileNum == 0) {
            if(rand.nextInt(3) == 0)
                encounter();
            System.out.println("\n\n\n\n");
            Message.tip();
        } else
            System.out.println("\n\n\n\n");
        if(tileNum == 1)
            player.heal(0);
        if(tileNum == 2) {
            Map.getPaths();
            System.out.println("You are at an unlocked door,"
                    + "\nwould you like to go through the door?(Y/N)");
            do {
                str = scan.nextLine();
            } while(!str.matches("(?i)[yn]{1}"));

            if(str.equalsIgnoreCase("n"))
                Map.navigate("backward");
            Map.navigate("forward");
        } 
        if(tileNum == 3) {
            player.itemGet(new Item(player.getClassType()));
            Map.setTile(Map.getX(), Map.getY(), 0);
        }
        if(tileNum == 4) {
            Map.getPaths();
            System.out.println("You stumble upon a teleporter,"
                    + "\nwould you like to use the teleporter?(Y/N)");
            do {
                str = scan.nextLine();
            } while(!str.matches("(?i)[yn]{1}"));

            if(str.equalsIgnoreCase("n")) {
                Map.navigate("backward");
                Map.navigate("forward");
            } else
                Map.teleport();
        }
        if(tileNum == 5) {
            System.out.println("You picked up a key!");
            Map.setTiles(-2, 2);
			Map.setTile(Map.getX(), Map.getY(), 0);
        }
		if(tileNum == 6) {
			System.out.println("You found your dead body and gain back half of your lost experience points!");
			player.addExp(player.expLost);
			Map.setTile(Map.getX(), Map.getY(), 0);
		}
        if(tileNum == 7) {
            Map.getPaths();
            System.out.println("The doors pulse with dark energy"
                    + "\nare you sure you want to enter the room ahead?(Y/N)");
            do {
                str = scan.nextLine();
            } while(!str.matches("(?i)[yn]{1}"));

            if(str.equalsIgnoreCase("n"))
                Map.navigate("backward");
            Map.navigate("forward");
        }
        if(tileNum == 8) {
            encounter(Enemy.Class.SONIC_THE_DUCK_HAT, true);
			if(!player.isDead()) {
				System.out.println("Congratulations! You have beaten the game!"
				+ "\nYour reward is the sense of accomplishment that you now feel for completing such a fantastic game!"
				+ "\nAlso, typing 'I am Bradley Johnson' from the navigation menu gives you god mode.");
			}
        }
    }

    /**
     * Prompts the user to adjust their terminal size
     */
    public static void configureScreen() {
        System.out.println("x\n+");
        for(int i = 0; i < 32; i++) {
            System.out.print("|");
            if(i == 29)
                System.out.print("\t\t\t\t\tPlease adjust your terminal height");
            if(i == 30)
                System.out.print("\t\t\t\t\tso that everything up to the + at");
            if(i == 31)
                System.out.print("\t\t\t\t\tthe top is visible, but the x is not.");

            System.out.println();
        }
        System.out.print("+\t\t\t\t\tpress Enter to continue...");
        scan.nextLine();
        
        for(int i = 0; i < 3; i++)
            System.out.println();
        System.out.println("\t\tM       M IIIII  SSSSS  SSSSS IIIII  OOOOO   N     N");
        System.out.println("\t\tMM     MM   I   S      S        I   O     O  NN    N");
        System.out.println("\t\tM M   M M   I   S      S        I   O     O  N N   N");
        System.out.println("\t\tM  M M  M   I   S      S        I   O     O  N  N  N");
        System.out.println("\t\tM   M   M   I    SSSS   SSSS    I   O     O  N   N N");
        System.out.println("\t\tM       M   I        S      S   I   O     O  N    NN");
        System.out.println("\t\tM       M   I        S      S   I   O     O  N    NN");
        System.out.println("\t\tM       M   I        S      S   I   O     O  N     N");
        System.out.println("\t\tM       M   I        S      S   I   O     O  N     N");
        System.out.println("\t\tM       M IIIII SSSSS  SSSSS  IIIII  OOOOO   N     N");
        System.out.println();
        System.out.println("\t\t       QQQQQQ    U     U EEEEEE  SSSSS TTTTTTT");
        System.out.println("\t\t      Q      Q   U     U E      S         T");
        System.out.println("\t\t      Q      Q   U     U E      S         T");
        System.out.println("\t\t      Q      Q   U     U E      S         T");
        System.out.println("\t\t      Q      Q   U     U EEE     SSSS     T");
        System.out.println("\t\t      Q      Q   U     U E           S    T");
        System.out.println("\t\t      Q      Q   U     U E           S    T");
        System.out.println("\t\t      Q     QQ   U     U E           S    T");
        System.out.println("\t\t      Q      QQ  U     U E           S    T");
        System.out.println("\t\t       QQQQQQ  Q  UUUUU  EEEEEE SSSSS     T");

        for(int i = 0; i < 6; i++)
            System.out.println();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        //Make a minimap for the user filled with "?" for any spaces unseen - DONE
        //Use getPaths to do it or something
        //        for (int i = 0; i < 21; i++) {
        //            for(int val: Map.map[i]) {
        //                System.out.print(val);
        //            }
        //            System.out.println();
        //        }
        //Big mistake! Map's backwards, right is left, left is right, x is y, everything is fucked.
        //Fixed kinda, switched x and y around
        //Have to fix the headings, I might be starting out facing South or North, probably South - FIXED
        //Also, right turns left, I think... Need to fix some stuff... - FIXED
        configureScreen();
		boolean gameover = false;
        int attackPts;
        int speedPts;
        int healthPts;
        int numPoints;
        String str;
        String name;
        Character.Class classType = null;

        System.out.println("********************************************************************************");
        System.out.println("\t\t\t\tCHARACTER SHEET");
        System.out.println("********************************************************************************");
        System.out.print("\t\t\tName  :\t");
        name = scan.nextLine();
        while(name.equals("")) {
            System.out.print("\t\t\t      :\t");
            name = scan.nextLine();
        }
        System.out.println();
        System.out.println("\t\t\tClass");
        System.out.println("\t\t\t\tWarrior\n\t\t\t\tWizard\n\t\t\t\tRogue");

        do {
            System.out.print("\t\t\t      :\t");
            str = scan.nextLine();

            if(str.equalsIgnoreCase("WARRIOR"))
                classType = Character.Class.WARRIOR;
            else if(str.equalsIgnoreCase("WIZARD"))
                classType = Character.Class.WIZARD;
            else if(str.equalsIgnoreCase("ROGUE"))
                classType = Character.Class.ROGUE;

        } while(classType == null);
        System.out.println();

        player = new Character(name, classType);
        System.out.println("\t\tSkill Points  : ");

        do {
            attackPts = 0;
            speedPts = 0;
            healthPts = 0;
            numPoints = 0;
            System.out.println("\t\tYou have 10 points to distribute");

            System.out.print("\t\t\tAttack:\t");
            attackPts = scan.nextInt();

            System.out.print("\t\t\tSpeed :\t");
            speedPts = scan.nextInt();

            System.out.print("\t\t\tHealth:\t");
            healthPts = scan.nextInt();

            numPoints = attackPts + speedPts + healthPts;

        } while(numPoints != 10 || attackPts <= 0 || speedPts <= 0 || healthPts <= 0);

        System.out.println("\n********************************************************************************");

        player.addAP(attackPts);
        player.addSP(speedPts);
        player.addHP(healthPts);

        player.heal(0);

        System.out.println(player);
        System.out.println("\n" +
                player.getName() + " sets out on a journey to vanquish all non-" + player.getClassType() + "s, starting in this cave." +
                "\nDetermined in their quest, " + player.getName() + " continues forth into the cave...\n");

        scan.nextLine();
        System.out.print("Press Enter to continue...");
        scan.nextLine();
        System.out.println("\n\n\n\n");
        Map.drawMap();
        Message.position();


        while(!gameover) {
            //There are some Threads still running after main exits for some reason, have to find these and end them. - DONE
            // more of this stuff, add more user interaction and fix the battle system so enemies can die -DONE
            str = "";

            System.out.println("What would you like to do? ");
            str = scan.nextLine();

            if(str.matches("(?i)(forward|backward|right|left|f|b|r|l)")) {
                Map.navigate(str);

                if(player.isDead()) {
                    Map.goHome();
					player.addExp(0);
					player.heal(0);
				}
                Message.position();

            } else if(str.matches("(?i)(inv|inventory|status)"))
                System.out.println(player);
            else if(str.matches("(?i)(map|minimap|position|pos)"))
                Message.position();
            else if(str.matches("(?i)(help|h|\\?)"))
                Message.help();
            else if(str.matches("(?i)(tips|tip)"))
                Message.tip();
            else if(str.equalsIgnoreCase("exit")) {
                gameover = true;
            }
            else if(str.equals("I am Bradley Johnson")) {
                player.addHP(999);
                player.addSP(999);
                player.addAP(999);
                player.heal(0);
            }
            else
                System.out.println("Not a valid command, type help for more options");
            //Should prompt for user input and call navigate when the player decides to move -DONE
            //Shouldn't enter battle phase unless player moves -DONE
            //Give options to check current position, stats, inventory(???)(probably not), whatever -DONE
            //Checks for random encounter at end of movement -DONE
            //Add a help option giving them choices of what commands to type -DONE
        }
    }
}

/*
 *  I need some kind of looping function that allows the user to choose to go somewhere or
 *  check their stats or return home to be healed. 0, 0 should be home, the rest will be a
 *  cave to be explored, random monster encounters will happen and I should place armor and
 *  upgrades around the map, not at random. No monster encounters will occur on treasure areas.
 *  Seed a random number generator with a 50%(?) chance of having an encounter every space. Use commands
 *  like backward, forward, left, and right to navigate the map, I will assume they start at
 *  the top left, forward depends on the direction that they're facing(!! shit didn't think of that).
 *  Maybe no backward is necessary... Provide a check orientation and allow forward movement and
 *  turns to happen. Make the orientation modulus 360(or 4) so 1 full rotation will reset position to 0.
 *  Maybe add a boss encounter on a space with set moves. Game over when dead. Later on maybe add
 *  tougher monsters when you reach double digits any direction. Player should know current position
 *  e.g.(4, 5), not making a labyrinth game. Put walls in places so the map isn't just a big field.
 *  Give directions to player for open paths, e.g. openings to the front, back, and right. Make it so that
 *  it isn't turn based combat, but the one with the faster speed attacks every 15 - speed turns
 *  e.g. rogue with 11 speed attacks every 4 loops, where warrior with 1 speed attacks every 14
 *  rogue attacks 3 times in the time that a warrior attacks once. Crit attacks should be accounted for
 *  maybe 10% chance to crit. Crit should do *3 dmg. Use compass orientation instead of degrees, translates
 *  to player better.
 *  
 *  Other stuff to add:
 *  Monster stats - DONE
 *  Attack dmg randomization - DONE
 *  Hit percent randomization - DONE
 *  Movement with 2D arrays - DONE
 *  ??Maybe some kind of GUI later on - DONE enough
 *  Weapons that add to damage - DONE
 *  Armor that subtracts enemy damage - DONE
 *  Navigation back and forth - DONE
 *  Add Healing at home - DONE
 *  Add level system - DONE
 *  Add different areas - DONE
 *  Add random crits - DONE
 *  Add Treasure - DONE
 *  Implement Weapons and Armor - DONE
 *  Add a Boss character - DONE
 *  Add a working key for the last door - DONE
 *  Add symbols for key and boss door - DONE
 *  End game after killing the final boss - DONE kinda
 *  TODO - Game is WAYY too hard, need to balance much more, make it more fun - More tweaking required
 *  Maybe make an Entity superclass for Enemy and Character, they share many of the same methods
 *  Ehh, dunno about that one, most are slightly different, not worth it..
 *
 *  Add Tips that are called at random with the tip command - DONE
 *	-Return home for heals
 *	-Monsters will not be encountered unless moving forward, this includes bumping into walls like a retard
 *	-There is a treasure chest hidden in every area, you will need all the loot you can get for the last area
 *	-Level 5 is the max level
 *	-Running from a battle opens you up to a 10% chance of dying instantly
 *	-Any items that you find are equipped automatically
 *	-No continues. Death means game over, man
 *	-
 *  Other Stuff... Keep it simple and finish late tomorrow... Yeah, right.
 */