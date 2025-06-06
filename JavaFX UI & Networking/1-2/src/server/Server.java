package server;

import data.database.Database;
import data.database.Player;
import util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
    volatile Database db;
    private FileOperations fileOperations;
    volatile private List<Player> transferPlayerList;
    volatile private HashMap<String, ClientInfo> clientMap;
    volatile private HashMap<String,List<String>> notificationMap;

    public static void main(String[] args) {
        int port = 45045;
        new Server(port);
    }

    public Server(int port) {
        clientMap = new HashMap<>();
        transferPlayerList = new ArrayList<>();
        notificationMap = new HashMap<>();
        try {
            loadDatabase();
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
//                System.out.println("client connected");
                serve(clientSocket);
            }
        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("this is exception while connecting");
        }
    }
    synchronized public void addNotification(String clubName, String notification){
        System.out.println(notification);
        if(notificationMap.containsKey(clubName)){
            notificationMap.get(clubName).add(notification);
        }
        else{
            List<String> list = new ArrayList<>();
            list.add(notification);
            notificationMap.put(clubName, list);
        }
    }

    synchronized public List<Player> getTransferPlayerList() {
        return transferPlayerList;
    }

    private void loadDatabase() throws IOException {
        db = new Database();
        fileOperations = new FileOperations();
        fileOperations.readFromFile(db);
    }

    private void serve(Socket socket) throws IOException {
        NetworkUtil networkUtil = new NetworkUtil(socket);
//        System.out.println("networkutil for server created");
        new ThreadServer(networkUtil, this);
    }

    synchronized public boolean sellPlayer(String playerName, String newClubName) {
        boolean b = false;
        try {
            String clubName = db.searchPlayerByName(playerName).getClub();
            //send an alert message to the club that the player has been sold to the newClubName


            Player player = db.searchPlayerByName(playerName);
            if (player.isInTransferList()) {
                transferPlayerList.remove(player);
                player.setInTransferList(false);
                player.setClub(newClubName);
                b = true;

                Player player1 = db.searchPlayerByName(playerName);
//                System.out.println(player1.getName() + " " + player1.getClub());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
    synchronized public List<String> getNotification(String clubName){
        if(notificationMap.containsKey(clubName)){
            return notificationMap.get(clubName);
        }else{
            return null;
        }
    }

    synchronized public boolean addToTransferWindow(String playerName, long playerPrice) {
        boolean b = false;
        try {
            // player should not be null
            //db.removePlayerFromClub(playerName);
            Player player = db.searchPlayerByName(playerName);
            player.setPrice(playerPrice);
            player.setInTransferList(true);
            transferPlayerList.add(player);
//            System.out.println(playerName + " " + player.getPrice());
//            System.out.println(transferPlayerList);
            b = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    // returns true if registration is successful, false otherwise
    synchronized public boolean registerClub(String clubName, String password, NetworkUtil networkUtil) {
//        System.out.println("reg req to server: username " + clubName + " password " + password);
        for (String clientName:
             clientMap.keySet()) {
            if (clientName.equalsIgnoreCase(clubName)) {
//                System.out.println("reg failure");
                return false;
            }
        }
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setClubName(clubName);
        clientInfo.setPassword(password);
        clientInfo.setNetworkUtil(networkUtil);
        clientInfo.setLoggedIn(false);
        clientMap.put(clubName, clientInfo);
//        System.out.println("reg success");
        return true;
    }

    // returns true if login is successful, false otherwise
    synchronized public boolean loginClub(String username, String password) {
//        System.out.println("login req to server: username " + username + " password " + password);
        if (clientMap.containsKey(username) && clientMap.get(username).getPassword().equals(password)
            && !clientMap.get(username).isLoggedIn()) {
            // if the username exists, there must have been set a password during registration
            // so, password cannot be null
            clientMap.get(username).setLoggedIn(true);
//            System.out.println("login successful");
            return true;
        }
//        System.out.println("login unsuccessful");
        return false;
    }

    synchronized public boolean changePassword(String username, String oldPassword, String newPassword) {
        if (clientMap.containsKey(username) && clientMap.get(username).getPassword().equals(oldPassword)
            && clientMap.get(username).isLoggedIn()) {
            // if the username exists, there must have been set a password during registration
            // so, password cannot be null
            clientMap.get(username).setPassword(newPassword);
            return true;
        }
        return false;
    }

    synchronized public boolean logoutClub(String username) {
        if (clientMap.containsKey(username) && clientMap.get(username).isLoggedIn()) {
            clientMap.get(username).setLoggedIn(false);
            return true;
        }
        return false;
    }

    synchronized public List<String> sendClubList() {
        List<String> clubList = new ArrayList<>();
        db.getClubList().forEach(e -> clubList.add(e.getName()));
        return clubList;
    }

    synchronized public boolean cancelTransfer(String playerName) {
        boolean b = false;
        try {
            Player player = db.searchPlayerByName(playerName);
            if (player.isInTransferList()) {
                transferPlayerList.remove(player);
                player.setInTransferList(false);
                b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
}
