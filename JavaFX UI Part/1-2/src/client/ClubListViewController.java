package client;

import data.database.Club;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.List;

public class ClubListViewController {

    private ClubHomeWindowController clubHomeWindowController;
    @FXML
    private GridPane gridPane;
    @FXML
    private ScrollPane scrollPane;


    public void setClubHomeWindowController(ClubHomeWindowController clubHomeWindowController) {
        this.clubHomeWindowController = clubHomeWindowController;
    }


    public void loadClubCards(List<Club> clubList) {
        try {
            int row = 0;
            int col = 0;
            for(Club club : clubList){
                System.out.println(club.getImgSource());
            }
            for (Club club : clubList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/clubCard.fxml"));

                Parent card = fxmlLoader.load();

                ClubCardController clubCardController = fxmlLoader.getController();
                clubCardController.setData(club);
                clubCardController.setClubHomeWindowController(this.clubHomeWindowController);

                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);
                scrollPane.setMinViewportHeight(150);
                scrollPane.setFitToHeight(true);


                gridPane.add(card, col++, row);
                // card.getStyleClass().add(player.getClub().replace(' ', '_'));

                //set grid width
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);
//                double width = 464;
//                double width = scrollPane.getWidth();
                //double width = 464;
//                gridPane.setMinWidth(width);
//                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
//                gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);

                //set grid height
                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_COMPUTED_SIZE);


                GridPane.setMargin(card, new Insets(10));
                // i want to set border of gridpane
                 //gridPane.setStyle("-fx-border-color: red; -fx-border-width: 1;");


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
}
