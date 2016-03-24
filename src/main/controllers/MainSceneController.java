package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Created by Adam on 24/03/2016.
 */
public class MainSceneController extends ParentController {

    @FXML
    ScrollPane scrollPane;

    @FXML
     VBox contentBox = new VBox();

    public MainSceneController() {
        mainScene = this;
    }

    @FXML
    public void newEventPressed(ActionEvent event) throws Exception{
        Pane newPane = FXMLLoader.load(getClass().getResource("/resources/Event.fxml"));
        eventsArray.add(individualEvent);
        contentBox.getChildren().addAll(newPane);
        newPane.prefWidthProperty().bind(scrollPane.widthProperty());
        scrollPane.setContent(contentBox);
        individualEvent.getTimeLeftField().appendText("5 Hours");
    }
}
