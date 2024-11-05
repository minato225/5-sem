package Controllers;

import enums.CoachType;
import models.Coach;
import models.ParsingException;
import models.Person;
import models.RailWayTransport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Parser implementation that uses DOM.
 */
public class DOMParser {
    public RailWayTransport parse(String xml) throws ParsingException {
        var inputFile = new File(xml);
        var dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Document doc;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new ParsingException("Configuration DOM parser error", e);
        }
        doc.getDocumentElement().normalize();

        var trainNode = doc.getDocumentElement();
        var waggonsNodes = trainNode.getChildNodes();

        var coachList = new ArrayList<Coach>();
        for (int i = 0; i < waggonsNodes.getLength(); i++) {
            var node = waggonsNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                var element = (Element) node;

                var peoples = new ArrayList<Person>();
                coachList.add(new Coach(
                        Integer.parseInt(element.getAttribute("id")),
                        CoachType.valueOf(element.getAttribute("type")),
                        peoples));
            }
        }

        return new RailWayTransport(
                trainNode.getAttribute("name"),
                trainNode.getAttribute("country"),
                Integer.parseInt(trainNode.getAttribute("maxSpeed")),
                coachList);
    }
}
