package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Adam on 24/03/2016.
 */
public class EventController extends ParentController{

    @FXML
    private Text eventTitle;

    @FXML
    private Button deleteButton;

    @FXML
    private Text dateDueField;

    @FXML
    private Text timeLeftField;

    private int eventID;


    public EventController() {
        individualEvent = this;
    }

    public String getEventTitle() {
        return eventTitle.getText();
    }

    public void setTitle(String text) {
        eventTitle.setText(text);
    }

    public void setEventID(int ID) {
        eventID = ID;
    }

    public void setDueDate(LocalDate date) {
        dateDueField.setText(date.toString());
    }

    public LocalDate getDueDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        LocalDate date = LocalDate.parse(dateDueField.getText(), formatter);
        return date;
    }

    public void buttonPressed(ActionEvent event) throws Exception {
        if(event.getSource() == deleteButton) {
            eventsDict.remove(eventID);
            mainScene.refreshEvents();
        }
    }
}
