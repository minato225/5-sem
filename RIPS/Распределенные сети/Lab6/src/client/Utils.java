package client;

import enums.CoachType;
import enums.Status;
import exception.TrainException;
import fabricMethod.PassengersDataArgs;
import fabricMethod.TrainCreator;
import models.Coach;
import models.Person;
import models.RailWayTransport;

import java.util.ArrayList;
import java.util.Random;

public class Utils {

    /**
     * Generate Random train.
     *
     * @param carCount       cars count
     * @param personCapacity person capacity.
     * @return random make train.
     */
    public static RailWayTransport generatePassengerTrain(int carCount, int personCapacity) {
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

        try {
            return TrainCreator.getTransport("Passengers", args);
        } catch (TrainException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Generate random number in diapason.
     *
     * @param min lower bound
     * @param max upper bound
     * @return random int
     */
    public static int rand(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }
}
