package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Controller class for the new event pop up.
 */
public class NewEventPopUpController {

    private static NewEventPopUpController newEventPopUpController;
    public String eventName = "";
    public LocalDate eventDate;

    @FXML private TextField titleInputField;
    @FXML private Button acceptButton;
    @FXML private DatePicker dateInput;

    /**
     * Constructor for the new event pop up.
     */
    public NewEventPopUpController() {
        newEventPopUpController = this;
    }

    /**
     * Used to get the instance of the new event popup controller.
     *
     * @return The new event popup controller.
     */
    public static NewEventPopUpController getInstance() {
        return newEventPopUpController;
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
            MainSceneController.getInstance().allowAddEvent();
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
