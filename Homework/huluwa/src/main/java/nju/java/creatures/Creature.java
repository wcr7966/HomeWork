package nju.java.creatures;
import nju.java.Field;
import nju.java.Ground;
import nju.java.position.*;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.net.URL;

public abstract class Creature implements Runnable, Serializable{
    private Position position = null;
    public Thread thread = null;
    public Boolean dead = false;
    protected int hurt = 0;
    protected Image img;
    protected Ground ground;

    public Creature(Ground ground){
        this.ground = ground;
    }

    public Creature(int x, int y, Ground ground){
        this.position = new Position(x, y);
        this.ground = ground;
    }

    public void setThread(Thread thread){
        this.thread = thread;
    }

    public void setPosition(Position position){
        this.position = position;
        this.position.setHolder(this);
    }

    public void setImg(Image img){
        this.img = img;
    }

    public void setImgFile(String filename){
        URL url = this.getClass().getClassLoader().getResource(filename);
        ImageIcon imageIcon = new ImageIcon(url);
        this.img = imageIcon.getImage();
    }

    public void setDead(){
        this.dead = true;
    }

    public Position getPosition(){
        //System.out.println("x: " + this.position.getX() + "y: " + this.position.getY());
        return this.position;
    }

    public int getHurt(){
        return this.hurt;
    }

    public Image getImg() {
        return img;
    }

    public boolean isDead(){
        return dead;
    }

    public int distance(Creature b){
        return Math.abs(this.getPosition().getX() - b.getPosition().getX()) + Math.abs(this.getPosition().getY() - b.getPosition().getY());
    }

    public void move(int x, int y){
        //int nx = this.position.getX() + x;
        //int ny = this.position.getY() + y;
        this.getPosition().setX(x);
        this.getPosition().setY(y);
    }

    public abstract void run();
}


