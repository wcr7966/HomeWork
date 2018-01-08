package nju.java.position;

import nju.java.creatures.huluteam.*;

import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {


    @Test
    public void getXY(){
        Position position = new Position(10, 15);
        assertEquals(position.getX(), 10);
        assertEquals(position.getY(), 15);
    }


    @Test
    public void setXY(){
        Position position = new Position(0, 0);
        position.setX(5);
        position.setY(5);
        assertEquals(position.getX(), 5);
        assertEquals(position.getY(), 5);
    }

    @Test
    public void setHolder(){
        Hulu hulu = new Hulu(0, 0, null, 0);
        Position position = new Position(0, 0);
        assertEquals(position.getHolder(), null);
        position.setHolder(hulu);
        assertEquals(position.getHolder(), hulu);
        }
}