package keyers.dg.calc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by Sajiel
 */

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        // Load FXML file for component placement
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KeyersCalcGUI.fxml"));
        primaryStage.setTitle("Keyers Calculator");
        Scene scene = new Scene(loader.load(),410,180);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Make the gui draggable
        Parent root = loader.getRoot();
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
