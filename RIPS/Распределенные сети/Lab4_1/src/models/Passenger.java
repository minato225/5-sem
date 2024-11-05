package models;

/**
 * Movable Passenger.
 */
public class Passenger {
    private final String name;
    private String trainName;
    private boolean movable;
    private final long moveTime;

    public Passenger(String name, boolean movable, long timeToMove) {
        this.name = name;
        this.movable = movable;
        this.moveTime = timeToMove;
    }

    /**
     * Train passengers can change to another train if possible due to the train stop time.
     *
     * @return true if passenger want to move another train, otherwise: false.
     */
    public boolean isMovable() {
        return movable;
    }

    /**
     * Get Move Time for exchange passenger.
     *
     * @return time in milliseconds.
     */
    public long getMoveTime() {
        return moveTime;
    }

    /**
     * When Passenger decide to move another train, field movable set false;
     *
     * @param movable =!movable
     */
    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    /**
     * Set When Passenger buy ticket on train.
     *
     * @param trainName train name witch passenger want to seat.
     */
    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    /**
     * If passenger ids movable then Train name set to A -> B trains path.
     *
     * @param nextTrainName next train name.
     */
    public void addTrainPathHistory(String nextTrainName) {
        trainName = String.format("%s -> %s", trainName, nextTrainName);
    }

    @Override
    public String toString() {
        return "{name = '" + name + '\'' +
                ", train = '" + trainName + '\'' +
                ", movable = " + movable +
                ", t = " + moveTime + "ml}";
    }
}