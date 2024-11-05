package sample;

import Domain.Enums.CoachType;
import Domain.Enums.Status;
import Domain.FabricMethod.PassengersDataArgs;
import Domain.FabricMethod.TrainCreator;
import Domain.Model.Coach;
import Domain.Model.Person;
import Domain.Model.RailWayTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataLoader {
    /**
     * Generate Random train.
     *
     * @param carCount       cars count
     * @param personCapacity person capacity.
     * @return random make train.
     */
    private static RailWayTransport generatePassengerTrain(String trainType, int carCount, int personCapacity) {
        var rnd = new Random();
        final var trainNames = new String[]{"sapsan", "chayka", "Ligth"};
        final var trainCountries = new String[]{"BEL", "RUS", "UKR", "USA", "CHI"};
        final var personNames = new String[]{"Dima", "Lisa", "Andry", "Roman"};

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

        return TrainCreator.getTransport(trainType, args);
    }

    /**
     * Generate random number in diapason.
     *
     * @param min lower bound
     * @param max upper bound
     * @return random int
     */
    private static int rand(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    public static List<RailWayTransport> LoadTrainsData(int count) {
        var trains = new ArrayList<RailWayTransport>(count);
        for (int i = 0; i < count; i++) {
            trains.add(generatePassengerTrain("Passengers", 10,10));
        }

        return trains;
    }
}
