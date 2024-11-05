import Controllers.DOMParser;
import Controllers.XMLValidator;
import models.RailWayTransport;

public class Main {

    public static void main(String[] args) {
        var xml = "src/source/trains.xml";
        var xsd = "src/source/trains.xsd";
        try {
            if (XMLValidator.validate(xml, xsd)) {
                System.out.println("File is valid");
                var train = new DOMParser().parse(xml);
                printTrain(train);
            } else {
                System.out.println("File is invalid");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Print Transport in custom string format.
     *
     * @param transport given transport.
     */
    public static void printTrain(RailWayTransport transport) {
        System.out.println("RailWay transport");
        var trainString = new StringBuilder(
                "\tName " + transport.getName() +
                        " Max speed " + transport.getMaxSpeed() +
                        " Country " + transport.getCountry() +
                        "\n\t\tTrain ");

        for (var wagon : transport.getWagons()) {
            trainString.append("\n\t\t\tid ").append(wagon.getId());
            trainString.append("\n\t\t\tType ").append(wagon.getType());
            trainString.append(" \n\t\t\tPeople");
            for (var person : wagon.getPeoples()) {
                trainString.append("\n\t\t\t\t").append(person);
            }
        }

        System.out.println(trainString);
    }
}
