package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Created by Adam on 25/03/2016.
 */
public class NewEventController extends ParentController {

    public String eventName = "";

    public LocalDate eventDate;

    @FXML
    TextField titleInputField;

    @FXML
    Button acceptButton;

    @FXML
    DatePicker dateInput;

    public NewEventController() {
        newEvent = this;
    }

    public String getEventTitle() {
        return eventName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    @FXML
    public void buttonPressed(ActionEvent event) throws Exception{
        Stage stage;
        if (event.getSource().equals(acceptButton)) {
            addEvent = true;
            this.eventName = titleInputField.getText();
            this.eventDate= dateInput.getValue();
            stage=(Stage)acceptButton.getScene().getWindow();
            stage.close();
        } else {
            stage=(Stage)acceptButton.getScene().getWindow();
            stage.close();
        }
    }
}
