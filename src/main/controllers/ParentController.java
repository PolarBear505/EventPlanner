package main.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adam on 24/03/2016.
 */
public class ParentController {

    public static MainSceneController mainScene;

    public static EventController individualEvent;

    public static EventController eventToRemove;

    public static NewEventController newEvent;

    public static ArrayList<EventController> eventsArray = new ArrayList<>();

    public static Boolean addEvent = false;

    public static Map<Integer, EventController> eventsDict = new HashMap<>();

    public static int generateID(){
        int idNumber = 0;
        while (eventsDict.containsKey(idNumber)) {
            idNumber++;
        }
        return idNumber;
    }
}
