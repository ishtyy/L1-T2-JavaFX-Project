package client;

import data.database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerCardController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView clubImage;
    @FXML
    private ImageView playerImage;

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label playerPositionLabel;

    @FXML
    private Button playerDetailsButton;

    @FXML
    private Button playerSellButton;

    @FXML
    private Label playerPriceLabel;

    private Player player;
    private ClubHomeWindowController clubHomeWindowController;
    @FXML
    void mouseEntered(MouseEvent event) {
        // i want to change the background color of the anchorpane
        anchorPane.setStyle("-fx-background-color: #16121D; -fx-background-radius: 20;-fx-border-color: #C63FEC; -fx-border-radius: 20");



    }
    @FXML
    void mouseExited(MouseEvent event) {
        // i want to change the background color of the anchorpane
        {
//            anchorPane.setStyle("-fx-background-radius: 20");
//            anchorPane.setStyle("-fx-background-color: #3F1777");
            anchorPane.setStyle("-fx-background-radius: 20 ; -fx-background-color: #292736;-fx-border-color: #FFFFFF; -fx-border-radius: 20");



        }
    }



    @FXML
    void showPlayerDetails(ActionEvent event) {

        try {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(player.getName());

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/playerDetailsWindow.fxml"));
            Parent root = fxmlLoader.load();

            PlayerDetailsWindowController playerDetails = fxmlLoader.getController();
            playerDetails.setData(player);


            Scene scene = new Scene(root);
            window.setScene(scene);
            window.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void selectPlayer(MouseEvent event) {
//        System.out.println(this.player.getName());
//        playerSellButton.setVisible(true);
    }

    public void setData(Player player) {
        this.player = player;
        playerNameLabel.setText(player.getName());
        playerPositionLabel.setText(player.getPosition());

      try{
            playerImage.setImage(new Image(getClass().getResourceAsStream("/images/player/" + player.getName().replace(' ', '_') + ".png")));

        } catch (Exception e) {
          playerImage.setImage(new Image(getClass().getResourceAsStream("/images/player/unknown.png")));
      }
      try {
          clubImage.setImage(new Image(getClass().getResourceAsStream("/images/logo/" + player.getClub().replace(' ', '_') + ".png")));

      }catch(Exception e){
            clubImage.setImage(new Image(getClass().getResourceAsStream("/images/logo/unknown.png")));
      }

    }




    public ClubHomeWindowController getClubHomeWindowController() {
        return clubHomeWindowController;
    }

    public void setClubHomeWindowController(ClubHomeWindowController clubHomeWindowController) {
        this.clubHomeWindowController = clubHomeWindowController;
    }
}
