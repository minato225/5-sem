import data.repository.IPassengerRepo;
import data.repository.InMemPassengerRepo;
import models.Passenger;
import models.Train;
import models.TrainStation;
import utilities.Helpers;

import java.util.List;
import java.util.concurrent.Exchanger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        IPassengerRepo repo = new InMemPassengerRepo();
        var trainStation = new TrainStation();
        var exchanger = new Exchanger<List<Passenger>>();

        var trainA = new Train("A", 6000, trainStation, repo.getPassengers(5), exchanger);
        var trainB = new Train("B", 1000, trainStation, repo.getPassengers(5), exchanger);
        var trainC = new Train("C", 1000, trainStation, repo.getPassengers(5), exchanger);
        var trainD = new Train("D", 1000, trainStation, repo.getPassengers(5), exchanger);

        var tA = new Thread(trainA);
        var tB = new Thread(trainB);
        var tC = new Thread(trainC);
        var tD = new Thread(trainD);

        tA.start();
        Thread.sleep(100);
        tB.start();
        Thread.sleep(50);
        tC.start();
        Thread.sleep(100);
        tD.start();

        tA.join();
        tB.join();
        tC.join();
        tD.join();

        System.out.println("\n\n--------------------Итогоывй Состав Поездов------------");
        System.out.println(Helpers.trainsToString(trainA, trainB, trainC, trainD));
        System.out.println("\n\n---------------------Оставшиеся пассажиры--------------");
        trainStation.getPlatformPassengers().forEach(System.out::println);
    }
}
