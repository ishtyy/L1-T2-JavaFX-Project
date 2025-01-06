package client;

import data.database.Club;
import data.database.Database;
import data.database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClubHomeWindowController {

    @FXML
    private ImageView clubLogoImage;

    @FXML
    private Label clubNameFirstLine;

    @FXML
    private Label clubNameSecondLine;
    @FXML
    private Label clubNameSecondLine1;



    @FXML
    private Button buyPlayerButton;

    @FXML
    private VBox bodyVBox;

    @FXML
    private HBox topBarHBox;

    @FXML
    private TextField searchPlayerNameTextField;

    @FXML
    private Button searchPlayerNameButton;

    @FXML
    private Button resetPlayerNameButton;

    @FXML
    private HBox refreshRateHBox;

    @FXML
    private ChoiceBox<String> refreshRateChoiceBox;

    @FXML
    private MenuButton clubMenuButton;

    @FXML
    private MenuItem usernameMenuItem;

    @FXML
    private HBox clubListHBox;

    @FXML
    private VBox playerListVBox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private TreeView<CheckBox> filterTreeCountry;

    @FXML
    private TreeView<CheckBox> filterTreePosition;

    @FXML
    private TextField ageFromTextField;

    @FXML
    private TextField ageToTextField;

    @FXML
    private TextField heightFromTextField;

    @FXML
    private TextField salaryFromTextField;

    @FXML
    private TextField heightToTextField;

    @FXML
    private TextField salaryToTextField;

    @FXML
    private Button applyFiltersButton;

    @FXML
    private Button resetFiltersButton;

    @FXML
    private Button home;

    @FXML
    private Button add;

    @FXML
    private Button notificationButton;

    private Stage notificationStage;
    private Player player;
     Club club;
     Club notClub;
     String notClubName;
    String clubName;
    private String logoImgSource;
    private List<Player> playerListOnDisplay;
    private Client client;
    Database db;
    Database dbs;
    Server server;



    private boolean aBoolean = false;
   volatile private int refreshRate;


   @FXML
   void showHome(MouseEvent event){
       init(client,clubName);
   }
   void showHome(){
       init(client,clubName);
   }


    @FXML
    void searchPlayerByName(ActionEvent event) {
        String playerName = searchPlayerNameTextField.getText().trim();
        Database db = new Database();
        db.addPlayer(club.getPlayers());
        Player player = db.searchPlayerByName(playerName);
        db.setPlayerList(new ArrayList<>());
        if (player != null) {
            db.getPlayerList().add(player);
        }
        loadPlayerCards(db.getPlayerList(),clubName);
    }

    @FXML
    void resetPlayerNameTextField(ActionEvent event) {
        searchPlayerNameTextField.setText("");
        loadPlayerCards(club.getPlayers(),clubName);
    }

    @FXML
    void applyFilters(ActionEvent event) {
        Database db = new Database();
        db.addPlayer(notClub.getPlayers());
        applyFiltersCountry(db);
        applyFiltersPosition(db);
        applyFiltersAge(db);
        applyFiltersHeight(db);
        applyFiltersSalary(db);

        loadPlayerCards(db.getPlayerList(),clubName);
    }

    private void applyFiltersSalary(Database db) {
        long lo, hi;
        try {
            lo = Long.parseLong(salaryFromTextField.getText());
        } catch (Exception e) {
            lo = 0;
            salaryFromTextField.setText(String.valueOf(lo));
        }
        try {
            hi = Long.parseLong(salaryToTextField.getText());
        } catch (Exception e) {
            hi = club.getMaxSalaryPlayers().get(0).getSalary();
            salaryToTextField.setText(String.valueOf(hi));
        }
        db.setPlayerList(db.searchPlayerBySalary(lo, hi));
    }
    private void showBuyButton(){
        buyPlayerButton.setVisible(true);
    }
    private void hideBuyButton(){
        buyPlayerButton.setVisible(false);
    }
    private void showAddButton(){
        add.setVisible(true);
    }
    private void hideAddButton(){
        add.setVisible(false);
    }

    private void applyFiltersHeight(Database db) {
        double lo, hi;
        try {
            lo = Double.parseDouble(heightFromTextField.getText());
        } catch (Exception e) {
            lo = 0;
            heightFromTextField.setText(String.valueOf(lo));
        }
        try {
            hi = Double.parseDouble(heightToTextField.getText());
        } catch (Exception e) {
            hi = club.getMaxHeightPlayers().get(0).getHeight();
            heightToTextField.setText(String.valueOf(hi));
        }
        db.setPlayerList(db.searchPlayerByHeight(lo, hi));
    }

    private void applyFiltersAge(Database db) {
        int lo, hi;
        try {
            lo = Integer.parseInt(ageFromTextField.getText());
        } catch (Exception e) {
            lo = 0;
            ageFromTextField.setText(String.valueOf(lo));
        }
        try {
            hi = Integer.parseInt(ageToTextField.getText());
        } catch (Exception e) {
            hi = club.getMaxAgePlayers().get(0).getAge();
            ageToTextField.setText(String.valueOf(hi));
        }
        db.setPlayerList(db.searchPlayerByAge(lo, hi));
    }

    private void applyFiltersPosition(Database db) {
        for (TreeItem<CheckBox> item:
                filterTreePosition.getRoot().getChildren()) {
            if (!item.getValue().isSelected()) {
                // remove players playing at this position
                for (Player player:
                        db.searchPlayerByPosition(item.getValue().getText())) {
                    db.getPlayerList().remove(player);
                }
            }
        }
    }

    private void applyFiltersCountry(Database db) {
        for (TreeItem<CheckBox> item:
             filterTreeCountry.getRoot().getChildren()) {
            if (!item.getValue().isSelected()) {
                // remove players from this country
                for (Player player:
                        db.searchPlayerByCountry(item.getValue().getText())) {
                    db.getPlayerList().remove(player);
                }
            }
        }
    }

    @FXML
    void resetFilters(ActionEvent event) {
        // reset countries
        for (TreeItem<CheckBox> item:
                filterTreeCountry.getRoot().getChildren()) {
            if (!item.getValue().isSelected()) {
                item.getValue().setSelected(true);
            }
        }

        // reset position
        for (TreeItem<CheckBox> item:
                filterTreePosition.getRoot().getChildren()) {
            if (!item.getValue().isSelected()) {
                item.getValue().setSelected(true);
            }
        }

        // reset text fields
        ageFromTextField.setText("");
        ageToTextField.setText("");

        heightFromTextField.setText("");
        heightToTextField.setText("");

        salaryFromTextField.setText("");
        salaryToTextField.setText("");

        applyFilters(event);
    }

    public void init(Client client, Database db,Server server) {

        this.client = client;
        this.db = new Database();
        this.server = new Server(db);


        loadClubCards(db.getClubList());



        loadClubData(db);

        loadPlayerCards(club.getPlayers(),clubName);
        loadClubCards(db.getClubList());
        makeFilterTree();
        makeMenu();




    }
    public void init(Client client, String clubName) {
        this.client = client;
        this.clubName = clubName;
        this.notClubName = clubName;
        this.dbs = new Database();
        this.server = new Server(dbs);

        loadClubData();
        dbs.addPlayer(club.getPlayers());
        club=dbs.getClub(clubName);
        this.notClub = club;
        initClubInfo();
        loadPlayerCards(club.getPlayers(),clubName);


        loadClubCards(dbs.getClubList());



        loadClubData(dbs);


        loadClubCards(dbs.getClubList());

        makeFilterTree();

        makeMenu();
        if(clubName.equals("ANY")) {
            hideBuyButton();
            showAddButton();
        }else {
            showBuyButton();
            hideAddButton();
        }

        //makeRefreshRateChoiceBox();
    }

    @FXML
    void showTransferWindow(ActionEvent event) {
        if (buyPlayerButton.getText().equals("Buy Player")) {
            client.startRefreshThread(this);

            buyPlayerButton.setText("BACK");
        } else {
            client.interruptRefreshThread();

            buyPlayerButton.setText("Buy Player");
            loadPlayerCards(this.club.getPlayers(),clubName);
        }

    }

    void loadTransferWindow() {
        List<?> players = this.client.loadTransferList();
//        System.out.println(players);
        if (players != null) {
            List<Player> playerList = new ArrayList<>();
            for (Object e : players) {
                if (e instanceof Player ) {
                    playerList.add((Player) e);
                }
            }
            loadPlayerCards(playerList,clubName);
        }

    }





    private void makeMenu() {
        clubMenuButton.setText(clubName);


        // Optionally set a Tooltip for full message display


        ImageView imageView = new ImageView(new Image((club.getImgSource())));
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        clubMenuButton.setGraphic(imageView);

        usernameMenuItem.setText("Signed in as " + clubName);
    }




    private void makeFilterTree() {
        makeFilterTreeCountry();
        makeFilterTreePosition();
    }

    private void makeFilterTreePosition() {
        TreeItem<CheckBox> root;
        root = new TreeItem<>();
        root.setExpanded(true);

        this.club.getPositionList().forEach(e -> makeBranchFilterTree(e, root));

        filterTreePosition.setRoot(root);
        filterTreePosition.setShowRoot(false);
    }

    private void makeFilterTreeCountry() {
        TreeItem<CheckBox> root;
        root = new TreeItem<>();
        root.setExpanded(true);

        club.getCountryList().forEach(e -> makeBranchFilterTree(e, root));

        filterTreeCountry.setRoot(root);
        filterTreeCountry.setShowRoot(false);
    }

    private TreeItem<CheckBox> makeBranchFilterTree(String title, TreeItem<CheckBox> parent) {
        CheckBox checkBox = new CheckBox(title);
        checkBox.setSelected(true);
        TreeItem<CheckBox> item = new TreeItem<>(checkBox);
        parent.getChildren().add(item);
        return item;
    }

    private void initClubInfo(String s) {
         this.clubName = s;



    }
    private void initClubInfo() {
        String clubName = this.clubName.replace(' ', '_');
        logoImgSource = this.club.getImgSource();
        clubLogoImage.setImage(new Image(getClass().getResourceAsStream(logoImgSource)));
        String[] words = clubName.split("_");
        clubNameFirstLine.setText(words[0].toUpperCase());
        if (words.length > 1) {
            clubNameSecondLine.setText(words[1].toUpperCase());
            if(words.length > 2){
                clubNameSecondLine1.setText(words[2].toUpperCase());
            }else{
                clubNameSecondLine1.setText("");
            }
        } else {
            clubNameSecondLine.setText("");

            clubNameSecondLine1.setText("");
        }
    }


    // for listing players under any condition
    private void loadPlayerCards(List<Player> playerList, String clubName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/playerListView.fxml"));
            Parent root = fxmlLoader.load();


            PlayerListViewController playerListViewController = fxmlLoader.getController();
            playerListViewController.setClubHomeWindowController(this);
            playerListViewController.loadPlayerCards(playerList,clubName);

            playerListVBox.getChildren().clear();
            playerListVBox.getChildren().add(root);


           this.playerListOnDisplay = new ArrayList<>(playerList);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void loadClubCards(List<Club> clubList) {
        try {
//            for(Club club : clubList){
//                System.out.println(club.getImgSource());
//            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/clubListView.fxml"));
            Parent root = fxmlLoader.load();

            ClubListViewController clubListViewController = fxmlLoader.getController();
            clubListViewController.setClubHomeWindowController(this);
            clubListViewController.loadClubCards(clubList);

            clubListHBox.getChildren().clear();
            clubListHBox.getChildren().add(root);

            // this.playerListOnDisplay = new ArrayList<>(playerList);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadClubData(Database db) {
        this.club = db.getClub(clubName);
    }
    private void loadClubData() {
        this.club = client.loadClubFromServer(this.clubName);
    }

    @FXML
    void listMaxAgePlayers(ActionEvent event) {
        loadPlayerCards(club.getMaxAgePlayers(),clubName);
    }

    @FXML
    void listMaxHeightPlayers(ActionEvent event) {
        loadPlayerCards(club.getMaxHeightPlayers(),clubName);
    }

    @FXML
    void listMaxSalaryPlayers(ActionEvent event) {
        loadPlayerCards(club.getMaxSalaryPlayers(),clubName);
    }

    @FXML
    void showTotalYearlySalary(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Total Yearly Salary");
        a.setHeaderText(this.clubName);
        a.setContentText("Total yearly salary is " + String.format("%,.2f", this.club.getTotalYearlySalary()) + " USD");
        a.showAndWait();
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    @FXML
    void logoutClub(ActionEvent event) {
        client.logoutClub(this.clubName);
    }

    public void sellPlayer(String playerName, long playerPrice) {
        boolean b = client.sellPlayer(playerName, playerPrice);
        if (b) {
            this.club.removePlayer(playerName);
            makeFilterTree();
            loadPlayerCards(this.club.getPlayers(),clubName);
        }
    }

    public void buyPlayer(Player player) {
        boolean b = client.buyPlayer(player.getName(), this.clubName);
        if (b) {
            this.playerListOnDisplay.remove(player);
            dbs.getClub(player.getClub()).removePlayer(player.getName());
            club.removePlayer(player.getName());
            player.setInTransferList(false);
            player.setClub(this.clubName);
            this.club.addPlayer(player);
            List<Player> updatedPlayers = new ArrayList<>();
            for (Player p : dbs.playerList) {
                if (p.getName().equals(player.getName())) {
                    p.setClub(this.clubName);
                }
                updatedPlayers.add(p);
            }
            dbs.playerList.clear();
            dbs.playerList.addAll(updatedPlayers);
            FileHandler FH = new FileHandler("player.txt",dbs);
            FH.saveToFile();
            makeFilterTree();
            loadPlayerCards(this.playerListOnDisplay,clubName);
        }
    }
    public void cancelPlayer(Player player) {
        boolean b = client.cancelTransfer(player.getName());
        if (b) {
            this.playerListOnDisplay.remove(player);
            dbs.getClub(player.getClub()).removePlayer(player.getName());
            club.removePlayer(player.getName());
            player.setInTransferList(false);
            player.setClub(this.clubName);
            this.club.addPlayer(player);
            List<Player> updatedPlayers = new ArrayList<>();
            for (Player p : dbs.playerList) {
                if (p.getName().equals(player.getName())) {
                    p.setClub(this.clubName);
                }
                updatedPlayers.add(p);
            }
            dbs.playerList.clear();
            dbs.playerList.addAll(updatedPlayers);
            FileHandler FH = new FileHandler("player.txt",dbs);
            FH.saveToFile();
            makeFilterTree();
            loadPlayerCards(this.playerListOnDisplay,clubName);
        }
    }



    public void selectedClub(Club club) {


        System.out.println("Club     : " + club.getName());
//        for(Player p: dbs.playerList){
//            System.out.println(player);
//        }
        FileHandler FH = new FileHandler("player.txt",dbs);
            if(notClubName.equalsIgnoreCase(club.getName())){
                notClub= this.club;
                notClubName = clubName;
//            clubMenuButton.setText("ANY");


                FH.loadPlayers();
                this.club= dbs.getClub(clubName);
//            ImageView imageView = new ImageView(new Image((club.getImgSource())));
//            imageView.setFitHeight(25);
//            imageView.setPreserveRatio(true);
//            imageView.setSmooth(true);
//            imageView.setCache(true);
//            clubMenuButton.setGraphic(imageView);
//            clickedImage.setViewport(null);



                loadPlayerCards(this.club.getPlayers(),clubName);
                return;
            }




        FH.loadPlayers();
        notClubName = club.getName();
        notClub = dbs.getClub(notClubName);

//        ImageView imageView = new ImageView(new Image((club.getImgSource())));
//        imageView.setFitHeight(25);
//        imageView.setPreserveRatio(true);
//        imageView.setSmooth(true);
//        imageView.setCache(true);
//        clubMenuButton.setGraphic(imageView);

        loadPlayerCards(notClub.getPlayers(),clubName);
        System.out.println("Club Name: " + notClubName);

    }
    public void notifyPlayerSold(String text) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Player Sold");
        a.setHeaderText("Player Sold");
        a.setContentText(text);
        a.showAndWait();
       //server will send a message and we need to show that to alertbox

    }


    public void playerAdd(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/playerAdd.fxml"));
            Parent root = fxmlLoader.load();


            PlayerAddController playerAddController = fxmlLoader.getController();
            playerAddController.setClubHomeWindowController(this);
            playerAddController.set(client,dbs,server);

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Add Player");
            Scene scene = new Scene(root);
            window.setScene(scene);
            window.show();
            System.out.println("Modal window closed. Continue other tasks here.");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void loadNotification() {
        List<String> notifications = client.loadNotification(clubName);
        try {
            if (notificationStage == null || !notificationStage.isShowing()) {
                // Create a new stage if it doesn't exist or is closed
                notificationStage = new Stage();
                notificationStage.initModality(Modality.APPLICATION_MODAL);
                notificationStage.setTitle("Notification");

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/notifications.fxml"));
                Parent root = fxmlLoader.load();

                NotificationsController notificationController = fxmlLoader.getController();
                notificationController.setClient(client);
                notificationController.loadNotifications(notifications);
                notificationStage.setOnCloseRequest(event -> {
                    client.interruptNotificationThread(); // Ensure thread interruption
                    notificationStage = null; // Set stage to null for future recreation
                });


                Scene scene = new Scene(root);
                notificationStage.setScene(scene);
                notificationStage.show();
            } else {
                // Update the existing stage's content
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/notifications.fxml"));
                Parent root = fxmlLoader.load();

                NotificationsController notificationController = fxmlLoader.getController();
                notificationController.setClient(client);
                notificationController.loadNotifications(notifications);
                notificationStage.setOnCloseRequest(event -> {
                    client.interruptNotificationThread(); // Ensure thread interruption
                    notificationStage = null; // Set stage to null for future recreation
                });


                Scene scene = new Scene(root);
                notificationStage.setScene(scene);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void showNotification(ActionEvent event) {
        client.startNotificationThread(this);




    }
}
