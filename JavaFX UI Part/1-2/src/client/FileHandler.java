package client;

import data.database.Country;
import data.database.Database;
import data.database.Player;

import java.io.*;

public class FileHandler {
    Database db;
    final private  String fileName;



    public FileHandler(String fileName, Database db) {
        this.fileName = fileName;
        this.db = db;
    }


    public void loadPlayers() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
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
            System.err.println("File not found: " + fileName);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }


    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Country country : db.getCountryList()) {

                for (Player player : country.getPlayerList()) {
                    writer.write("" + player);
                    writer.newLine();
                }
            }
            writer.close();
            System.out.println("Database successfully saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error while saving to file: " + e.getMessage());
        }
    }

}
