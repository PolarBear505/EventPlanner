package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Controller class for the new event pop up.
 */
public class EventPopUpController {

    @FXML private TextField titleInputField;
    @FXML private Button acceptButton;
    @FXML private DatePicker dateInput;
    @FXML private Text emptyFieldText;

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
            LocalDate eventDate = dateInput.getValue();

            // Checks if values have been entered in the pop up fields
            if (!eventName.equals("") && eventDate != null) {
                // Closes the pop up
                stage=(Stage)acceptButton.getScene().getWindow();
                stage.close();

                // Tells the main scene controller to add the event
                MainSceneController.getInstance().addEvent(eventName, eventDate);
            } else {
                emptyFieldText.setText("Title and date required!");
            }
        } else {
            stage=(Stage)acceptButton.getScene().getWindow();
            stage.close();
        }
    }
}
