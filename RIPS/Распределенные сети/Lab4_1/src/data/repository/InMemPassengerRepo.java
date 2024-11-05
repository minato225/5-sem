package data.repository;

import models.Passenger;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * In Memory Storage for Passengers.
 */
public class InMemPassengerRepo implements IPassengerRepo {
    static Random rnd = new Random();
    static String[] names = new String[]{"Alex", "Lisa", "Andrey", "Roman", "Bill", "Bob"};

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Passenger> getPassengers(int count) {
        return IntStream.range(0, count)
                .mapToObj(x -> new Passenger(randomName(), rnd.nextBoolean(), rnd.nextInt(2000)))
                .collect(Collectors.toList());
    }

    private String randomName() {
        var name = names[rnd.nextInt(names.length)];
        var surnameInitial = (char) (rnd.nextInt(26) + 'A');
        var patronymicInitial = (char) (rnd.nextInt(26) + 'A');
        return String.format("%s %c.%c.", name, surnameInitial, patronymicInitial);
    }
}
