package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Adam on 24/03/2016.
 */
public class MainSceneController extends ParentController {

    @FXML
    ScrollPane scrollPane;

    @FXML
     VBox contentBox = new VBox();

    @FXML
    Button newEventButton;

    public MainSceneController() {
        mainScene = this;
    }

    @FXML
    public void newEventPressed(ActionEvent event) throws Exception{

        Stage stage;
        Parent root;

        if(event.getSource()==newEventButton)
        {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/resources/NewEvent.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("New Event");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(newEventButton.getScene().getWindow());
            stage.showAndWait();
        }
        else
        {
            stage=(Stage)newEventButton.getScene().getWindow();
            stage.close();
        }

        if (addEvent) {
            Pane newPane = FXMLLoader.load(getClass().getResource("/resources/Event.fxml"));
            eventsArray.add(individualEvent);
            contentBox.getChildren().addAll(newPane);
            newPane.prefWidthProperty().bind(scrollPane.widthProperty());
            scrollPane.setContent(contentBox);
            individualEvent.setTitle(newEvent.getEventName());
            individualEvent.getTimeLeftField().appendText("5 Hours");
            addEvent = false;
        }
    }
}
