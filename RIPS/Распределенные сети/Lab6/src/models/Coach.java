package models;
import enums.CoachType;

import java.io.Serializable;
import java.util.List;

/**
 * Coach of Passenger.
 */
public class Coach implements Serializable {
    private final int id;
    private final CoachType type;
    private final List<Person> peoples;

    /**
     * Constructor - create new instance of class Coach
     * @param id id.
     * @param type type.
     * @param peoples list of people.
     * @see Coach#Coach(int, CoachType, List)
     */
    public Coach(int id, CoachType type, List<Person> peoples) {
        this.id = id;
        this.type = type;
        this.peoples = peoples;
    }

    /**
     * Get List Peoples.
     * @return List of peoples.
     */
    public List<Person> getPeoples() {
        return peoples;
    }


    /**
     * Get Coach Type.
     * @return Coach Type.
     */
    public CoachType getType() {
        return type;
    }


    /**
     * Get Coach ID.
     * @return int cell ID.
     */
    public int getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public String toString() {
        return "Coach{id=" + id  + ", type=" + type + ", peoples=" + peoples + '}';
    }
}
