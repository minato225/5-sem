package fabricMethod;

import models.Coach;
import models.FreightTrain;

import java.util.List;

/**
 * Model of transports params
 * */
public class DataArgs {
    private final String name;
    private final String country;
    private final int maxSpeed;
    private final List<Coach> wagons;

    /**
     * Constructor create new instance of Data args.
     * @param name - train name.
     * @param country - train country.
     * @param maxSpeed - train max speed.
     * @param wagons - train wagons.
     * @see FreightTrain#FreightTrain(String, String, int, List)
     */
    public DataArgs(String name, String country, int maxSpeed, List<Coach> wagons) {
        this.name = name;
        this.country = country;
        this.maxSpeed = maxSpeed;
        this.wagons = wagons;
    }

    /**
     * setter for Name.
     * @return string name.
     * */
    public String getName() {
        return name;
    }

    /**
     * setter for Country.
     * @return string Country.
     * */
    public String getCountry() {
        return country;
    }

    /**
     * setter for Max Speed.
     * @return int Max speed.
     * */
    public int getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * setter for Wagons.
     * @return List of wagons {@link #getWagons()}
     * */
    public List<Coach> getWagons() {
        return wagons;
    }
}
