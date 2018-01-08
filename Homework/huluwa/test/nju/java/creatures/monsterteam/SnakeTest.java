package nju.java.creatures.monsterteam;

import nju.java.creatures.huluteam.Hulu;
import org.junit.Test;

import javax.swing.*;
import java.net.URL;

import static org.junit.Assert.*;

public class SnakeTest {

    @Test
    public void testDead(){
        Snake snake = new Snake(0, 0, null);
        assert (!snake.isDead());
        snake.setDead();
        assert (snake.isDead());
    }

    @Test
    public void testName(){
        Snake snake = new Snake(0, 0, null);
        assertEquals(snake.toString(), "蛇精");
    }

    @Test
    public void testImage(){
        Snake snake = new Snake(0, 0, null);
        URL url = this.getClass().getClassLoader().getResource("蛇精.png");
        ImageIcon imageIcon = new ImageIcon(url);
        snake.setImg(imageIcon.getImage());
        assertEquals(snake.getImg(), imageIcon.getImage());
    }

    @Test
    public void getHurt(){
        Snake snake = new Snake(0, 0, null);
        assertEquals(snake.getHurt(), 80);
    }
}