package client;

import data.database.Database;

public class Server {

    static FileHandler FH;
    public Server(Database db) {
        initialize(db);
    }




    public static void initialize(Database db) {
        System.out.println("Initializing PlayerList");
        FH = new FileHandler("player.txt", db);
        FH.loadPlayers();
        System.out.println("Done");
       // db.getPlayerList().forEach(System.out::println);
       // System.out.println("sesh");



    }
}
