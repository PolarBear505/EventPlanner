package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Adam on 25/03/2016.
 */
public class NewEventController extends ParentController {

    public String eventName = "";

    @FXML
    TextField titleInputField;

    @FXML
    Button acceptButton;

    public NewEventController() {
        newEvent = this;
    }

    public String getEventName() {
        return eventName;
    }

    @FXML
    public void buttonPressed(ActionEvent event) throws Exception{
        Stage stage;
        if (event.getSource().equals(acceptButton)) {
            addEvent = true;
            this.eventName = titleInputField.getText().toString();
            stage=(Stage)acceptButton.getScene().getWindow();
            stage.close();
        } else {
            stage=(Stage)acceptButton.getScene().getWindow();
            stage.close();
        }
    }
}
