package persistence;

import controllers.EventController;
import controllers.MainSceneController;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Map;

/**
 * Class used to save and store the event data.
 */
public final class EventPersistence {

    /**
     * Used to save events to an xml document.
     *
     * @param eventsMap A map of all events.
     */
    public static void saveEvents(Map<Integer, EventController> eventsMap) {

        File file = new File("/Saved Events");

        if (!(file.exists() && file.isDirectory())) {
            new File("/Saved Events").mkdir();
        }

        try {
            Element root = new Element("SavedEvents");

            for (EventController event : eventsMap.values()) {
                Element child1 = new Element("Title");
                child1.addContent(event.getEventTitle());

                Element child2 = new Element("Date");
                child2.addContent(event.getDueDate().toString());

                Element eventElement = new Element("Event");
                eventElement.addContent(child1);
                eventElement.addContent(child2);

                root.addContent(eventElement);
            }

            org.jdom2.Document doc = new org.jdom2.Document();
            doc.setRootElement(root);

            XMLOutputter outPutter = new XMLOutputter();
            outPutter.setFormat(Format.getPrettyFormat());
            outPutter.output(doc, new FileWriter(new File(file.getPath() + "/events.xml")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to load events into the system.
     */
    public static void loadEvents() {
        try {
            File eventsFile = new File("/Saved Events/events.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(eventsFile);
            doc.getDocumentElement().normalize();

            NodeList termList = doc.getElementsByTagName("Event");

            for (Integer i = 0; i < termList.getLength(); i++) {
                Node node = termList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;

                    String title = element.getElementsByTagName("Title").item(0).getTextContent();
                    String date = element.getElementsByTagName("Date").item(0).getTextContent();
                    LocalDate localDate = LocalDate.parse(date);

                    MainSceneController.getInstance().addEventToScene(title, localDate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
