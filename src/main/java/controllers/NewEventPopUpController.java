package controllers;

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

    @FXML private TextField titleInputField;
    @FXML private Button acceptButton;
    @FXML private DatePicker dateInput;

    /**
     * Handles any button being pressed on the pop up.
     *
     * @param event The action event.
     */
    @FXML
    public void buttonPressed(ActionEvent event) {
        Stage stage;
        if (event.getSource().equals(acceptButton)) {
            // Gets the data from the pop up
            String eventName = titleInputField.getText();
            LocalDate eventDate= dateInput.getValue();

            // Closes the pop up
            stage=(Stage)acceptButton.getScene().getWindow();
            stage.close();

            // Tells the main scene controller to add the event
            MainSceneController.getInstance().addEvent(eventName, eventDate);
        } else {
            stage=(Stage)acceptButton.getScene().getWindow();
            stage.close();
        }
    }
}
