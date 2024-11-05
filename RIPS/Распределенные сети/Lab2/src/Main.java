import Comparators.MaxSpeedComparator;
import Controller.RailWayController;
import Enums.*;
import FabricMethod.*;
import Model.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Console Main class.
 * */
public class Main {

    /**
     * Main launch method for testing possibilities.
     * @param args command line params.
     * */
    public static void main(String[] args) {
        var train = generatePassengerTrain(10, 10);
        RailWayController.printTrain(train);

        System.out.println("Count of baggage " + RailWayController.CountBaggage(train));
        System.out.println("Count of passengers " + RailWayController.CountPassengers(train));

        var railwayStation = new ArrayList<RailWayTransport>();
        railwayStation.add(generatePassengerTrain(5, 5));
        railwayStation.add(generatePassengerTrain(5, 3));
        railwayStation.add(generatePassengerTrain(5, 10));
        railwayStation.add(generatePassengerTrain(5, 8));

        System.out.print("Speed: ");
        railwayStation.forEach(x -> System.out.print(x.getMaxSpeed() + " "));

        System.out.println("\nCount person sort");
        Collections.sort(railwayStation);
        railwayStation.forEach(x -> System.out.print(x.getWagons().size() + " "));

        System.out.println("\nmax Speed sort");
        railwayStation.sort(new MaxSpeedComparator());
        railwayStation.forEach(x -> System.out.print(x.getMaxSpeed() + " "));

        System.out.println("Filter by speed");
        RailWayController
                .findTrainsBySpeed(railwayStation, 100, 300)
                .forEach(x -> System.out.println("speed: " + x.getMaxSpeed() + ", country " + x.getCountry()));
    }

    /**
     * Generate Random train.
     * @param carCount cars count
     * @param personCapacity person capacity.
     * @return random make train.
     * */
    private static RailWayTransport generatePassengerTrain(int carCount, int personCapacity) {
        var rnd = new Random();
        var trainNames = new String[]{"sapsan", "chayka", "Ligth"};
        var trainCountries = new String[]{"BEL", "RUS", "UKR", "USA", "CHI"};
        var personNames = new String[]{"dima", "lisa", "andry", "roman"};

        var passengerCars = new ArrayList<Coach>();
        var l = rand(1, carCount);
        for (int i = 0; i < l; ++i) {
            var id = rnd.nextInt(5);
            var type = CoachType.values()[rnd.nextInt(CoachType.values().length)];
            var people = new ArrayList<Person>();

            var lp = rand(1, personCapacity);
            for (int j = 0; j < lp; ++j) {
                var name = personNames[rnd.nextInt(personNames.length)];
                var age = rand(1, 100);
                var status = Status.values()[rnd.nextInt(Status.values().length)];
                var bag = new Person.Baggage(rand(2, 100), rand(1, 10), rand(1000, 9999));
                people.add(new Person(name, age, status, bag));
            }

            passengerCars.add(new Coach(id, type, people));
        }

        var args = new PassengersDataArgs(trainNames[rnd.nextInt(trainNames.length)],
                trainCountries[rnd.nextInt(trainCountries.length)],
                rand(100, 500),
                passengerCars);

        return TrainCreator.getTransport("Passengers", args);
    }

    /**
     * Generate random number in diapason.
     * @param min lower bound
     * @param max upper bound
     * @return random int
     * */
    private static int rand(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }
}
