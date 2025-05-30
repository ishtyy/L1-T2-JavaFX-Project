package client;

import data.database.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.List;

public class PlayerListViewController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    private ClubHomeWindowController clubHomeWindowController;



    // for listing players under any condition
    public void loadPlayerCards(List<Player> playerList,String clubName) {
        try {
            int row = 0;
            int col = 0;
            for (Player player : playerList) {
                System.out.println(player);

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/playerCard.fxml"));

                Parent card = fxmlLoader.load();

                PlayerCardController playerCardController = fxmlLoader.getController();
                playerCardController.setData(player,clubName);
                playerCardController.setClubHomeWindowController(this.clubHomeWindowController);

                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);
                scrollPane.setMinViewportHeight(530);
                 scrollPane.setFitToHeight(true);


                if(col %2==0) {
                    col = 0;
                    row++;
                }

                gridPane.add(card, col++,row);
               // card.getStyleClass().add(player.getClub().replace(' ', '_'));

                //set grid width
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);
//                double width = 464;
//                double width = scrollPane.getWidth();
                double width = 464;
                gridPane.setMinWidth(width);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);

                //set grid height
                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_COMPUTED_SIZE);


                GridPane.setMargin(card, new Insets(10));

//                card.toFront();
            }
//            gridPane.toFront();
//            scrollPane.toFront();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClubHomeWindowController getClubHomeWindowController() {
        return clubHomeWindowController;
    }

    public void setClubHomeWindowController(ClubHomeWindowController clubHomeWindowController) {
        this.clubHomeWindowController = clubHomeWindowController;
    }
}
