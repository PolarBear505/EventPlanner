import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import persistence.EventPersistence;

import java.net.URL;

/**
 * The main class for the application.
 */
public class App extends Application {

    /**
     * The main entry of the application.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates and displays the Main Scene.
     *
     * @param primaryStage Primary stage for the scene
     * @throws Exception When loading an invalid fxml file
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Window sizes
        Integer width = 470;
        Integer height = 600;

        // Set up the scene
        URL sceneUrl = getClass().getResource("/MainScene.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(sceneUrl);
        AnchorPane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);

        // Set up and show the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Event Planner");
        primaryStage.setHeight(height);
        primaryStage.setWidth(width);
        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);
        primaryStage.setMaxWidth(width);
        primaryStage.show();

        EventPersistence.loadEvents();
    }
}
