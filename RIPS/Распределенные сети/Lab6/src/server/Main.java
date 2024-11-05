package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Console Main class.
 */
public class Main {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Main launch method for testing possibilities.
     *
     * @param args command line params.
     */
    public static void main(String[] args) throws RemoteException {
        logger.info("Initializing ITrainService...");
        var registry = LocateRegistry.createRegistry(8080);
        registry.rebind("ITrainService", new TrainService());
        logger.info("Server Started!!");
    }
}
