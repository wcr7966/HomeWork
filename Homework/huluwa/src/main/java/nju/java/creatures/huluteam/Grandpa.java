package nju.java.creatures.huluteam;

import nju.java.Ground;
import nju.java.position.*;

public class Grandpa extends HuluTeam{
    public Grandpa(int x, int y, Ground ground){
        super(x, y, ground);
        this.hurt = 10;
    }

    @Override
    public String toString(){
        return "爷爷";
    }
}

