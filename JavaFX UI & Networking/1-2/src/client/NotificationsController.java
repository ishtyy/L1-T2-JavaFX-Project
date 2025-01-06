package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.List;

public class NotificationsController {

    @FXML
    private Button backButton;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;
    Client client;



    public void loadNotifications(List<String> notifications) {
        try {
            int row = 0;
            int col = 0;
            if (notifications == null || notifications.isEmpty()) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/notificationCard.fxml"));

                Parent card = fxmlLoader.load();

                NotificationCardController notificationCardController = fxmlLoader.getController();
                notificationCardController.setData("No notifications");

                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);
                scrollPane.setMinViewportHeight(450);
                scrollPane.setFitToHeight(true);

                gridPane.add(card, col,row);

                //set grid width
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);
            }
           else {
                for (String notification : notifications) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/views/notificationCard.fxml"));

                    Parent card = fxmlLoader.load();

                    NotificationCardController notificationCardController = fxmlLoader.getController();
                    notificationCardController.setData(notification);

                    scrollPane.setFitToWidth(true);
                    scrollPane.setFitToHeight(true);
                    scrollPane.setMinViewportHeight(450);
                    scrollPane.setFitToHeight(true);


                    gridPane.add(card, col, row++);

                    //set grid width
                    gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);
                    GridPane.setMargin(card, new Insets(10));


                }
           }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void setClient(Client client) {
        this.client = client;
    }
    @FXML
    void closeWindow(ActionEvent event) {
        client.interruptNotificationThread();
        backButton.getScene().getWindow().hide();
    }
}
