package client;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;


public class NotificationCardController {




    @FXML
    private Label messageLabel;





    public void setData(String message) {
        messageLabel.setText(message);

        // Enable text wrapping
        messageLabel.setWrapText(true);

        // Optionally set a Tooltip for full message display
        Tooltip tooltip = new Tooltip(message);
        messageLabel.setTooltip(tooltip);

        // Adjust label width dynamically if needed
        //messageLabel.setPrefWidth(300); // You can change this value as per your layout
    }

}
