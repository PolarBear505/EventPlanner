package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by Adam on 24/03/2016.
 */
public class EventController extends ParentController{

    @FXML
    private TextField timeLeftField;

    public EventController() {
        individualEvent = this;
    }

    public TextField getTimeLeftField() {
        return timeLeftField;
    }

}
