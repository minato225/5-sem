package FabricMethod;

import Model.Coach;
import java.util.List;

/**
 * {@inheritDoc}
 * */
public class PassengersDataArgs extends DataArgs{

    /**
     * {@inheritDoc}
     * */
    public PassengersDataArgs(String name, String country, int maxSpeed, List<Coach> wagons) {
        super(name, country, maxSpeed, wagons);
    }
}
