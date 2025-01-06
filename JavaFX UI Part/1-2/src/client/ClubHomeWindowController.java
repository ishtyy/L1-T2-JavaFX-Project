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
    private Label clubBudgetLabel;

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
    private HBox bottomBarHBox;
    private Player player;
     Club club;
    String clubName;
    private String logoImgSource;
    private List<Player> playerListOnDisplay;
    private Client client;
    Database db;
    Server server;



    private boolean aBoolean = false;



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
        loadPlayerCards(db.getPlayerList());
    }

    @FXML
    void resetPlayerNameTextField(ActionEvent event) {
        searchPlayerNameTextField.setText("");
        loadPlayerCards(club.getPlayers());
    }

    @FXML
    void applyFilters(ActionEvent event) {
        Database db = new Database();
        db.addPlayer(club.getPlayers());
        applyFiltersCountry(db);
        applyFiltersPosition(db);
        applyFiltersAge(db);
        applyFiltersHeight(db);
        applyFiltersSalary(db);

        loadPlayerCards(db.getPlayerList());
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

        db = new Database();
        server = new Server(db);
        this.db = db;
        this.server = server;

        initClubInfo("ANY");
        loadClubData(db);

        loadPlayerCards(club.getPlayers());
        loadClubCards(db.getClubList());
        makeFilterTree();
        makeMenu();




    }
    public void refresh (){
        loadClubData(db);
        loadPlayerCards(club.getPlayers());
        loadClubCards(db.getClubList());
        makeFilterTree();
        makeMenu();
    }


    private void makeMenu() {
        clubMenuButton.setText(clubName);

        ImageView imageView = new ImageView(new Image((club.getImgSource())));
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        clubMenuButton.setGraphic(imageView);

        //usernameMenuItem.setText("Signed in as " + clubName);
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

    // for listing players under any condition
    private void loadPlayerCards(List<Player> playerList) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/playerListView.fxml"));
            Parent root = fxmlLoader.load();

            PlayerListViewController playerListViewController = fxmlLoader.getController();
            playerListViewController.setClubHomeWindowController(this);
            playerListViewController.loadPlayerCards(playerList);

            playerListVBox.getChildren().clear();
            playerListVBox.getChildren().add(root);

           // this.playerListOnDisplay = new ArrayList<>(playerList);

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

    @FXML
    void listMaxAgePlayers(ActionEvent event) {
        loadPlayerCards(club.getMaxAgePlayers());
    }

    @FXML
    void listMaxHeightPlayers(ActionEvent event) {
        loadPlayerCards(club.getMaxHeightPlayers());
    }

    @FXML
    void listMaxSalaryPlayers(ActionEvent event) {
        loadPlayerCards(club.getMaxSalaryPlayers());
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
    public void selectedClub(Club club) {




        if(clubName.equalsIgnoreCase(club.getName())){
            clubName = "ANY";
            clubMenuButton.setText("ANY");
            club= db.getClub(clubName);
            ImageView imageView = new ImageView(new Image((club.getImgSource())));
            imageView.setFitHeight(25);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);
            clubMenuButton.setGraphic(imageView);
//            clickedImage.setViewport(null);



            loadPlayerCards(club.getPlayers());
            return;
        }

        //i want to create a border for the selected imageview

        clubName = club.getName();
        club = db.getClub(clubName);
        clubMenuButton.setText(clubName);
        ImageView imageView = new ImageView(new Image((club.getImgSource())));
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        clubMenuButton.setGraphic(imageView);

        loadPlayerCards(club.getPlayers());
        System.out.println("Club Name: " + clubName);

    }


    public void playerAdd(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/playerAdd.fxml"));
            Parent root = fxmlLoader.load();


            PlayerAddController playerAddController = fxmlLoader.getController();
            playerAddController.setClubHomeWindowController(this);
            playerAddController.set(client,db,server);

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
}
