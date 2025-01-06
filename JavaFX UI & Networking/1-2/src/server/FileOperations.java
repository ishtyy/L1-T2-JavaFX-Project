package server;

import data.database.Database;
import data.database.Player;

import java.io.*;
import java.util.List;

/*
    FileOperations class handles reading from and writing to a file.
 */

public class FileOperations {
    private String inputFileName;
    private String outputFileName;

    public FileOperations() {
        inputFileName = outputFileName = "player.txt";
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public void readFromFile(Database db) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            int i =0 ;
            while ((line = br.readLine()) != null) {


                try {
                    String[] data = line.split(",");
                    if (data.length<7 || data.length>8) {
                        System.out.println("Invalid data format in line: " + line);
                        continue;
                    }

                    Player player = new Player(data);
                    db.addPlayer(player);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + inputFileName);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void writeToFile(List<Player> players) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName));

        for (Player player : players) {
            bw.write(player.getName() + ",");
            bw.write(player.getCountry() + ",");
            bw.write(player.getAge() + ",");
            bw.write(player.getHeight() + ",");
            bw.write(player.getClub() + ",");
            bw.write(player.getPosition() + ",");
            bw.write(player.getNumber() + ",");
            bw.write(player.getSalary() + "");
            bw.write("\n");
        }
        bw.close();
    }
}
