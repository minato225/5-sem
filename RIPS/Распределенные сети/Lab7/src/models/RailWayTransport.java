package models;

import java.util.List;

/**
 * Abstract base class of all RailWay Transport.
 * */
public class RailWayTransport{
    /** name*/protected String name;
    /** country*/protected String country;
    /** max speed*/protected int maxSpeed;
    /** wagons*/protected List<Coach> wagons;

    public RailWayTransport(){ }

    /**
     * Constructor create new instance of Passengers Train.
     * @param name - train name.
     * @param country - train country.
     * @param maxSpeed - train max speed.
     * @param wagons - train passengers Cars.
     */
    public RailWayTransport(String name, String country, int maxSpeed, List<Coach> wagons) {
        this.name = name;
        this.country = country;
        this.maxSpeed = maxSpeed;
        this.wagons = wagons;
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
