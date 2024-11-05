package models;

import enums.Status;

import java.io.Serializable;
import java.util.List;

/**
 * Just Person.
 */
public class Person implements Serializable {
    private final String name;
    private final int age;
    private final Status status;
    private final Baggage baggage;

    /**
     * Constructor create new instance of Person Train.
     * @param name - person name.
     * @param age - person age.
     * @param status - person status.
     * @param baggage - person baggage.
     * @see Person#Person(String, int, Status, Baggage)
     */
    public Person(String name, int age, Status status, Baggage baggage) {
        this.name = name;
        this.age = age;
        this.status = status;
        this.baggage = baggage;
    }

    /**
     * Get private field Baggage.
     * @return person baggage.
     * @see Baggage
     * */
    public Baggage getBaggage() {
        return baggage;
    }

    /**
     * override to string for each person.
     * @return person represnts string.
     * */
    @Override
    public String toString() {
        return "Person{" +
                " name='" + name + '\'' +
                ", age=" + age +
                ", status=" + status +
                ", baggage=" + baggage +
                '}';
    }

    /**
     * Inner Class Baggage of {@link Person}
     * */
    public static final class Baggage implements Serializable{
        private final int weight;
        private final int count;
        private final int cellPlace;

        /**
         * Constructor create new instance of Passengers Train.
         * @param weight weigth
         * @param count count
         * @param cellPlace cell palece.
         * @see FreightTrain#FreightTrain(String, String, int, List)
         */
        public Baggage(int weight, int count, int cellPlace) {
            this.weight = weight;
            this.count = count;
            this.cellPlace = cellPlace;
        }

        /**
         * Get private field count.
         * @return baggage count's.
         * */
        public int getCount() {
            return count;
        }

        /**
         * Override to string for each baggage.
         * @return baggage represnts string.
         * */
        @Override
        public String toString() {
            return "{ weight=" + weight +
                    ", count=" + count +
                    ", cellPlace=" + cellPlace +
                    " }";
        }
    }
}
