package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Created by Adam on 24/03/2016.
 */
public class EventController extends ParentController{

    @FXML
    private TextField timeLeftField;

    @FXML
    private Text eventTitle;


    public EventController() {
        individualEvent = this;
    }

    public TextField getTimeLeftField() {
        return timeLeftField;
    }

    public void setTitle(String text) {
        eventTitle.setText(text);
    }

}
