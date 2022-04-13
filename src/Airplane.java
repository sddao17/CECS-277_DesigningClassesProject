
import java.util.ArrayList;

/**
 * Constructs an Airplane object that takes SeatingSection objects.
 *
 * For our purposes:
 *      - The Airplane object must NOT be coupled with the Scanner or PrintStream classes.
 *      - Seats will be portrayed using various SeatingSection objects, each with their own
 *          member variables `seats` which are two-D arrays of type String[][].
 */
public class Airplane {

    private final SeatingSection firstClass;
    private final SeatingSection economyClass;

    /**
     * Constructs a default empty plane.
     */
    public Airplane() {
        firstClass = new SeatingSection();
        economyClass = new SeatingSection();
    }

    /**
     * Constructs a plane with the given seating class rows and columns.
     *
     * @param firstRows the number of rows of the firstClass seatingSection
     * @param firstColumns the number of columns of the firstClass seatingSection
     * @param economyRows the number of rows of the economyClass seatingSection
     * @param economyColumns the number of columns of the economyClass seatingSection
     */
    public Airplane(int firstRows, int firstColumns, int economyRows, int economyColumns) {
        firstClass = new SeatingSection(firstRows, firstColumns);
        economyClass = new SeatingSection(economyRows, economyColumns);
    }

    /**
     * Returns the firstClass SeatingSection instance.
     *
     * @return the firstClass SeatingSection instance
     */
    public SeatingSection getFirstClass() {
        return firstClass;
    }

    /**
     * Returns the economyClass SeatingSection instance.
     *
     * @return the economyClass SeatingSection instance
     */
    public SeatingSection getEconomyClass() {
        return economyClass;
    }

    /**
     * Returns all seats from all seating sections of the Airplane.
     *
     * @return all seats from all seating sections of the Airplane
     */
    public ArrayList<String[][]> getAllSeats() {
        ArrayList<String[][]> allSeats = new ArrayList<String[][]>();

        // copy firstClass seats into the ArrayList
        allSeats.add(firstClass.getSeating());
        // copy economyClass seats into the ArrayList
        allSeats.add(economyClass.getSeating());

        return allSeats;
    }

    /**
     * Returns whether or not the Airplane is completely full.
     *
     * @return true if the Airplane is full; false otherwise.
     */
    public boolean isFull() {
        ArrayList<String[][]> allSeats = getAllSeats();

        // scan the airplane; if even one seat is empty, immediately return false
        for (String[][] section : allSeats) {
            for (String[] row : section) {
                for (String column : row) {
                    if (column.equals(".")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns whether or not the seating section is completely full.
     *
     * @return true if the seating section is full; false otherwise.
     */
    public boolean isFullInSection(String seatingSection) {
        // check the appropriate seating section to see if it is completely full
        if (seatingSection.equals("first")) {
            return firstClass.isFull();
        } else if (seatingSection.equals("economy")) {
            return economyClass.isFull();
        }
        return false;
    }

    /**
     * Returns whether or not the number of seats in a row on either side of the aisle matches the number of passengers.
     *
     * @param seatingSection the seating section to be scanned
     * @param numOfPassengers the number of passengers to be seated
     * @return true if rows can be filled with the number of passengers; false otherwise.
     */
    public boolean canFillRowInSection(String seatingSection, int numOfPassengers) {
        // check the appropriate seating section to see if a row can be filled
        if (seatingSection.equals("first")) {
            return firstClass.canFillRow(numOfPassengers);
        } else if (seatingSection.equals("economy")) {
            return economyClass.canFillRow(numOfPassengers);
        }

        return false;
    }

    /**
     * Returns true if the seating section has enough available seats; false otherwise.
     *
     * @param seatingSection the seating section within the Airplane
     * @param numOfPassengers the number of passengers to seat
     * @return true if the seating section has enough available seats; false otherwise.
     */
    public boolean hasAvailableSeatsInSection(String seatingSection, int numOfPassengers) {
        // check the appropriate seating section to see if there are enough available seats
        if (seatingSection.equals("first")) {
            return firstClass.hasAvailableSeats(numOfPassengers);
        } else if (seatingSection.equals("economy")) {
            return economyClass.hasAvailableSeats(numOfPassengers);
        }

        return false;
    }

    /**
     * Returns the seating section's indexes of the available seats of the specified seating location.
     *
     * @param numOfPassengers the number of passengers to seat
     * @return the array containing:
     *              - index of the row to add,
     *              - the index of the column to begin adding,
     *              - the index of the column to stop after adding
     */
    public int[] availableSeatsOfLocationType(String seatingSection, int numOfPassengers, String seatingLocation) {
        // check the appropriate seating section to see if there are enough available seats
        if (seatingSection.equals("first")) {
            return firstClass.availableSeatsOfLocationType(numOfPassengers, seatingLocation);
        } else if (seatingSection.equals("economy")) {
            return economyClass.availableSeatsOfLocationType(numOfPassengers, seatingLocation);
        }

        return new int[0];
    }

    /**
     * Adds a passenger to the plane.
     *
     * @param seatingSection the type of seating section that the passenger will sit in
     * @param numOfPassengers the number of passengers to seat
     * @param seatingLocation the location of the seat relative to the row (aisle, center, or window)
     */
    public void addPassengers(String seatingSection, int numOfPassengers, String seatingLocation) {
        // separate the instances of adding passengers because they hold their own unique arrays
        if (seatingSection.equals("first")) {
            firstClass.addPassengers(numOfPassengers, seatingLocation);
        } else if (seatingSection.equals("economy")) {
            economyClass.addPassengers(numOfPassengers, seatingLocation);
        }
    }

    /**
     * Returns a description of the Airplane as a string.
     *
     * @return a description of the Airplane as a string
     */
    public String toString() {
        String description = "\n";

        // reset the rowCount so it will be the same during each display representation
        firstClass.setRowCount(0);

        // add toString() of all seating sections here
        description += firstClass;
        description += economyClass;

        return description;
    }
}
