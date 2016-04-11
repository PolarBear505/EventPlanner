package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

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

    private LocalDate dueDate;


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
        dueDate = date;
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();
        String theDate = "";
        theDate = theDate.concat(day + "/" + month + "/" + year);
        dateDueField.setText(theDate);
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void buttonPressed(ActionEvent event) throws Exception {
        if(event.getSource() == deleteButton) {
            eventsDict.remove(eventID);
            mainScene.refreshEvents();
        }
    }

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

            //Days/Months between the two dates
            Period period = Period.between(currentDate, dueDate);
            int daysLeft = period.getDays();
            int monthsLeft = period.getMonths();
            int yearsLeft = period.getYears();


            //Time between two times
            int hours = dueTime.getHour() - currentTime.getHour();
            int minutes = dueTime.getMinute() - currentTime.getMinute();

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
        }
    }
}
