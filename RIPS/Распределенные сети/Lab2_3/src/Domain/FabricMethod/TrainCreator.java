package Domain.FabricMethod;

import Domain.Model.FreightTrain;
import Domain.Model.PassengerTrain;
import Domain.Model.RailWayTransport;

import java.util.List;

/**
 * Represents GoF pattern fabric method.
 * @see PassengerTrain#PassengerTrain(DataArgs)
 * @see FreightTrain#FreightTrain(DataArgs)
 * */
public class TrainCreator {
    private enum Signs {Passengers, Freight}

    /**
     * Get transport instance by string id.
     * @param id <b>Signs</b> Passengers and Freight.
     * @param args data args.
     * @return Rail Way Transport.
     * */
    public static RailWayTransport getTransport(String id, DataArgs args) {
        Signs sign = Signs.valueOf(id);

        return switch (sign) {
            case Passengers -> new PassengerTrain(args);
            case Freight -> new FreightTrain(args);
        };
    }
}
