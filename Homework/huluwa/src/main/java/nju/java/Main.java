package nju.java;

import javax.swing.*;

import static nju.java.Common.*;

public class Main extends JFrame{
    public Main(){
        Ground ground = new Ground();
        Field field = new Field(ground);
        ground.setField(field);
        add(field);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOWS_WIDTH  ,
                WINDOWS_HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Ground");
    }

    public static void main(String []args){
        Main mymain = new Main();
        mymain.setVisible(true);
    }
}
