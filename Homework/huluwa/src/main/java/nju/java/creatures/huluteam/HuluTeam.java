package nju.java.creatures.huluteam;
import nju.java.Ground;
import nju.java.Status;
import nju.java.creatures.*;
import nju.java.creatures.monsterteam.Minion;
import nju.java.creatures.monsterteam.MonsterTeam;
import nju.java.creatures.monsterteam.Scorpion;
import nju.java.creatures.monsterteam.Snake;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Random;
import static nju.java.Common.*;

public abstract class HuluTeam extends Creature {

    public HuluTeam(int x, int y, Ground ground){
        super(x, y, ground);
    }

    public ArrayList<MonsterTeam> getMonster(){
        ArrayList<MonsterTeam> m = ground.getMonsterTeams();
        ArrayList<MonsterTeam> r =new ArrayList<MonsterTeam>();
        for(MonsterTeam b: m){
            if(this.distance(b) <= SPACE){
                r.add(b);
            }
        }
        return r;
    }

    public MonsterTeam getNearest(){
        ArrayList<MonsterTeam> m = ground.getMonsterTeams();
        MonsterTeam r = null;
        int min = 1000;
        for(MonsterTeam b: m){
            if(this.distance(b) < min) {
                min = this.distance(b);
                r = b;
            }
        }
        return r;
    }

    public void run(){
        while(!Thread.interrupted()){
            if(ground.getStatus() == Status.Fight){
                try{
                    if(isDead() || ground.getStatus() != Status.Fight){
                        Thread.sleep(100);
                        continue;
                    }
                    ArrayList<MonsterTeam> monsterTeams = getMonster();
                    //System.out.println("可攻击的妖怪有" + monsterTeams.size());
                    MonsterTeam m = null;
                    if(!monsterTeams.isEmpty()){
                        int max = 0;
                        for(MonsterTeam i: monsterTeams){
                            if(i.getHurt() > max) {
                                max = i.getHurt();
                                m = i;
                            }
                        }
                    }
                    if(m != null){
                        if(ground.ifAttack(this, m)) {

                            Random r;
                            if (this instanceof Hulu) {
                                r = new Random();
                                int p = r.nextInt(10);
                                if (p < 6)
                                    m.setDead();
                                else this.setDead();
                            } else if (this instanceof Grandpa) {
                                r = new Random();
                                int p = r.nextInt(10);
                                if (p < 4)
                                    m.setDead();
                                else this.setDead();
                            }
                        }
                    }
                    else{
                        //找到距离最近的{
                        m = getNearest();
                        int dx = m.getPosition().getX() - this.getPosition().getX();
                        int dy = m.getPosition().getY() - this.getPosition().getY();
                        int x = 0, y = 0;
                        if (Math.abs(dx) > Math.abs(dy)) {
                            if (dx > 0)
                                x = 5;
                            else x = -5;
                        } else {
                            if (dy > 0)
                                y = 5;
                            else y = -5;
                        }
                        if (ground.ifWalk(x, y, this))
                            move(this.getPosition().getX() + x, this.getPosition().getY() + y);
                    }
                    Thread.sleep(200);
                }catch(Exception e){
                    System.out.println("捕获异常");
                }
            }
            else if(ground.getStatus() == Status.Replay){
                try {
                    Thread.sleep(200);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
