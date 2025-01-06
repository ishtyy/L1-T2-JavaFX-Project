package client;

import data.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class Client extends Application {

    public static Stage stage;
    public Server server;
    public Database db;






    @Override
    public  void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        showClubHomePage();
    }


    private void showClubHomePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/clubHomeWindow.fxml"));

        Parent root = fxmlLoader.load();

        ClubHomeWindowController controller = fxmlLoader.getController();
        controller.init(this,db,server);

        Scene scene = new Scene(root);



        stage.setTitle("IPL Player Management System");
        stage.setScene(scene);
        stage.setX(10);
        stage.setY(10);
        stage.show();
    }

    public  void changeScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("/views/clubHomeWindow.fxml"));

        stage.setScene(new Scene(fxmlLoader.load()));




    }





    public static void main(String[] args) {
        launch(args);
    }
}
