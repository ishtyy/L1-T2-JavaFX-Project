package client;

import data.database.Club;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ClubCardController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView clubImage;
    private ClubHomeWindowController clubHomeWindowController;
    private Club club;
    String clubName;
    Boolean isSelected = false;
    public static ClubCardController prev = null;

    @FXML
    void mouseEntered(MouseEvent event) {
        // i want to change the background color of the anchorpane
        anchorPane.setStyle("-fx-background-color: #1B132E;-fx-border-color: #E4080A;");;
       // anchorPane.setStyle("-fx-border-color: #E4080A");

    }
    @FXML
    void mouseExited(MouseEvent event) {
        // i want to change the background color of the anchorpane
       if(!isSelected){
           anchorPane.setStyle("-fx-background-color:   #0D0B46;-fx-border-color: #C63FEC;");

       }
    }

    @FXML
    void selectedClub(MouseEvent event) {
        // If there was a previously selected card, reset its style
       if(prev!= null){
           System.out.println(prev.clubName);
           System.out.println("test");

       }

        if (prev != null && prev.anchorPane != anchorPane) {
            prev.anchorPane.setStyle("-fx-background-color: #0D0B46; -fx-border-color: #C63FEC;");
        }

        // Check if the current card is already selected
        if (isSelected && prev.anchorPane == anchorPane) {

            isSelected = false;
            clubHomeWindowController.selectedClub(club);
            anchorPane.setStyle("-fx-background-color: #0D0B46; -fx-border-color: #C63FEC;");

        } else {

            isSelected = true;
            anchorPane.setStyle("-fx-background-color: #1B132E; -fx-border-color: #E4080A;");
            clubHomeWindowController.selectedClub(club);
        }


        prev = this;
    }


    void setData(Club club){
        this.club = club;
        clubName = club.getName();
        System.out.println(club.getImgSource());

       // use try catch
        try {
            clubImage.setImage(new Image(club.getImgSource()));
        }catch (Exception e){

            clubImage.setImage(new Image("/images/logo/unknown.png"));
            System.out.println(e.getMessage());
        }


   }

    public void setClubHomeWindowController(ClubHomeWindowController clubHomeWindowController) {
        this.clubHomeWindowController = clubHomeWindowController;
    }
}
