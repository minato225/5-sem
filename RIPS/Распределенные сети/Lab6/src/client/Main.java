package client;

import comparators.MaxSpeedComparator;
import models.RailWayTransport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        logger.info("Starting...\n");
        var railWayController = (ITrainService) LocateRegistry.getRegistry(8080).lookup("ITrainService");
        logger.info("done\n");

        //////////////////////////////////////////////////////////////////////////////////////////////////////
        var train = Utils.generatePassengerTrain(10, 10);

        try {
            railWayController.printTrain(train);
        } catch (RemoteException e) {
            logger.error(e.getMessage());
        }

        try {
            System.out.println("Count of baggage " + railWayController.CountBaggage(train));
            System.out.println("Count of passengers " + railWayController.CountPassengers(train));
        } catch (RemoteException e) {
            logger.error(e.getMessage());
        }

        var railwayStation = new ArrayList<RailWayTransport>();
        railwayStation.add(Utils.generatePassengerTrain(5, 2));
        railwayStation.add(Utils.generatePassengerTrain(5, 2));
        railwayStation.add(Utils.generatePassengerTrain(5, 3));
        railwayStation.add(Utils.generatePassengerTrain(5, 3));

        System.out.print("Speed: ");
        railwayStation.forEach(x -> System.out.print(x.getMaxSpeed() + " "));

        System.out.println("\nCount person sort");
        Collections.sort(railwayStation);
        railwayStation.forEach(x -> System.out.print(x.getWagons().size() + " "));

        System.out.println("\nmax Speed sort");
        railwayStation.sort(new MaxSpeedComparator());
        railwayStation.forEach(x -> System.out.print(x.getMaxSpeed() + " "));

        System.out.println("Filter by speed");
        try {
            railWayController.findTrainsBySpeed(railwayStation, 100, 300)
                    .forEach(x -> System.out.println("speed: " + x.getMaxSpeed() + ", country " + x.getCountry()));
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }
}
