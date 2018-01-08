package nju.java.creatures.monsterteam;

import nju.java.Ground;
import nju.java.position.*;

public class Snake extends MonsterTeam {
   public Snake(int x, int y, Ground ground){
       super(x, y, ground);
       this.hurt = 80;
   }

    @Override
    public String toString(){
        return "蛇精";
    }
}

