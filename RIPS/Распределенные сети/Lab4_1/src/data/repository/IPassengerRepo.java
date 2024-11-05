package data.repository;

import models.Passenger;

import java.util.List;

public interface IPassengerRepo {
    /**
     * Give List of Passengers from any storage.
     *
     * @param count count od passengers.
     * @return List of Passengers.
     */
    List<Passenger> getPassengers(int count);
}
