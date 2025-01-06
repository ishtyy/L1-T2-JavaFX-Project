package client;

import data.database.Database;
import data.database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PlayerAddController {

    @FXML
    private MenuItem batsman;
    @FXML
    private MenuItem bowler;
    @FXML
    private MenuItem allrounder;
    @FXML
    private MenuItem wicketkeeper;
    @FXML
    private TextField playerName;

    @FXML
    private TextField playerAge;

    @FXML
    private TextField playerHeight;

    @FXML
    private TextField playerCountry;

    @FXML
    private TextField playerClub;

    @FXML
    private TextField playerNumber;

    @FXML
    private TextField playerPosition;

    @FXML
    private TextField playerSalary;

    @FXML
    private ImageView playerPreview;

    @FXML
    private ImageView clubPreview;

    @FXML
    private ImageView flagPreview;

     Database db;
    Client client;
    private ClubHomeWindowController clubHomeWindowController;
    private Server server;

    // Validation method to check if a text field is empty
    private boolean isFieldEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    // Method to show an alert dialog
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
    void set(Client client,Database db,Server server) {
        this.client = client;
        this.db = db;
        this.server = server;
    }
    // Method to add player data
    @FXML
    void addPlayer(ActionEvent event) {
        try {
            if (isFieldEmpty(playerName.getText()) ||
                    isFieldEmpty(playerClub.getText()) ||
                    isFieldEmpty(playerCountry.getText()) ||
                    isFieldEmpty(playerHeight.getText()) ||
                    isFieldEmpty(playerAge.getText()) ||
                    isFieldEmpty(playerPosition.getText()) ||
                    isFieldEmpty(playerNumber.getText()) ||
                    isFieldEmpty(playerSalary.getText())) {
                showAlert(Alert.AlertType.ERROR, "All fields are required. Please fill in all values.");
                return;
            }

            double height = Double.parseDouble(playerHeight.getText());
            int age = Integer.parseInt(playerAge.getText());
            int number = Integer.parseInt(playerNumber.getText());
            double salary = Double.parseDouble(playerSalary.getText());

            if (height <= 0 || age <= 0 || number <= 0 || salary <= 0) {
                showAlert(Alert.AlertType.ERROR, "Numeric values must be positive.");
                return;
            }

            Player newPlayer = new Player(
                    playerName.getText(),
                    playerCountry.getText(),
                    age,
                    height,
                    playerPosition.getText(),
                    playerClub.getText(),
                    number,
                    (long) salary
            );

            db.addPlayer(newPlayer);
            FileManager.writeFile(db.getPlayerList());
            FileManager.readFile(db.playerList);

            showAlert(Alert.AlertType.INFORMATION, "Player Added Successfully");

            // Refresh the ClubHomeWindowController


            // Close the Player Add window
            Stage currentStage = (Stage) playerName.getScene().getWindow();
            currentStage.close();
            //client.changeScene();
            clubHomeWindowController.init(client,db,server);


        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Please enter valid numeric values for height, age, number, and salary.");
        }
    }

    // Method to add player image
    @FXML
    void addPlayerImage(ActionEvent event) {
        try {
            // Ensure the player directory exists or create it
            Path playerDirectory = Paths.get("src/images/player");
            if (!Files.exists(playerDirectory)) {
                Files.createDirectories(playerDirectory);
            }

            // Configure file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Player Image");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
            fileChooser.setInitialDirectory(playerDirectory.toFile()); // Set initial directory

            // Open file chooser
            File source = fileChooser.showOpenDialog(new Stage());
            if (source == null || isFieldEmpty(playerName.getText())) {
                showAlert(Alert.AlertType.ERROR, "Please enter the player name first or choose a valid image.");
                return;
            }

            String playerNameValue = playerName.getText().replace(" ", "_");
            Path destinationPath = Paths.get(playerDirectory.toString(), playerNameValue + ".png");

            if (Files.exists(destinationPath)) {
                playerPreview.setImage(new Image(Files.newInputStream(destinationPath)));
                showAlert(Alert.AlertType.CONFIRMATION, "Player Image Already Exists!");
            } else {
                Files.copy(source.toPath(), destinationPath);
                playerPreview.setImage(new Image(Files.newInputStream(destinationPath)));
                showAlert(Alert.AlertType.CONFIRMATION, "Player Image Added!");
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "An error occurred while saving the image: " + e.getMessage());
        }
    }

    // Method to add club image
    @FXML
    void addClubImage(ActionEvent event) {
        try {
            // Ensure the club directory exists or create it
            Path clubDirectory = Paths.get("src/images/logo");
            if (!Files.exists(clubDirectory)) {
                Files.createDirectories(clubDirectory);
            }

            // Configure file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Club Image");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
            fileChooser.setInitialDirectory(clubDirectory.toFile()); // Set initial directory

            // Open file chooser
            File source = fileChooser.showOpenDialog(new Stage());
            if (source == null || isFieldEmpty(playerClub.getText())) {
                showAlert(Alert.AlertType.ERROR, "Please enter the club name first or choose a valid image.");
                return;
            }

            String clubNameValue = playerClub.getText().replace(" ", "_");
            Path destinationPath = Paths.get(clubDirectory.toString(), clubNameValue + ".png");

            if (Files.exists(destinationPath)) {
                clubPreview.setImage(new Image(Files.newInputStream(destinationPath)));
                showAlert(Alert.AlertType.CONFIRMATION, "Club Image Already Exists!");
            } else {
                Files.copy(source.toPath(), destinationPath);
                clubPreview.setImage(new Image(Files.newInputStream(destinationPath)));
                showAlert(Alert.AlertType.CONFIRMATION, "Club Image Added!");
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "An error occurred while saving the image: " + e.getMessage());
        }
    }

    @FXML
    void click(ActionEvent event) {
        if(event.getSource() == batsman){
            playerPosition.setText("Batsman");
        }
        if(event.getSource() == bowler){
            playerPosition.setText("Bowler");
        }
        if(event.getSource() == allrounder){
            playerPosition.setText("Allrounder");
        }
        if(event.getSource() == wicketkeeper){
            playerPosition.setText("Wicketkeeper");
        }


    }



    // Setter for ClubHomeWindowController
    public void setClubHomeWindowController(ClubHomeWindowController clubHomeWindowController) {
        this.clubHomeWindowController = clubHomeWindowController;
    }
}
