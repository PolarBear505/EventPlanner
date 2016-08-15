package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
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
    private LocalTime dueTime;
    private Integer dateLengthValue;
    private Boolean eventDone = false;

    @FXML private Text eventTitle;
    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private Button doneButton;
    @FXML private Text dateDueField;
    @FXML private Text timeLeftField;
    @FXML private Rectangle eventRectangle;

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
     * The initialize method called after the class is constructed.
     */
    @FXML
    public void initialize() {
        // Create and set the tool tips
        Tooltip doneButtonTooltip = new Tooltip("Mark event as complete");
        Tooltip editButtonTooltip = new Tooltip("Edit event");
        Tooltip deleteButtonTooltip = new Tooltip("Delete event");

        doneButton.setTooltip(doneButtonTooltip);
        editButton.setTooltip(editButtonTooltip);
        deleteButton.setTooltip(deleteButtonTooltip);
    }

    /**
     * Handles a button being pressed on the event.
     *
     * @param event The action event.
     */
    @FXML
    public void buttonPressed(ActionEvent event) throws Exception {
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
        } else if (event.getSource() == editButton) {
            Stage stage = new Stage();
            URL url = getClass().getResource("/NewEventPopUp.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            AnchorPane pane = fxmlLoader.load();
            Scene scene = new Scene(pane);

            stage.setScene(scene);
            stage.setTitle("Edit Event");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(editButton.getScene().getWindow());
            stage.setResizable(false);

            EventPopUpController.getInstance().setWindowTitle("Edit Event");
            EventPopUpController.getInstance().setTitleFieldText(eventTitle.getText());
            EventPopUpController.getInstance().setDateField(dueDate);
            EventPopUpController.getInstance().setTimeFields(dueTime);
            EventPopUpController.getInstance().setEventID(eventID);

            stage.showAndWait();
        } else if (event.getSource() == doneButton) {
            eventDone = true;
            calculateTimeLeft();
            MainSceneController.getInstance().refreshEvents();
        }
    }

    /**
     * Calculates the time left and adds it to the time left text field.
     */
    public void calculateTimeLeft() {
        // The current date and time
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        // Boolean checks before calculating time
        Boolean dateCheck = (dueDate.isAfter(currentDate) || dueDate.equals(currentDate));
        Boolean timeCheck = true;
        if (!dueTime.isAfter(currentTime) && dueDate.equals(currentDate)) {
            timeCheck = false;
        }

        // Calculates time if checks succeed
        if (dateCheck && timeCheck) {
            // Days, months and years between the two dates
            Period period = Period.between(currentDate, dueDate);
            Integer daysLeft = period.getDays();
            Integer monthsLeft = period.getMonths();
            Integer yearsLeft = period.getYears();

            // Hours and minutes between two times
            Integer hours = dueTime.getHour() - currentTime.getHour();
            Integer minutes = dueTime.getMinute() - currentTime.getMinute();

            if (minutes < 0) {
                minutes = 60 + minutes;
                hours--;
            }

            if (hours < 0) {
                hours = 24 + hours;
                daysLeft--;
            }

            //Prepares the time left string
            String timeLeftString = "";
            if (yearsLeft > 0) timeLeftString = timeLeftString.concat(yearsLeft + " Years ");
            if (monthsLeft > 0) timeLeftString = timeLeftString.concat(monthsLeft + " Months ");
            if (daysLeft > 0) timeLeftString = timeLeftString.concat(daysLeft + " Days ");
            if (hours > 0) timeLeftString = timeLeftString.concat(hours + " Hours ");
            if (minutes > 0) timeLeftString = timeLeftString.concat(minutes + " Minutes");

            // Creates an integer value of time left
            Integer timeLeftInteger = minutes + (hours * 60);
            timeLeftInteger += daysLeft * 24 * 60;
            timeLeftInteger += monthsLeft * 28 * 24 * 60;
            timeLeftInteger += yearsLeft * 12 * 28 * 24 * 60;
            timeLeftInteger += timeLeftInteger * 1000;

            // Sets the two variables
            if (!eventDone) {
                dateLengthValue = timeLeftInteger;
                timeLeftField.setText(timeLeftString);
            } else {
                dateLengthValue = 1;
                timeLeftField.setText("Event Completed");
            }
        } else {
            // Handles no time left
            dateLengthValue = 1;
            timeLeftField.setText("Time Expired");
        }

        Paint greenColourFill = Paint.valueOf("#b9f8b6");
        Paint redColourFill = Paint.valueOf("#dd9d9d");
        Paint yellowColourFill = Paint.valueOf("#fcffaa");

        Integer noTimeLeftValue = 1000;
        Integer threeDaysLeftValue = 4320000;

        // Change the event colours based on the time left
        if (dateLengthValue <= noTimeLeftValue) {
            eventRectangle.setFill(greenColourFill);
        } else if (dateLengthValue <= threeDaysLeftValue) {
            eventRectangle.setFill(redColourFill);
        } else {
            eventRectangle.setFill(yellowColourFill);
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
     * Used to set the due date and time.
     *
     * @param date The due date.
     * @param time The due time.
     */
    public void setDueDateAndTime(LocalDate date, LocalTime time) {
        dueDate = date;
        dueTime = time;
        String fieldText = "";

        // Date fields
        Integer day = date.getDayOfMonth();
        Integer month = date.getMonthValue();
        Integer year = date.getYear();
        fieldText = fieldText.concat(day + "/" + month + "/" + year);

        // Time fields
        Integer hour = time.getHour();
        Integer minute = time.getMinute();
        String timeType = "AM";
        if (hour >= 12) {
            hour -= 12;
            timeType = "PM";
        }
        if (hour == 0) hour = 12;

        fieldText = fieldText.concat(" at " + hour
                + ":" + String.format("%02d", minute) + " " + timeType);

        dateDueField.setText(fieldText);
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
     * Used to get the due time.
     *
     * @return The due time.
     */
    public LocalTime getDueTime() {
        return dueTime;
    }

    /**
     * Used to get the date length value.
     *
     * @return The date length value.
     */
    public Integer getDateLengthValue() {
        return dateLengthValue;
    }

    /**
     * Used to check if the event is done.
     *
     * @return A boolean value.
     */
    public Boolean eventDone() {
        return eventDone;
    }

    /**
     * Used to set the event done field.
     *
     * @param value A boolean value.
     */
    public void setEventDone(Boolean value) {
        eventDone = value;
    }
}
