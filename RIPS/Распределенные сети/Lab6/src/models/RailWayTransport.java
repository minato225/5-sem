package models;
import exception.TrainException;
import fabricMethod.DataArgs;

import java.io.Serializable;
import java.util.List;

/**
 * Abstract base class of all RailWay Transport.
 * */
public abstract class RailWayTransport implements Comparable<RailWayTransport> , Serializable {
    /** name*/protected String name;
    /** country*/protected String country;
    /** max speed*/protected int maxSpeed;
    /** wagons*/protected List<Coach> wagons;

    /**
     * Constructor create new instance of Passengers Train.
     * @param name - train name.
     * @param country - train country.
     * @param maxSpeed - train max speed.
     * @param wagons - train passengers Cars.
     * @see FreightTrain#FreightTrain(String, String, int, List)
     */
    public RailWayTransport(String name, String country, int maxSpeed, List<Coach> wagons) {
        this.name = name;
        this.country = country;
        this.maxSpeed = maxSpeed;
        this.wagons = wagons;
    }

    /**
     * Simple Constructor create new instance of Passengers Train.
     * @param args - train args {@link DataArgs}.
     * @see RailWayTransport#RailWayTransport(DataArgs)
     */
    public RailWayTransport(DataArgs args) throws TrainException {

        if (args == null){
            throw new TrainException("Data Args is null");
        }

        this.name = args.getName();
        this.country = args.getCountry();
        this.maxSpeed = args.getMaxSpeed();
        this.wagons = args.getWagons();
    }

    /**
     * Get private field Name.
     * @return name.
     * */
    public String getName() { return name; }

    /**
     * Get private field Country.
     * @return country.
     * */
    public String getCountry() { return country; }

    /**
     * Get private field Max Speed.
     * @return train max speed.
     * */
    public int getMaxSpeed() { return maxSpeed; }

    /**
     * Get private field Wagons.
     * @return wagons.
     * @see Coach
     * */
    public List<Coach> getWagons() { return wagons; }

    /**
     * {@inheritDoc}
     * */
    @Override
    public String toString() {
        return  "RailWayTransport" +
                " name = " + name +
                ", country = " + country +
                ", maxSpeed = " + maxSpeed +
                ", passengerCars = " + wagons;
    }
}
