package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.Optional;

/**
 * Controller class for an event.
 */
public class EventController {

    private static EventController eventController;

    private Integer eventID;
    private LocalDate dueDate;
    private Integer dateLengthValue;

    @FXML private Text eventTitle;
    @FXML private Button deleteButton;
    @FXML private Text dateDueField;
    @FXML private Text timeLeftField;

    /**
     * Constructor for the class.
     */
    public EventController() {
        eventController = this;
    }

    /**
     * Static method used to retrieve the instance of the class.
     *
     * @return The class instance.
     */
    public static EventController getInstance() {
        return eventController;
    }

    /**
     * Handles a button being pressed on the event.
     *
     * @param event The action event.
     */
    @FXML
    public void buttonPressed(ActionEvent event) {
        if(event.getSource() == deleteButton) {
            // Create the alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Event");
            alert.setHeaderText("Are you sure you want to delete the event?");
            alert.setContentText("This cannot be undone");

            // Create the button types
            ButtonType confirmButtonType = new ButtonType("Yes");
            ButtonType cancelButtonType = new ButtonType("Cancel");
            alert.getButtonTypes().setAll(confirmButtonType, cancelButtonType);

            // Set default buttons
            Button yesButton = (Button) alert.getDialogPane().lookupButton( confirmButtonType );
            yesButton.setDefaultButton( false );
            Button cancelButton = (Button) alert.getDialogPane().lookupButton( cancelButtonType );
            cancelButton.setDefaultButton( true );

            // Display dialog and check result
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == confirmButtonType){
                MainSceneController.getInstance().removeEventFromMap(eventID);
                MainSceneController.getInstance().refreshEvents();
            }
        }
    }

    /**
     * Calculates the time left and adds it to the time left text field.
     */
    public void calculateTimeLeft() {
        //The two local date times;
        LocalDateTime dueDateTime = dueDate.atStartOfDay();
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (dueDateTime.isAfter(currentDateTime)) {
            //The two local dates
            LocalDate dueDate = dueDateTime.toLocalDate();
            LocalDate currentDate = currentDateTime.toLocalDate();

            //The two local times
            LocalTime dueTime = dueDateTime.toLocalTime();
            LocalTime currentTime = currentDateTime.toLocalTime();

            //Days, months and years between the two dates
            Period period = Period.between(currentDate, dueDate);
            Integer daysLeft = period.getDays();
            Integer monthsLeft = period.getMonths();
            Integer yearsLeft = period.getYears();

            //Time between two times
            Integer hours = dueTime.getHour() - currentTime.getHour();
            Integer minutes = dueTime.getMinute() - currentTime.getMinute();

            if (hours < 0) {
                hours = 24 + hours;
                daysLeft--;
            }

            if (minutes < 0) {
                minutes = 60 + minutes;
                hours--;
            }

            //Prepares the time left string
            String timeLeftString = "";
            if (yearsLeft > 0) timeLeftString = timeLeftString.concat(yearsLeft + " Years ");
            if (monthsLeft > 0) timeLeftString = timeLeftString.concat(monthsLeft + " Months ");
            if (daysLeft > 0) timeLeftString = timeLeftString.concat(daysLeft + " Days ");
            if (hours > 0) timeLeftString = timeLeftString.concat(hours + " Hours ");
            if (minutes > 0) timeLeftString = timeLeftString.concat(minutes + " Minutes");
            timeLeftField.setText(timeLeftString);

            // Creates an integer value of time left
            Integer timeLeftInteger = 0;
            timeLeftInteger += minutes * 1000;
            timeLeftInteger += hours * 60 * 1000;
            timeLeftInteger += daysLeft * 24 * 60 * 1000;
            timeLeftInteger += monthsLeft * 28 * 24 * 60 * 1000;
            timeLeftInteger += yearsLeft * 12 * 28 * 24 * 60 * 1000;
            dateLengthValue = timeLeftInteger;
        }
    }

    /**
     * Used to get the event title.
     *
     * @return The event title.
     */
    public String getEventTitle() {
        return eventTitle.getText();
    }

    /**
     * Used to set the event title.
     *
     * @param text The event title.
     */
    public void setTitle(String text) {
        eventTitle.setText(text);
    }

    /**
     * Used to set the event ID.
     *
     * @param eventId The event ID.
     */
    public void setEventID(int eventId) {
        eventID = eventId;
    }

    /**
     * Used to set the Due date.
     *
     * @param date The due date.
     */
    public void setDueDate(LocalDate date) {
        dueDate = date;
        Integer day = date.getDayOfMonth();
        Integer month = date.getMonthValue();
        Integer year = date.getYear();
        String theDate = "";
        theDate = theDate.concat(day + "/" + month + "/" + year);
        dateDueField.setText(theDate);
    }

    /**
     * Used to get the due date.
     *
     * @return The due date.
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Used to get the date length value.
     *
     * @return The date length value.
     */
    public Integer getDateLengthValue() {
        return dateLengthValue;
    }
}
