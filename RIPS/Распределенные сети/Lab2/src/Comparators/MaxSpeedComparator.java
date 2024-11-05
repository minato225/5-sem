package Comparators;

import Model.RailWayTransport;
import java.util.Comparator;

/**
 * Custom comparator for RailWayTransport by max speed.
 * @see RailWayTransport
 * */
public class MaxSpeedComparator implements Comparator<RailWayTransport> {

    /**
     * Compare each transport by their max speed.
     * @return -1 0 1.
     * */
    @Override
    public int compare(RailWayTransport o1, RailWayTransport o2) {
        return o1.getMaxSpeed() - o2.getMaxSpeed();
    }
}
