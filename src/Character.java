import java.util.ArrayList;
import java.util.Random;

public class Character {
    public enum Class {
        WARRIOR,
        WIZARD,
        ROGUE
    }
    public static final int MAX_EXP = 1000;
    private int AP = 0;
    private int SP = 0;
    private int HP = 0;
    private int currHP = 0;
    private String name;
    private int level = 1;
    private int exp;
	public int expLost;
    private Class classType;
    private Random rand = new Random();
    private ArrayList<Item.Type> inventory = new ArrayList<Item.Type>();


    public Character(String name, Class classType) {
        this.name = name;
        this.classType = classType;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }
    /**
     * @return the class as an integer
     */
    public Class getClassType() {
        return classType;
    }
    /**
     * @return the player's speed
     */
    public int getSpeed() {
        return SP + getSpdMod();
    }
    /**
     * @return the player's health
     */
    public int getHealth() {
        return HP;
    }
    /** Resets experience if param is 0, otherwise increases it by param value.
	 *  Resets when player dies.
     * @param expGained the amount of exp points gained
     */
    public void addExp(int expGained) {
        if(expGained == 0) {
			expLost = exp / 2;
			exp = 0;
		} else if(level < 5) {
            exp += expGained;
            if(exp > (MAX_EXP * level))
                levelUp();
        }
    }
    /**
     * Called when a level is gained, adds new stat points and increments level
     */
    private void levelUp() {
        exp %= (MAX_EXP * level);
        level++;
        addAP(getClassType() == Class.WARRIOR ? 2 : 1);
        addSP(getClassType() == Class.ROGUE ? 2 : 1);
        addHP(getClassType() == Class.WIZARD ? 2 : 1);
        System.out.println("Congratulations, you've leveled up!");
        System.out.println(toString());
    }
    /** Warrior gets +3 to attack 'cause dey strong
     * @param attackPts the amount of attack points given to character
     */
    public void addAP(int attackPts) {
        if(getClassType() == Class.WARRIOR && AP == 0) {
            AP = 3;
        }
        AP += attackPts;

    }
    /** Rogue gets +3 to speed 'cause dey fast
     * @param speedPts the amount of speed points given to character
     */
    public void addSP(int speedPts) {
        if(getClassType() == Class.ROGUE && SP == 0) {
            SP = 3;
        }
        SP += speedPts;
    }
    /** Wizard gets +30 to HP 'cause I fucking said so
     * @param healthPts the amount of health points given to character
     */
    public void addHP(int healthPts) {
        if(getClassType() == Class.WIZARD && HP == 0) {
            HP = 30;
        }
        HP += (healthPts * 10);
        heal(healthPts * 10);
    }
    /**
     * @param item the item that the player picked up
     */
    public void itemGet(Item item) {
        System.out.println("You acquired a " + item.type);
        if(item.type.getHpMod() > 0) {
            System.out.println("You eat the " + item.type + " and feel healthier");
            addHP(item.type.getHpMod());
        }
        inventory.add(item.type);

        //Probably implement by adding things to attack() to check for weapons, etc. - DONE
        //Eat the hearts so I don't have to do HP/HP + HP/HP in status - DONE
        //Add a getBonus method to Item class - NO
        //Maybe do some other stuff too
    }
    /**
     * @return the player's attack modifier
     */
    private int getAtkMod() {
        int modifier = 0;
        for(Item.Type item: inventory) {
            modifier += item.getAtkMod();
        }
        return modifier;
    }
    /**
     * @return the player's defense modifier
     */
    private int getDefMod() {
        int modifier = 0;
        for(Item.Type item: inventory) {
            modifier += item.getDefMod();
        }
        return 3;
    }
    /**
     * @return the player's speed modifier
     */
    private int getSpdMod() {
        int modifier = 0;
        for(Item.Type item: inventory) {
            modifier += item.getSpdMod();
        }
        return modifier;
    }
    /**
     * @param amount the amount of health taken away from player
     */
    public void hit(int amount) {
		if(getDefMod() != 0 && amount != 0) {
			if(getDefMod() >= amount)
				System.out.println("But your armor absorbed all of it");
			else {
				System.out.println("But your armor absorbed " + getDefMod() + " points of it");
				amount -= getDefMod();
			}
		}
        currHP -= amount;
		currHP = currHP < 0 ? 0 : currHP;
    }
    /** Heals player for full health if param is 0, otherwise uses param value
     * @param amount the amount of health given to the player
     */
    public void heal(int amount) {
        currHP += amount == 0 ? HP : amount;
        currHP -= currHP > HP ? (currHP - HP) : 0;
    }
    /**
     * @return the amount of damage that the player does to the enemy
     */
    public int attack() {
        int hit;
        if(rand.nextInt(3) > 0) {
            hit = AP;
            hit += rand.nextInt(3);
            hit -= rand.nextInt(3);
            hit += getAtkMod();
            if(rand.nextInt(10) == 0) {
                hit *= 3;
                System.out.println("Your attack crit, dealing " + Math.abs(hit) + " damage");
                return Math.abs(hit);
            }
            System.out.println("Your attack hit, dealing " + Math.abs(hit) + " damage");
            return Math.abs(hit);
        }
        System.out.println("Your attack missed");
        return 0;
    }
    /**
     * @return true if character has 0 hp or less
     */
    public boolean isDead() {
        return currHP <= 0;
    }
    @Override
    public String toString() {
        String stat = name + "\n[AP = " + AP + (getAtkMod() > 0 ? " + " + getAtkMod() : "") 
        + ", HP = " + currHP + "/" + HP + (getDefMod() > 0 ? " + " + getDefMod() + " DEF" : "") 
        + ", SP = " + SP + (getSpdMod() > 0 ? " + " + getSpdMod() : "")
        + ", class = " + classType + ", exp = " + exp + ", level = " + level + "]\n";
        String inv = "";
        if(!inventory.isEmpty()) {
            inv = "|INVENTORY";
            int length = stat.length() - inv.length() - name.length() - 3;
            for(int i = 0; i < length; i++) {
                inv += "+";
            }
            inv += "|\n|";
            length = inv.length() - 3;
            int temp = 0;
            for(Item.Type item: inventory) {
                if((temp + item.toString().length() + 3) < length) {
                    inv += ("   " + item);
                    temp += item.toString().length() + 3;
                } else {
                    for(int j = 0; j < (length - temp - 1); j++)
                        inv += " ";
                    inv += "|\n|";
                    inv += ("   " + item);
                    temp = item.toString().length() + 3;
                }
            }
            for(int k = 0; k < (length - temp - 1); k++)
                inv += " ";
            inv += "|\n";
            for(int l = 0; l < length + 1; l++)
                inv += "-";
        }
        // Use Item array and append names to check against the length and not have things leaking over - DONE
        /* 
         * while(items != empty)
         *   for(length) {
         *      if(Itemlist becomes longer than length)
         *          --Item;
         *   }
         *   lineBreak;
         * }
         */
        return stat + inv;
    }
}
