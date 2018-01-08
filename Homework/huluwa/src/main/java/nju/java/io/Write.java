package nju.java.io;
import nju.java.Status;
import nju.java.creatures.Creature;
import nju.java.creatures.huluteam.HuluTeam;
import nju.java.creatures.monsterteam.MonsterTeam;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.FileNameMap;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Write {
    private static File writeFile;
    private static FileWriter fileWriter;
    private static BufferedWriter bufferedWriter;
    private static Date date = null;


    public static void setWriteFile(File file) {
        writeFile = file;
    }

    public static synchronized void write(ArrayList<HuluTeam> huluTeams, ArrayList<MonsterTeam> monsterTeams, ArrayList<Creature> deadCreatures) throws IOException {
        if (writeFile == null) {
            date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

            String str1 = "." + File.separator + simpleDateFormat.format(date) + ".txt";
            writeFile = new File(str1);
        }

        try {
            fileWriter = new FileWriter(writeFile, true);
            bufferedWriter = new BufferedWriter(fileWriter);

            ArrayList<String> str = new ArrayList<String>();
            for (Creature i : huluTeams) {
                str.add(i.toString() + " " + i.getPosition().getX() + " " + i.getPosition().getY() + " alive");
            }
            for (Creature i : monsterTeams) {
                str.add(i.toString() + " " + i.getPosition().getX() + " " + i.getPosition().getY() + " alive");
            }
            for (Creature i : deadCreatures) {
                //System.out.println("dead");
                str.add(i.toString() + " " + i.getPosition().getX() + " " + i.getPosition().getY() + " dead");
            }

            for (String s : str) {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("IO异常");
            e.printStackTrace();
            throw e;
        }

    }

    public static void changeName(){

        JFileChooser jfc=new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("文件类型(.txt)", ".txt");
        jfc.setFileFilter(filter);
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        jfc.setSelectedFile(new File(date.toString()));
        int flag = jfc.showDialog(new JLabel(), "选择");
        File file=jfc.getSelectedFile();
        if(file.isDirectory()){
            System.out.println("文件夹:"+file.getAbsolutePath());
        }else if(file.isFile()){
            System.out.println("文件:"+file.getAbsolutePath());
        }
        System.out.println(jfc.getSelectedFile().getName());

        if(flag == jfc.APPROVE_OPTION){
            if(!file.getName().endsWith(".txt"))
                file = new File(jfc.getCurrentDirectory(), file.getName() + ".txt");
            if(writeFile.renameTo(file))
                System.out.println("修改成功");
        }
    }

}
