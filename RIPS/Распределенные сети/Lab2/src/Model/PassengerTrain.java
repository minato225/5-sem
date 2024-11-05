package Model;

import FabricMethod.DataArgs;

import java.util.List;

/**
 * Passenger Train.
 * */
public class PassengerTrain extends RailWayTransport{

    /**
     * {@inheritDoc}
     * */
    public PassengerTrain(String name, String country, int maxSpeed, List<Coach> passengerCars) {
        super(name, country, maxSpeed, passengerCars);
    }

    /**
     * {@inheritDoc}
     * */
    public PassengerTrain(DataArgs args) {
        super(args);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public int compareTo(RailWayTransport o) {
        return this.wagons.size() - o.wagons.size();
    }
}
