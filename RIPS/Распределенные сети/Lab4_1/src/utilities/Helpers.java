package utilities;

import models.Train;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Contains useful static functions.
 */
public class Helpers {
    /**
     * Build String representation of trains.
     *
     * @param trains some trains.
     * @return string.
     */
    public static String trainsToString(Train... trains) {
        return Arrays.stream(trains)
                .map(x -> x.trainId() + ": " + x.passengers().stream().
                        map(y -> "\t" + y.toString())
                        .collect(Collectors.joining("\n")))
                .collect(Collectors.joining("\n\n"));
    }
}
