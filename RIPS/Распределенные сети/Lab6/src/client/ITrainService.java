package client;

import models.RailWayTransport;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ITrainService extends Remote {
    int CountPassengers(RailWayTransport transport) throws RemoteException;

    int CountBaggage(RailWayTransport transport) throws RemoteException;

    List<RailWayTransport> findTrainsBySpeed(List<RailWayTransport> trainsDepo, int min, int max) throws RemoteException;

    void printTrain(RailWayTransport transport) throws RemoteException;
}
