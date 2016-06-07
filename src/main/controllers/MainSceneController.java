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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the main scene.
 */
public class MainSceneController  {

    public static Map<Integer, EventController> eventsDict = new HashMap<>();
    private static MainSceneController mainSceneController;
    private Boolean addEvent;

    @FXML private ScrollPane scrollPane;
    @FXML private VBox contentBox = new VBox();
    @FXML private Button newEventButton;

    /**
     * Constructor for the main scene.
     */
    public MainSceneController() {
        mainSceneController = this;
    }

    /**
     * Used to get the instance of the main scene controller.
     *
     * @return The main scene controller.
     */
    public static MainSceneController getInstance() {
        return mainSceneController;
    }

    /**
     * Handles the new event button being pressed.
     *
     * @param event The button event.
     * @throws Exception The fxml cant be loaded.
     */
    @FXML
    public void newEventPressed(ActionEvent event) throws Exception {

        Stage stage;
        Parent root;

        if(event.getSource()==newEventButton)
        {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/resources/NewEventPopUp.fxml"));
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
            EventController.getInstance().setEventID(eventID);
            eventsDict.put(eventID, EventController.getInstance());
            contentBox.getChildren().addAll(newPane);
            newPane.prefWidthProperty().bind(scrollPane.widthProperty());
            scrollPane.setContent(contentBox);
            EventController.getInstance().setTitle(NewEventPopUpController.getInstance().getEventTitle());
            EventController.getInstance().setDueDate(NewEventPopUpController.getInstance().getEventDate());
            EventController.getInstance().calculateTimeLeft();
            addEvent = false;
        }
    }

    /**
     * Function used to refresh events.
     *
     * @throws Exception The fxml can't be loaded.
     */
    public void refreshEvents() throws Exception {
        Collection<EventController> allEvents = eventsDict.values();
        eventsDict = new HashMap<>();
        contentBox = new VBox();
        for (EventController event : allEvents) {
            Pane newPane = FXMLLoader.load(getClass().getResource("/resources/Event.fxml"));
            int eventID = generateID();
            EventController.getInstance().setEventID(eventID);
            eventsDict.put(eventID, EventController.getInstance());
            contentBox.getChildren().addAll(newPane);
            newPane.prefWidthProperty().bind(scrollPane.widthProperty());
            EventController.getInstance().setTitle(event.getEventTitle());
            EventController.getInstance().setDueDate(event.getDueDate());
            addEvent = false;
        }
        scrollPane.setContent(contentBox);
    }

    public void allowAddEvent() {
        addEvent = true;
    }

    public static int generateID(){
        int idNumber = 0;
        while (eventsDict.containsKey(idNumber)) {
            idNumber++;
        }
        return idNumber;
    }

    public Map getEventsDict() {
        return eventsDict;
    }
}
