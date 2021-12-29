package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class creates the Inventory System Application.
 * This application allows user to organize Parts and Products data.
 *
 * @author Jonathan Mecham
 *
 */
public class InventorySystem extends Application {

    /**
     * This method starts the program.
     * @param primaryStage primaryStage
     * @throws Exception Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        primaryStage.setTitle("Inventory System");
        primaryStage.setScene(new Scene(root, 940, 400));
        primaryStage.show();
    }

    /**
     * This is the main method.
     * @param args args
     */
    public static void main(String[] args) {launch(args);
    }
}
