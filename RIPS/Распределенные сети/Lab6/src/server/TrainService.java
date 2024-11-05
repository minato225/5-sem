package server;

import client.ITrainService;
import models.RailWayTransport;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService extends UnicastRemoteObject implements ITrainService {
    protected TrainService() throws RemoteException {
        super();
    }

    /**
     * Evaluate count of passengers in transport
     *
     * @param transport given transport.
     * @return passengers count.
     */
    @Override
    public int CountPassengers(RailWayTransport transport) throws RemoteException {
        var count = 0;
        for (var coach : transport.getWagons()) {
            count += coach.getPeoples().size();
        }

        return count;
    }

    /**
     * Evaluate count of baggage in transport
     *
     * @param transport given transport.
     * @return baggage count.
     */
    @Override
    public int CountBaggage(RailWayTransport transport) throws RemoteException {
        var count = 0;
        for (var coach : transport.getWagons()) {
            for (var people : coach.getPeoples()) {
                count += people.getBaggage().getCount();
            }
        }

        return count;
    }

    /**
     * Filter list of transports by speed.
     *
     * @param trainsDepo List of transports..
     * @param min        allowable min speed.
     * @param max        allowable max speed.
     * @return list of filtered transport depo.
     */
    @Override
    public List<RailWayTransport> findTrainsBySpeed(List<RailWayTransport> trainsDepo, int min, int max) throws RemoteException {
        return trainsDepo.stream()
                .filter(x -> x.getMaxSpeed() > min && x.getMaxSpeed() < max)
                .collect(Collectors.toList());
    }

    /**
     * Print Transport in custom string format.
     *
     * @param transport given transport.
     */
    @Override
    public void printTrain(RailWayTransport transport) throws RemoteException {
        System.out.println("RailWay transport");
        var trainString = new StringBuilder(
                "\tName " + transport.getName() +
                        " Max speed " + transport.getMaxSpeed() +
                        " Country " + transport.getCountry() +
                        "\n\t\tTrain ");

        for (var wagon : transport.getWagons()) {
            trainString.append("\n\t\t\tid ").append(wagon.getId());
            trainString.append("\n\t\t\tType ").append(wagon.getType());
            trainString.append(" \n\t\t\tPeople");
            for (var person : wagon.getPeoples()) {
                trainString.append("\n\t\t\t\t").append(person);
            }
        }

        System.out.println(trainString);
    }
}
