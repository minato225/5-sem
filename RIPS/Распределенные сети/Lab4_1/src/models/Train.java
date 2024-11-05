package models;

import exceptions.TrainException;

import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.stream.Collectors;

public record Train(String trainId, long stayTime, TrainStation station,
                    List<Passenger> passengers,
                    Exchanger<List<Passenger>> exchanger) implements Runnable {

    public Train {
        try {
            if (passengers == null) throw new TrainException("Passengers is null.");
        } catch (TrainException e) {
            e.printStackTrace();
        }

        assert passengers != null;
        passengers = passengers.stream()
                .peek(x -> x.setTrainName(trainId))
                .collect(Collectors.toList());
    }

    @Override
    public void run() {
        System.out.printf("Поезд %s в пути.\n", trainId);

        try {
            station.onTrainArrival(this);
            //Thread.sleep(1000);
            var neighbourTrain = station.waitNeighborTrain(trainId);
            var movablePassengers = selectMovablePassengers(neighbourTrain.trainId);

            System.out.printf("Поезд %s начал пересадку\n\n", trainId);
            station.movePassengers(trainId, stayTime, passengers);
            passengers.removeAll(movablePassengers);
            passengers.addAll(exchanger.exchange(movablePassengers));

            station.onTrainDeparture(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * List movable passengers with move time less than neighbour train exchange time.
     *
     * @param neighborTrainName for change passenger's path.
     * @return List passengers.
     */
    private List<Passenger> selectMovablePassengers(String neighborTrainName) {
        return passengers.stream()
                .filter(Passenger::isMovable)
                .filter(x -> {
                    try {
                        return x.getMoveTime() <= station.waitNeighborTrain(trainId).stayTime;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .peek(x -> x.addTrainPathHistory(neighborTrainName))
                .peek(x -> x.setMovable(false))
                .collect(Collectors.toList());
    }

    private synchronized void waitForNeighbour() throws InterruptedException {
        while (station.getPlatformTrains().size() < 2)
            wait(100);
        notifyAll();
    }
}
