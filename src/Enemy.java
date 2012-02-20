import java.util.Random;

public class Enemy {
    /*
     * Make an enum for enemy classes, make the constructor randomize the type when called - DONE
     * give attributes inside of each enum for attack, speed, and health - DONE
     * use a random number(1-3) for each attack to check if it hit(66% hit rate) - DONE
     * use a random number for attack damage(atkpwr -2 to atkpwr +2) - DONE
     */
    public enum Class {
        MISSINGNO(0, 0, 0, 1),
        ZOMBIE(2, 1, 20, 90),
        SKELETON(2, 3, 10, 60),
        GOBLIN(2, 4, 30, 125),
        GHOST(4, 2, 30, 150),
        OGRE(5, 2, 50, 250),
        SPIDER(4, 5, 30, 200),
        GIANT(7, 1, 70, 400),
        RAPTOR(6, 6, 40, 375),
        SONIC_THE_DUCK_HAT(8, 8, 80, 1000000);

        private final int AP;
        private final int SP;
        private final int HP;
        private final int EXP;

        private Class(int atkPts, int spdPts, int hthPts, int expPts) {
            AP = atkPts;
            SP = spdPts;
            HP = hthPts;
            EXP = expPts;
        }

        public int getAP() {
            return AP;
        }

        public int getSP() {
            return SP;
        }

        public int getHP() {
            return HP;
        }

        public int getEXP() {
            return EXP;
        }
    }
    private Class classType;
    private int currHP;
    private Random rand = new Random();

    public Enemy(Class c) {
        int num = rand.nextInt(2);
        if(c == null) {
            if(Map.getX() < 10 && Map.getY() < 10) {
                //LEVEL 1
                if(num == 0)
                    classType = Class.ZOMBIE;
                else if(num == 1)
                    classType = Class.SKELETON;
            } else if(Map.getX() > 10 && Map.getY() < 10) {
                //LEVEL 2
                if(num == 0)
                    classType = Class.GOBLIN;
                else if(num == 1)
                    classType = Class.GHOST;
            } else if(Map.getX() < 10 && Map.getY() > 10) {
                //LEVEL 3
                if(num == 0)
                    classType = Class.OGRE;
                else if(num == 1)
                    classType = Class.SPIDER;
            } else if(Map.getX() > 10 && Map.getY() > 10) {
                //LEVEL 4
                if(num == 0)
                    classType = Class.GIANT;
                else if(num == 1)
                    classType = Class.RAPTOR;
            } else
                //Something goes wrong
                classType = Class.MISSINGNO;
        } else
            classType = c;
        currHP = classType.getHP();
    }
    
    public Enemy() {
        this(null);
    }
    /**
     * @return the type of enemy that is made
     */
    public Class getClassType() {
        return classType;
    }    
    /**
     * @return the enemy's speed
     */
    public int getSpeed() {
        return classType.getSP();
    }
    /**
     * @return the amount of experience given
     */
    public int getExperience() {
        return classType.getEXP();
    }
    /**
     * @param amount the amount of health taken away from enemy
     */
    public void hit(int amount) {
        currHP -= amount;
    }
    /**
     * @return the amount of damage that the enemy does to the player
     */
    public int attack() {
        int hit;
        if(rand.nextInt(3) > 0) {
            hit = classType.getAP();
            hit += rand.nextInt(3);
            hit -= rand.nextInt(3);
            if(rand.nextInt(10) == 0) {
                hit *= 3;
                System.out.println(getClassType() + " crit you for " + Math.abs(hit) + " damage!");    
                return Math.abs(hit);
            }
            System.out.println(getClassType() + " hit you for " + Math.abs(hit) + " damage!");
            return Math.abs(hit);
        }
        System.out.println(getClassType() + " missed its attack!");
        return 0;
    }
    /**
     * @return true if enemy has 0 hp or less
     */
    public boolean isDead() {
        return currHP <= 0;
    }
}
