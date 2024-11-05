package Model;
import FabricMethod.DataArgs;

import java.util.List;

/**
 * Freight train.
 * */
public class FreightTrain extends RailWayTransport{

    /**
     * Constructor create new instance of Freight Train.
     * @param name - train name.
     * @param country - train country.
     * @param maxSpeed - train max speed.
     * @param coaches - train passengers Cars.
     * @see FreightTrain#FreightTrain(String, String, int, List)
     */
    public FreightTrain(String name, String country, int maxSpeed, List<Coach> coaches) {
        super(name, country, maxSpeed, coaches);
    }

    /**
     * Simple Constructor create new instance of Freight Train.
     * @param args - train args {@link DataArgs}.
     * @see FreightTrain#FreightTrain(DataArgs)
     */
    public FreightTrain(DataArgs args) {
        super(args);
    }

    /**
     * Compare on wagons count count.
     * */
    @Override
    public int compareTo(RailWayTransport o) {
        return this.wagons.size() - o.wagons.size();
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
}
