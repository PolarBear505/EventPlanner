package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Created by Adam on 24/03/2016.
 */
public class EventController extends ParentController{

    @FXML
    private TextField timeLeftField;

    @FXML
    private Text eventTitle;

    @FXML
    private Button deleteButton;

    private int eventID;


    public EventController() {
        individualEvent = this;
    }

    public TextField getTimeLeftField() {
        return timeLeftField;
    }

    public String getEventTitle() {
        return eventTitle.getText();
    }

    public void setTitle(String text) {
        eventTitle.setText(text);
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int ID) {
        eventID = ID;
    }

    public void buttonPressed(ActionEvent event) throws Exception {
        if(event.getSource() == deleteButton) {
            eventsDict.remove(eventID);
            mainScene.refreshEvents();
        }
    }
}
