
public class Item {

    public enum Type {
        SWORD(2, 0, 0, 0),
        STAFF(2, 0, 0, 0),
        DAGGER(2, 0, 0, 0),
        BAT_HEART(0, 0, 2, 0),
        CAT_HEART(0, 0, 2, 0),
        RAT_HEART(0, 0, 2, 0),
        SHIELD(0, 2, 0, 0),
        DEFENSE_ORB(0, 2, 0, 0),
        CLOAK(0, 2, 0, 0),
        FANCY_HAT(1, 1, 0, 1);
        
        private final int atkMod;
        private final int defMod;
        private final int hpMod;
        private final int spdMod;
        
        private Type(int aP, int dP, int hP, int sP) {
            atkMod = aP;
            defMod = dP;
            hpMod = hP;
            spdMod = sP;
        }

        /**
         * @return the atkMod
         */
        public int getAtkMod() {
            return atkMod;
        }

        /**
         * @return the defMod
         */
        public int getDefMod() {
            return defMod;
        }

        /**
         * @return the hpMod
         */
        public int getHpMod() {
            return hpMod;
        }

        /**
         * @return the spdMod
         */
        public int getSpdMod() {
            return spdMod;
        }   
    }
	
	public final Type type;
	
	public Item(Character.Class playerClass) {
		if(Map.getX() < 10 && Map.getY() < 10)//LEVEL 1
			if(playerClass == Character.Class.WARRIOR)
				type = Type.SWORD;
			else if(playerClass == Character.Class.WIZARD)
				type = Type.STAFF;
			else
				type = Type.DAGGER;
		else if(Map.getX() > 10 && Map.getY() < 10)//LEVEL 2
			if(playerClass == Character.Class.WARRIOR)
				type = Type.BAT_HEART;
			else if(playerClass == Character.Class.WIZARD)
				type = Type.CAT_HEART;
			else
				type = Type.RAT_HEART;
		else if(Map.getX() < 10 && Map.getY() > 10)//LEVEL 3
			if(playerClass == Character.Class.WARRIOR)
				type = Type.SHIELD;
			else if(playerClass == Character.Class.WIZARD)
				type = Type.DEFENSE_ORB;
			else
				type = Type.CLOAK;
		else//LEVEL 4
			type = Type.FANCY_HAT;
	}

    
}
