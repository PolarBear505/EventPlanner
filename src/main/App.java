package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.applet.Main;

/**
 * Created by Adam on 23/03/2016.
 */
public class App extends Application {

    private static App app;
    private EventUtil eventUtil;

    public App() {
        app = this;
    }

    public static App getInstance() {
        return app;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane pane = FXMLLoader.load(Main.class.getResource("/resources/MainScene.fxml"));
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("EventPlanner");
        primaryStage.setHeight(600);
        primaryStage.setWidth(400);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(400);
        primaryStage.show();

        eventUtil = new EventUtil();
    }

    public EventUtil getEventUtil() {
        return eventUtil;
    }
}
