package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Created by Adam on 24/03/2016.
 */
public class MainSceneController extends ParentController {

    public MainSceneController() {
        mainScene = this;
    }

    @FXML
    public void newEventPressed(ActionEvent event) {
        System.out.println("It Worked");
    }
}
