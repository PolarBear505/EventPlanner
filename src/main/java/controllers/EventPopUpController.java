package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Controller class for the new event pop up.
 */
public class EventPopUpController {

    @FXML private TextField titleInputField;
    @FXML private Button acceptButton;
    @FXML private DatePicker dateInput;
    @FXML private Text emptyFieldText;
    @FXML private TextField firstTimeInput;
    @FXML private TextField secondTimeInput;
    @FXML private ChoiceBox timeTypeDropDown;

    /**
     * Handles any button being pressed on the pop up.
     *
     * @param event The action event.
     */
    @FXML
    public void buttonPressed(ActionEvent event) {
        Stage stage;
        if (event.getSource().equals(acceptButton)) {
            // Sorts the date data from the pop up
            String eventName = titleInputField.getText();
            LocalDate eventDate = dateInput.getValue();
            LocalDate currentDate = LocalDate.now();

            // Sorts the time data from the pop up
            Integer firstTimeValue = 0;
            Integer secondTimeValue = 0;
            Boolean firstTimeBool = checkValidTime(firstTimeInput.getText(), 12);
            Boolean secondTimeBool = checkValidTime(secondTimeInput.getText(), 59);
            String timeType = timeTypeDropDown.getSelectionModel().getSelectedItem().toString();
            if (firstTimeBool && secondTimeBool) {
                firstTimeValue = Integer.parseInt(firstTimeInput.getText());
                secondTimeValue = Integer.parseInt(secondTimeInput.getText());

                if (timeType.equals("PM")) firstTimeValue += 12;
            }
            LocalTime eventTime = LocalTime.of(firstTimeValue, secondTimeValue);
            LocalTime currentTime = LocalTime.now();

            // Checks if values have been entered in the pop up fields
            if (eventName.equals("")) {
                emptyFieldText.setText("Valid title required!");
            } else if (eventDate == null) {
                emptyFieldText.setText("Valid date required!");
            } else if (!firstTimeBool || !secondTimeBool){
                emptyFieldText.setText("Valid time required!");
            } else if (!eventDate.isAfter(currentDate) && !eventDate.equals(currentDate)) {
                emptyFieldText.setText("Date must be after current date!");
            } else if (!eventTime.isAfter(currentTime) && eventDate.equals(currentDate)){
                emptyFieldText.setText("Time must be after current time!");
            } else {
                // Closes the pop up
                stage=(Stage)acceptButton.getScene().getWindow();
                stage.close();

                // Tells the main scene controller to add the event
                MainSceneController.getInstance().addEventToScene(eventName, eventDate, eventTime);
                MainSceneController.getInstance().refreshEvents();
            }
        } else {
            stage=(Stage)acceptButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Returns true if the number given is a valid time number.
     *
     * @param givenNumber the given time string.
     * @param maxValue The max value the string can be.
     * @return A Boolean.
     */
    private Boolean checkValidTime(String givenNumber, Integer maxValue) {
        Integer value;

        // Check if the string is a valid number
        try {
            value = Integer.parseInt(givenNumber);
        } catch (Exception e) {
            return false;
        }

        return (value > 0 && value <= maxValue);
    }
}
