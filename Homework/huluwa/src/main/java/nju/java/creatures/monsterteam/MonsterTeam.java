package nju.java.creatures.monsterteam;
import nju.java.Ground;
import nju.java.Status;

import nju.java.creatures.Creature;
import nju.java.creatures.huluteam.Grandpa;
import nju.java.creatures.huluteam.HuluTeam;
import static nju.java.Common.*;

import java.util.ArrayList;
import java.util.Random;

public abstract class MonsterTeam extends Creature{

    public MonsterTeam(Ground ground){
        super(ground);
    }

    public MonsterTeam(int x, int y, Ground ground){
        super(x, y, ground);
    }

    public ArrayList<HuluTeam> getHuluwa(){
        ArrayList<HuluTeam> m = ground.getHuluTeams();
        ArrayList<HuluTeam> r =new ArrayList<HuluTeam>();
        for(HuluTeam b: m){
            if(this.distance(b) < SPACE){
                r.add(b);
            }
        }
        return r;
    }

    public HuluTeam getNearest(){
        ArrayList<HuluTeam> m = ground.getHuluTeams();
        HuluTeam r = null;
        int min = 1000;
        for(HuluTeam b: m){
            if( this.distance(b) < min) {
                min = this.distance(b);
                r = b;
            }
        }
        return r;
    }

    public void run(){
        while(!Thread.interrupted()){
            if(ground.getStatus() == Status.Fight){
                try {
                    if (isDead() || ground.getStatus() != Status.Fight) {
                        Thread.sleep(100);
                        continue;
                    }
                    ArrayList<HuluTeam> huluTeams = getHuluwa();
                    //System.out.println("可攻击的葫芦娃有" + huluTeams.size());
                    int max = 0;
                    HuluTeam h = null;
                    if (!huluTeams.isEmpty()) {
                        for (HuluTeam i : huluTeams) {
                            if (i.getHurt() > max) {
                                max = i.getHurt();
                                h = i;
                            }
                        }
                    }
                    if (h != null) {
                        if (ground.ifAttack(this, h)) {

                            Random r;
                            if (this instanceof Snake) {
                                //System.out.println("蛇精攻击");
                                r = new Random();
                                int p = r.nextInt(10);
                                if (p < 8)
                                    h.setDead();
                                else this.setDead();
                            }
                            else if (this instanceof Scorpion){
                                //System.out.println("蝎子精攻击");
                                r = new Random();
                                int p = r.nextInt(10);
                                if (p < 5)
                                    h.setDead();
                                else this.setDead();
                            }
                            else if(this instanceof Minion) {
                                //System.out.println("小喽啰攻击");
                                r = new Random();
                                int p = r.nextInt(10);
                                if (p < 4)
                                    h.setDead();
                                else this.setDead();
                            }
                        }
                    } else {
                        HuluTeam m = getNearest();

                        int dx = m.getPosition().getX() - this.getPosition().getX();
                        int dy = m.getPosition().getY() - this.getPosition().getY();
                        int x = 0, y = 0;
                        if (Math.abs(dx) > Math.abs(dy)) {
                            if (dx > 0)
                                x = 5;
                            else x = -5;
                        }
                        else {
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
