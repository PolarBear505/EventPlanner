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

    private static EventPopUpController eventPopUpController;

    @FXML private TextField titleInputField;
    @FXML private Button acceptButton;
    @FXML private DatePicker dateInput;
    @FXML private Text emptyFieldText;
    @FXML private Text titleText;
    @FXML private TextField firstTimeInput;
    @FXML private TextField secondTimeInput;
    @FXML private ChoiceBox timeTypeDropDown;

    private Integer givenEventID = null;

    /**
     * Constructor for class.
     */
    public EventPopUpController() {
        eventPopUpController = this;
    }

    /**
     * Used to get the instance of the event pop up controller.
     *
     * @return The event pop up controller.
     */
    public static EventPopUpController getInstance() {
        return eventPopUpController;
    }

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
            Integer hour = 0;
            Integer minute = 0;
            Boolean firstTimeBool = checkValidTime(firstTimeInput.getText(), 12);
            Boolean secondTimeBool = checkValidTime(secondTimeInput.getText(), 59);
            String timeType = timeTypeDropDown.getSelectionModel().getSelectedItem().toString();

            // Calculate the hour and minute values
            if (firstTimeBool && secondTimeBool) {
                hour = Integer.parseInt(firstTimeInput.getText());
                minute = Integer.parseInt(secondTimeInput.getText());

                if (timeType.equals("AM") && hour == 12) hour = 0;
                if (timeType.equals("PM")) hour += 12;
                if (hour == 24) hour = 12;
            }
            String dateString = String.format("%02d", hour) + ":" + String.format("%02d", minute);
            LocalTime eventTime = LocalTime.parse(dateString);
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
                if (givenEventID != null) {
                    MainSceneController.getInstance().removeEventFromMap(givenEventID);
                }
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

        return (value >= 0 && value <= maxValue);
    }

    /**
     * Used to set the title text on the pop up.
     *
     * @param title The title text.
     */
    public void setWindowTitle(String title) {
        titleText.setText(title);
    }

    /**
     * Used to set the title input field text.
     *
     * @param text The input field text.
     */
    public void setTitleFieldText(String text) {
        titleInputField.setText(text);
    }

    /**
     * Used to set the date field.
     *
     * @param date The date.
     */
    public void setDateField(LocalDate date) {
        dateInput.setValue(date);
    }

    /**
     * Used to set the time field.
     *
     * @param time The time.
     */
    @SuppressWarnings("Duplicates")
    public void setTimeFields(LocalTime time) {
        Integer hour = time.getHour();
        Integer minute = time.getMinute();
        Integer timeType = 0;

        if (hour >= 12) {
            hour -= 12;
            timeType = 1;
        }
        if (hour == 0) hour = 12;

        firstTimeInput.setText(String.format("%02d", hour));
        secondTimeInput.setText(String.format("%02d", minute));
        timeTypeDropDown.getSelectionModel().select(timeType.intValue());
    }

    /**
     * Used to set the event od of an event that is being edited.
     *
     * @param id The id.
     */
    public void setEventID(Integer id) {
        givenEventID = id;
    }
}
