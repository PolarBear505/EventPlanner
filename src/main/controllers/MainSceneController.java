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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
    public void newEventPressed(ActionEvent event) throws Exception {

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
            int eventID = generateID();
            individualEvent.setEventID(eventID);
            eventsDict.put(eventID, individualEvent);
            contentBox.getChildren().addAll(newPane);
            newPane.prefWidthProperty().bind(scrollPane.widthProperty());
            scrollPane.setContent(contentBox);
            individualEvent.setTitle(newEvent.getEventTitle());
            individualEvent.setDueDate(newEvent.getEventDate());
            individualEvent.calculateTimeLeft();
            addEvent = false;
        }
    }

    public void refreshEvents() throws Exception {
        Collection<EventController> allEvents = eventsDict.values();
        eventsDict = new HashMap<>();
        contentBox = new VBox();
        for (EventController event : allEvents) {
            Pane newPane = FXMLLoader.load(getClass().getResource("/resources/Event.fxml"));
            int eventID = generateID();
            individualEvent.setEventID(eventID);
            eventsDict.put(eventID, individualEvent);
            contentBox.getChildren().addAll(newPane);
            newPane.prefWidthProperty().bind(scrollPane.widthProperty());
            individualEvent.setTitle(event.getEventTitle());
            individualEvent.setDueDate(event.getDueDate());
            addEvent = false;
        }
        scrollPane.setContent(contentBox);
    }
}
