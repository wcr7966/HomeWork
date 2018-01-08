package nju.java.creatures.monsterteam;

import nju.java.Ground;
import nju.java.position.*;

public class Scorpion extends MonsterTeam {
   public Scorpion(int x, int y, Ground ground){
       super(x, y, ground);
       this.hurt = 90;
   }

    @Override
    public String toString(){
        return "蝎子精";
    }
}

