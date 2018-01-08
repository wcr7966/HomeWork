package nju.java;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.io.File;
import javax.swing.*;
import static nju.java.Common.*;
import nju.java.creatures.*;
import nju.java.io.*;

public class Field extends JPanel {

    private Ground ground = null;

    private Image backgroundImage = null; // 背景图片
    private Image grandpaImage = null;
    private Image[] brothersImage = new Image[7];
    private Image snakeImage = null;
    private Image scorpionImage = null;
    private Image minionsImage = null;
    private Image deadHuluwa = null;
    private Image deadMonster = null;


    public Field(Ground ground) {

        this.ground = ground;
        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld();
    }

    public Image getGrandpaImage() {
        return grandpaImage;
    }

    public Image[] getBrothersImage() {
        return brothersImage;
    }

    public Image getSnakeImage() {
        return snakeImage;
    }

    public Image getScorpionImage() {
        return scorpionImage;
    }

    public Image getMinionsImage() {
        return minionsImage;
    }

    public Image getDeadHuluwa() {
        return deadHuluwa;
    }

    public Image getDeadMonster() {
        return deadMonster;
    }

    public void setGrandpaImage(Image image){
        this.grandpaImage = image;
    }

    public void setBrothersImage(Image image, int i){
        this.brothersImage[i] = image;
    }

    public void setSnakeImage(Image image){
        this.snakeImage = image;
    }

    public void setScorpionImage(Image image){
        this.scorpionImage = image;
    }

    public void setMinionsImage(Image image){
        this.minionsImage = image;
    }


    public final void initWorld() {

        URL url = this.getClass().getClassLoader().getResource("背景.png");
        ImageIcon imageIcon = new ImageIcon(url);
        this.backgroundImage = imageIcon.getImage();

        url = this.getClass().getClassLoader().getResource("葫芦娃墓碑.png");
        imageIcon = new ImageIcon(url);
        this.deadHuluwa = imageIcon.getImage();

        url = this.getClass().getClassLoader().getResource("妖怪墓碑.png");
        imageIcon = new ImageIcon(url);
        deadMonster = imageIcon.getImage();
    }

    public synchronized void buildWorld(Graphics g) {

        Font font =new Font("隶书",Font.BOLD,30);
        g.setFont(font);
        g.drawImage(backgroundImage, 0, 0, WINDOWS_WIDTH, WINDOWS_HEIGHT, this);

        g.drawString("葫芦娃大战妖怪", WINDOWS_WIDTH/2 - 2*SPACE, WINDOWS_HEIGHT/2 - SPACE);
        font =new Font("隶书",Font.BOLD,20);
        g.setFont(font);
        g.drawString("按下空格键开始游戏", WINDOWS_WIDTH/2 - 2*SPACE, WINDOWS_HEIGHT/2);
        g.drawString("按下'L'回放", WINDOWS_WIDTH/2 - 2*SPACE, WINDOWS_HEIGHT/2 + SPACE);

        if(ground.getStatus() != Status.Start) {
            g.drawImage(backgroundImage, 0, 0, WINDOWS_WIDTH, WINDOWS_HEIGHT, this);

            for(Creature i: ground.getHuluTeams()){
                g.drawImage(i.getImg(), i.getPosition().getX(), i.getPosition().getY(), SPACE, SPACE, this);
            }
            for(Creature i: ground.getMonsterTeams()){
                g.drawImage(i.getImg(), i.getPosition().getX(), i.getPosition().getY(), SPACE, SPACE, this);
            }
            for(Creature i: ground.getDeadCreature()){
                g.drawImage(i.getImg(), i.getPosition().getX(), i.getPosition().getY(), SPACE, SPACE, this);
            }
            if(ground.getStatus() == Status.HuluWin){
                g.setColor(Color.green);
                g.drawString("葫芦娃获胜！", WINDOWS_WIDTH/5, WINDOWS_HEIGHT/4);
                g.setColor(Color.black);
                g.drawString("'回车' - 重新开始", WINDOWS_WIDTH/5, WINDOWS_HEIGHT/4 + SPACE);
                g.drawString("'L' - 回放", WINDOWS_WIDTH/5, WINDOWS_HEIGHT/4 + 2*SPACE);
                g.drawString("'R' - 重命名", WINDOWS_WIDTH/5, WINDOWS_HEIGHT/4 + 3*SPACE);
            }
            else if(ground.getStatus() == Status.MonsterWin){
                g.setColor(Color.red);
                g.drawString("妖怪获胜！", WINDOWS_WIDTH/5, WINDOWS_HEIGHT/4);
                g.setColor(Color.black);
                g.drawString("'回车' - 重新开始", WINDOWS_WIDTH/5, WINDOWS_HEIGHT/4 + SPACE);
                g.drawString("'L' - 回放", WINDOWS_WIDTH/5, WINDOWS_HEIGHT/4 + 2*SPACE);
                g.drawString("'R' - 重命名", WINDOWS_WIDTH/5, WINDOWS_HEIGHT/4 + 3*SPACE);
            }
            else if(ground.getStatus() == Status.Dogfall){
                g.setColor(Color.YELLOW );
                g.drawString("双方打平！", WINDOWS_WIDTH/5, WINDOWS_HEIGHT/4);
                g.setColor(Color.black);
                g.drawString("'回车' - 重新开始", WINDOWS_WIDTH/5, WINDOWS_HEIGHT/4 + SPACE);
                g.drawString("'L' - 回放", WINDOWS_WIDTH/5, WINDOWS_HEIGHT/4 + 2*SPACE);
                g.drawString("'R' - 重命名", WINDOWS_WIDTH/5, WINDOWS_HEIGHT/4 + 3*SPACE);
            }
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        buildWorld(g);
    }

    class TAdapter extends KeyAdapter {
        @Override
        public synchronized void keyPressed(KeyEvent e){

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE){ // 开始
                if( ground.status == Status.Start){

                    ground.setStatus(Status.Fight);
                    ground.init(100);
                }
            }
            else if(key == KeyEvent.VK_L){

                JFileChooser jfc=new JFileChooser(".");
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
                int flag = jfc.showDialog(new JLabel(), "选择");
                File file=jfc.getSelectedFile();
                if(file.isDirectory()){
                    System.out.println("文件夹:"+file.getAbsolutePath());
                }else if(file.isFile()){
                    System.out.println("文件:"+file.getAbsolutePath());
                }
                System.out.println(jfc.getSelectedFile().getName());


                if(flag == jfc.APPROVE_OPTION){
                    Read.setReadFile(jfc.getSelectedFile());
                    ground.setStatus(Status.Replay);
                    ground.init(2);
                }


            }
            else if(key == KeyEvent.VK_ENTER){
                if(ground.getStatus() == Status.HuluWin || ground.getStatus() == Status.MonsterWin || ground.getStatus() == Status.Dogfall){
                    ground.getCreatureThreads().clear();
                    Write.setWriteFile(null);
                    ground.setStatus(Status.Fight);
                    ground.init(200);
                }
            }

            else if(key == KeyEvent.VK_R){
                Write.changeName();
            }

            repaint();
        }
    }

}