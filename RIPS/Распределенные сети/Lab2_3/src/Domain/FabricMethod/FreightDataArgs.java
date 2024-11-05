package Domain.FabricMethod;

import Domain.Model.Coach;

import java.util.List;

/**
 * {@inheritDoc}
 * */
public class FreightDataArgs extends DataArgs{
    /**
     * {@inheritDoc}
     * */
    public FreightDataArgs(String name, String country, int maxSpeed, List<Coach> wagons) {
        super(name, country, maxSpeed, wagons);
    }
}
