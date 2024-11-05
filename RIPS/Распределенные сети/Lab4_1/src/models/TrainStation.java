package models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainStation {
    private boolean platformWork = false;
    private final List<Train> platformTrains = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger();
    private final List<Passenger> platformPassengers = new ArrayList<>();

    /**
     * Modeling the situation of a train arriving at the platform.
     *
     * @param train arriving train.
     * @throws InterruptedException when threads don't synchronized.
     */
    public synchronized void onTrainArrival(Train train) throws InterruptedException {
        while (platformTrains.size() >= 2) wait();

        logger.info(String.format("Поезд %s прибыл.\n", train.trainId()));
        platformTrains.add(train);
        Thread.sleep(500);

        System.out.printf("Поезд %s прибыл с пассажирами.\n", train.trainId());
        train.passengers().forEach(x -> System.out.println("\t" + x));

        System.out.printf("Поездов на платформе %d\n\n", platformTrains.size());

        notifyAll();
    }

    /**
     * Modeling the situation of a train Departure at the platform.
     *
     * @param train train.
     * @throws InterruptedException when threads don't synchronized.
     */
    public synchronized void onTrainDeparture(Train train) throws InterruptedException {
        while (platformTrains.size() < 1) wait();

        platformTrains.remove(train);
        Thread.sleep(500);

        logger.info(String.format("Поезд %s уехал.\n", train.trainId()));
        System.out.printf("Поезд %s уехал с пассажирами.\n", train.trainId());
        train.passengers().forEach(x -> System.out.println("\t" + x));

        System.out.printf("На платформе %d поездов\n\n", platformTrains.size());

        notifyAll();
    }

    /**
     * Modeling the situation of a train move passengers.
     *
     * @param trainId    current train id.
     * @param stayTime   current train stay time.
     * @param passengers current train passengers.
     * @throws InterruptedException when threads don't synchronized.
     */
    public synchronized void movePassengers(String trainId, long stayTime, List<Passenger> passengers)
            throws InterruptedException {
        while (platformWork) wait();

        platformWork = true;
        var latePassengers = passengers.stream()
                .filter(x -> x.getMoveTime() > stayTime)
                .filter(Passenger::isMovable)
                .collect(Collectors.toList());

        logger.info("Происходит пересадки.");

        platformPassengers.addAll(latePassengers);
        Thread.sleep(stayTime);
        System.out.printf("Время пересадки поезда %s:%d\n", trainId, stayTime);
        platformWork = false;

        notifyAll();
    }

    /**
     * Get platform neighbour train.
     *
     * @return train.
     */
    public synchronized Train waitNeighborTrain(String trainId) throws InterruptedException{
        while (platformTrains.size() < 2) wait();
        notifyAll();

        return platformTrains.stream()
                .filter(x -> !x.trainId().equals(trainId))
                .findFirst()
                .get();
    }

    public List<Train> getPlatformTrains() {
        return platformTrains;
    }

    public List<Passenger> getPlatformPassengers() {
        return platformPassengers;
    }
}
