package nju.java;

import nju.java.creatures.Creature;
import nju.java.creatures.huluteam.Grandpa;
import nju.java.creatures.huluteam.Hulu;
import nju.java.creatures.huluteam.HuluTeam;
import nju.java.creatures.monsterteam.Minion;
import nju.java.creatures.monsterteam.MonsterTeam;
import nju.java.creatures.monsterteam.Snake;
import nju.java.creatures.monsterteam.Scorpion;
import nju.java.position.*;
import nju.java.io.Write;
import nju.java.io.Read;
import static nju.java.Common.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.net.URL;


public final class Ground extends JFrame {

    private Field field = null;
    private ArrayList<HuluTeam> huluTeams;
    private Grandpa grandpa;
    private Hulu[] brothers;

    private ArrayList<MonsterTeam> monsterTeams;
    private Snake snake;
    private Scorpion scorpion;
    private Minion[] minions;

    private ArrayList<Creature> deadCreature;

    private ArrayList<Thread> creatureThreads = null;

    public static Status status = Status.Start;

    private Timer timer ;
    private ActionListener timerTask ;


    public Ground() {
    }

    public void init(int time){
        initCreatures();
        initThread();
        initTimer(time);
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Status getStatus(){
        return status;
    }

    public ArrayList<HuluTeam> getHuluTeams() {
        return huluTeams;
    }

    public ArrayList<MonsterTeam> getMonsterTeams() {
        return monsterTeams;
    }

    public ArrayList<Creature> getDeadCreature() {
        return deadCreature;
    }

    public ArrayList<Thread> getCreatureThreads() {
        return creatureThreads;
    }

    public Field getField(){
        return field;
    }

    public void setStatus(Status s){
        this.status = s;
    }

    public void initCreatures(){
        huluTeams = new ArrayList<HuluTeam>();
        monsterTeams = new ArrayList<MonsterTeam>();
        deadCreature = new ArrayList<Creature>();

        grandpa = new Grandpa(WINDOWS_WIDTH/2 - 5 * SPACE, WINDOWS_HEIGHT / 2, this);
        URL url = this.getClass().getClassLoader().getResource("爷爷.png");
        ImageIcon imageIcon = new ImageIcon(url);
        grandpa.setImg(imageIcon.getImage());
        field.setGrandpaImage(imageIcon.getImage());
        huluTeams.add(grandpa);

        brothers = new Hulu[7];
        for(int i = 0; i < 7; i++){
            brothers[i] = new Hulu(WINDOWS_WIDTH/2 - 3 * SPACE ,(i + 1) * SPACE, this, i);
            url = this.getClass().getClassLoader().getResource(Rank.values()[i].toString()+".png");
            imageIcon = new ImageIcon(url);
            brothers[i].setImg(imageIcon.getImage());
            field.setBrothersImage(imageIcon.getImage(), i);
            huluTeams.add(brothers[i]);
        }


        minions = new Minion[7];
        minions[0] = new Minion(WINDOWS_WIDTH/2 + SPACE, WINDOWS_HEIGHT/2, this,0);
        url = this.getClass().getClassLoader().getResource("妖怪.png");
        imageIcon = new ImageIcon(url);
        minions[0].setImg(imageIcon.getImage());
        field.setMinionsImage(imageIcon.getImage());
        monsterTeams.add(minions[0]);
        int j = 1;
        for(int i = 1; i < 7; i++){
            if(i%2 == 1){
                minions[i] = new Minion(WINDOWS_WIDTH/2 + (j + 1)*SPACE, WINDOWS_HEIGHT/2 + j*SPACE, this, i);
            }
            else{
                minions[i] = new Minion(WINDOWS_WIDTH/2 + (j + 1)*SPACE,  WINDOWS_HEIGHT/2 - j*SPACE, this, i);
                j++;
            }
            minions[i].setImg(imageIcon.getImage());
            monsterTeams.add(minions[i]);
        }

        snake = new Snake(WINDOWS_WIDTH/2 + 4*SPACE, WINDOWS_HEIGHT/2, this);
        url = this.getClass().getClassLoader().getResource("蛇精.png");
        imageIcon = new ImageIcon(url);
        snake.setImg(imageIcon.getImage());
        field.setSnakeImage(imageIcon.getImage());
        monsterTeams.add(snake);

        scorpion = new Scorpion(WINDOWS_WIDTH/2 + 5*SPACE, WINDOWS_HEIGHT/2, this);
        url = this.getClass().getClassLoader().getResource("蝎子精.png");
        imageIcon = new ImageIcon(url);
        scorpion.setImg(imageIcon.getImage());
        field.setScorpionImage(imageIcon.getImage());
        monsterTeams.add(scorpion);

    }

    public void initTimer(int time){
        timerTask = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeList();
                checkStatus();
                field.repaint();
            }
        };
        timer = new Timer(time, timerTask);
        timer.start();
    }

    public void initThread(){
        creatureThreads = new ArrayList<Thread>();
        for(Creature i: huluTeams){
            Thread c = new Thread(i);
            creatureThreads.add(c);
            i.setThread(c);
        }

        for(Creature i: monsterTeams){
            Thread c= new Thread(i);
            creatureThreads.add(c);
            i.setThread(c);
        }

        for(Creature i: deadCreature){
            Thread c = new Thread(i);
            creatureThreads.add(c);
            i.setThread(c);
        }

        for(Thread i: creatureThreads){
            i.start();
        }
    }

    public void changeList(){

        if(getStatus() == Status.Fight) {

            Iterator<HuluTeam> it = huluTeams.iterator();
            while (it.hasNext()) {
                HuluTeam temp = it.next();
                if (temp.isDead()) {
                    temp.setImg(field.getDeadHuluwa());
                    deadCreature.add(temp);
                    it.remove();
                }
            }

            Iterator<MonsterTeam> it2 = monsterTeams.iterator();
            while (it2.hasNext()) {
                MonsterTeam temp = it2.next();
                if (temp.isDead()) {
                    temp.setImg(field.getDeadMonster());
                    deadCreature.add(temp);
                    it2.remove();
                }
            }

            try{
                Write.write(huluTeams, monsterTeams, deadCreature);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void checkStatus(){

        if( huluTeams.isEmpty() && !monsterTeams.isEmpty() ) {
            status = Status.MonsterWin;

            for (Thread t : creatureThreads)
                t.suspend();
            return;
        }
        else if(! huluTeams.isEmpty() && monsterTeams.isEmpty()){

            status = Status.HuluWin;
            for (Thread t : creatureThreads)
                t.suspend();
            return;
        }
        else if(huluTeams.isEmpty() && monsterTeams.isEmpty()){
            status = Status.Dogfall;
        }

        if(getStatus() == Status.Replay){
            String str;
            if(Read.getReadFile()==null)
                return;
            str = Read.read();
            if (str != null) {
                replay(str);
            }
            else{
                setStatus(Status.Close);
            }
        }

    }

    public boolean ifWalk(int dx, int dy, Creature c){
        int x = c.getPosition().getX() + dx;
        int y = c.getPosition().getY() + dy;
        if(x < 0 || y < 0 || x > WINDOWS_WIDTH || y > WINDOWS_HEIGHT)
            return false;
        else{
            for(Creature i: huluTeams){
                if(i == c)
                    continue;
                if(x == i.getPosition().getX() && y == i.getPosition().getY())
                    return false;
            }

            for(Creature i: monsterTeams){
                if(i == c)
                    continue;
                if(x == i.getPosition().getX() && y == i.getPosition().getY())
                    return false;
            }
        }
        return true;
    }

    public boolean ifAttack(Creature a, Creature b){
        int distance = a.distance(b);
        if(distance > 0 && distance <= SPACE){
            return true;
        }
        return false;
    }

    public void replay(String str){
        System.out.println(str);
        String name;
        Position p = new Position(-1, -1);
        boolean isAlive;

        String []temp = str.split(" ");
        name = temp[0];
        p.setX(Integer.parseInt(temp[1]));
        p.setY(Integer.parseInt(temp[2]));

        if(temp[3].equals("alive"))
            isAlive = true;
        else isAlive = false;

        if(name.equals("爷爷")){
            grandpa.setPosition(p);
            if(isAlive)
                grandpa.setImg(field.getGrandpaImage());
            else grandpa.setImg(field.getDeadHuluwa());
        }
        else if(name.equals("蛇精")){
            snake.setPosition(p);
            if(isAlive)
                snake.setImg(field.getSnakeImage());
            else snake.setImg(field.getDeadMonster());
        }
        else if(name.equals("蝎子精")){
            scorpion.setPosition(p);
            if(isAlive)
                scorpion.setImg(field.getScorpionImage());
            else scorpion.setImg(field.getDeadMonster());
        }
        else if(name.substring(1).equals("娃")){
            Rank r = Rank.valueOf(name);
            int index = r.ordinal();
            brothers[index].setPosition(p);
            if(isAlive)
                brothers[index].setImg(field.getBrothersImage()[index]);
            else brothers[index].setImg(field.getDeadHuluwa());
        }
        else if(name.substring(0, 3).equals("小喽啰")){
            int index = Integer.parseInt(name.substring(3, 4));
            minions[index].setPosition(p);
            if(isAlive)
                minions[index].setImg(field.getMinionsImage());
            else minions[index].setImg(field.getDeadMonster());
        }
        field.repaint();
    }
}