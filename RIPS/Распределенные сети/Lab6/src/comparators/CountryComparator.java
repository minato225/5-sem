package comparators;

import models.RailWayTransport;

import java.util.Comparator;

/**
 * Custom comparator for RailWayTransport by country.
 * @see RailWayTransport
 * */
public class CountryComparator implements Comparator<RailWayTransport> {

    /**
     * Compare each transport by their country.
     * @return -1 0 1.
     * */
    @Override
    public int compare(RailWayTransport o1, RailWayTransport o2) {
        return o1.getCountry().compareTo(o2.getCountry());
    }
}
