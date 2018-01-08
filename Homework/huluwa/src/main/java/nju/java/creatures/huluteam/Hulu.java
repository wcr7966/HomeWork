package nju.java.creatures.huluteam;

import nju.java.Ground;
import nju.java.Rank;

import java.util.Random;

enum Color {
    红, 橙, 黄, 绿, 青, 蓝, 紫
}


public class Hulu extends HuluTeam {
    private int index;

    public Hulu(int x, int y, Ground ground, int index) {
        super(x, y, ground);
        Random r = new Random();
        this.hurt = 50 + r.nextInt(50);
        this.index = index;
    }

    @Override
    public String toString() {
        return Rank.values()[index].toString();
    }
}
