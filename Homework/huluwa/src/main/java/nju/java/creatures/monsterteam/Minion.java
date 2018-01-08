package nju.java.creatures.monsterteam;
import nju.java.Ground;

public class Minion extends MonsterTeam{
    private int index;

    public Minion(Ground ground){
        super(ground);
    }

    public Minion(int x, int y, Ground ground, int index){
       super(x, y, ground);
       this.index = index;
       this.hurt = 50;
   }

   @Override
   public String toString(){
        return "小喽啰" + index;
   }
}

