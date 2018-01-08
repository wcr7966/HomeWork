package nju.java.io;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Read {
    private static File readFile;
    private static FileReader fileReader;
    private static BufferedReader bufferedReader;

    public static File getReadFile() {
        return readFile;
    }

    public static void setReadFile(File f) {
        readFile = f;
        try {
            fileReader = new FileReader(readFile);
            bufferedReader = new BufferedReader(fileReader);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static synchronized String read(){
        if(readFile == null)
            return null;

        String str = null;
        try {
            str = bufferedReader.readLine();
            if (str == null) {
                bufferedReader.close();
                fileReader.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }
}
