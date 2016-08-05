package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import persistence.EventPersistence;

/**
 * Controller for the main scene.
 */
public class MainSceneController  {

    private static MainSceneController mainSceneController;

    public static Map<Integer, EventController> eventsMap = new HashMap<>();

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
    public void buttonPressed(ActionEvent event) throws Exception {
        Stage stage;

        // Opens the pop up if the new event button is pressed
        if (event.getSource() == newEventButton) {
            stage = new Stage();
            URL url = getClass().getResource("/NewEventPopUp.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            AnchorPane pane = fxmlLoader.load();
            Scene scene = new Scene(pane);

            stage.setScene(scene);
            stage.setTitle("New Event");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(newEventButton.getScene().getWindow());
            stage.setResizable(false);
            stage.showAndWait();
        } else {
            stage=(Stage)newEventButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Used to add an event to the main scene.
     */
    public void addEventToScene(String eventTitle, LocalDate dueDate) {
        try {
            // Create the event
            Pane newPane = FXMLLoader.load(getClass().getResource("/Event.fxml"));
            EventController event = EventController.getInstance();
            Integer eventID = generateID();

            // Set event attributes
            event.setEventID(eventID);
            event.setTitle(eventTitle);
            event.setDueDate(dueDate);
            event.calculateTimeLeft();

            // Add event to scroll pane
            eventsMap.put(eventID, EventController.getInstance());
            contentBox.getChildren().addAll(newPane);
            newPane.prefWidthProperty().bind(scrollPane.widthProperty());
            scrollPane.setContent(contentBox);

            // Save the events to xml
            EventPersistence.saveEvents(eventsMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a unique ID number for an event in the events map.
     *
     * @return The ID number.
     */
    private Integer generateID(){
        int idNumber = 0;
        while (eventsMap.containsKey(idNumber)) {
            idNumber++;
        }
        return idNumber;
    }

    /**
     * Adds all events to a sorted map, the re-loads them into the scene.
     */
    public void refreshEvents() {
        Map<Integer, EventController> treeMap = new TreeMap<>();
        Collection<EventController> allEvents = eventsMap.values();

        for (EventController event : allEvents) {
            Integer value = event.getDateLengthValue();

            // Ensures duplicates are accounted for
            while (treeMap.containsKey(value)) {
                value++;
            }

            // Add to tree map
            treeMap.put(value, event);
        }

        eventsMap = new HashMap<>();
        contentBox = new VBox();
        for (EventController event : treeMap.values()) {
            addEventToScene(event.getEventTitle(), event.getDueDate());
        }
        scrollPane.setContent(contentBox);
    }

    /**
     * Used to remove a specific event from the events map.
     *
     * @param idNumber The ID number of the event.
     */
    public void removeEventFromMap(Integer idNumber) {
        eventsMap.remove(idNumber);
        EventPersistence.saveEvents(eventsMap);
    }
}
