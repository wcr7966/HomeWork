package nju.java.creatures.huluteam;

import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

import static org.junit.Assert.*;

public class HuluTest {

    @Test
    public void testDead(){
        Hulu hulu = new Hulu(0, 0, null, 0);
        assert (!hulu.isDead());
        hulu.setDead();
        assert (hulu.isDead());
    }

    @Test
    public void testName(){
        Hulu hulu = new Hulu(0, 0, null, 0);
        assertEquals(hulu.toString(), "大娃");
        Hulu hulu2 = new Hulu(0, 0, null, 1);
        assertEquals(hulu2.toString(), "二娃");
    }

    @Test
    public void testImage(){
        Hulu hulu = new Hulu(0, 0, null, 0);
        URL url = this.getClass().getClassLoader().getResource("大娃.png");
        ImageIcon imageIcon = new ImageIcon(url);
        hulu.setImg(imageIcon.getImage());
        assertEquals(hulu.getImg(), imageIcon.getImage());
    }

}