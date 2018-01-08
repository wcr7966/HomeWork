package nju.java.io;

import org.junit.Test;
import java.io.File;

import static org.junit.Assert.*;

public class ReadTest {

    @Test
    public void setReadFile(){
        File f = new File("1.txt");
        Read.setReadFile(f);
        assertEquals(Read.getReadFile(), f);
    }

}