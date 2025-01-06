package client;

import data.database.Player;
import java.io.*;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
public class FileManager {
    public static void readFile(List players){
        File playerFile=new File("player.txt");
        if(playerFile.exists()){
            try {
                Scanner textScn=new Scanner(playerFile);
                while(textScn.hasNextLine()){
                    String line=textScn.nextLine();
                    String[] lineArray=line.split(",");
                    String name=lineArray[0].trim();
                    String country=lineArray[1].trim();
                    int age=Integer.parseInt(lineArray[2]);
                    double height=Double.parseDouble(lineArray[3]);
                    String club=lineArray[4].trim();
                    String pos=lineArray[5].trim();
                    int num=Integer.parseInt(lineArray[6]);
                    long salary=Long.parseLong(lineArray[7]);
                    Player temp=new Player(name,country,age,height,club,pos,num,salary);
                    players.add(temp);
                    // System.out.println(temp);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Printing the players
//           for(Object a:players){
//               System.out.println(a);
//           }
        }
        else {
            System.out.println("File not found");
        }
    }

    public static void writeFile(List<Player> players){

        try {
            FileWriter myWriter = new FileWriter("player.txt");
            for(Player a:players){
                myWriter.write(a.getName()+","+a.getCountry()+","+a.getAge()+","+a.getHeight()+","+a.getClub()+","+a.getPosition()+","+a.getNumber()+","+a.getSalary()+"\n");

            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
