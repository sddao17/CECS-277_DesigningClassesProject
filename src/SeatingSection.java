
/**
 * Constructs a SeatingSection object representing an airplane's seat section.
 *
 * For our purposes:
 *      - An empty seat will be marked as "."
 *      - An occupied seat will be marked as "X" or "*"
 */
public class SeatingSection {

    private final String[][] seats;
    // make the count static for appending to Airplane
    private static int rowCount;

    /**
     * Constructs a default empty seating section.
     */
    public SeatingSection() {
        seats = new String[][]{};
        rowCount = 0;
    }

    /**
     * Constructs an empty seating section with the given rows and columns.
     *
     * @param rows the number of rows
     * @param columns the number of columns
     */
    public SeatingSection(int rows, int columns) {
        seats = new String[rows][columns];
        rowCount = 0;

        // populate the array with empty seats
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                seats[i][j] = ".";
            }
        }
    }

    /**
     * Returns the array representation of the seating section.
     *
     * @return the array representation of the seating section
     */
    public String[][] getSeating() {
        return seats;
    }

    /**
     * Returns the SeatingSection's row count.
     *
     * @return the SeatingSection's row count
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Sets the rowCount for the SeatingSections.
     *
     * @param newRowCount the new row count for the seating sections
     */
    public void setRowCount(int newRowCount) {
        rowCount = newRowCount;
    }

    /**
     * Returns whether or not the seating section is completely full.
     *
     * @return true if the seating section is full; false otherwise.
     */
    public boolean isFull() {
        // scan the seating section; if even one seat is empty, immediately return false
        for (String[] row : seats) {
            for (String column : row) {
                if (column.equals(".")) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns whether or not the number of seats in a row on either side of the aisle matches the number of passengers.
     *
     * @param numOfPassengers the number of passengers to be seated
     * @return true if rows can be filled with the number of passengers; false otherwise.
     */
    public boolean canFillRow(int numOfPassengers) {
        return seats[0].length / 2 == numOfPassengers;
    }

    /**
     * Returns true if the seating section has enough available seats; false otherwise.
     *
     * @param numOfPassengers the number of passengers to seat
     * @return true if the seating section has enough available seats; false otherwise.
     */
    public boolean hasAvailableSeats(int numOfPassengers) {
        int numOfEmptySeats = 0;

        // if the seat is marked as "." it is available; keep track of them
        for (String[] row : seats) {
            for (String column : row) {
                if (column.equals(".")) {
                    ++numOfEmptySeats;
                }
            }
            // at the end of each row iteration: if there are enough available seats, we are done
            if (numOfEmptySeats >= numOfPassengers) {
                return true;
            }
        }
        // if we've reached this point, there aren't enough available seats
        return false;
    }

    /**
     * Returns the indexes of the available seats of the specified seating location.
     *
     * @param numOfPassengers the number of passengers to seat
     * @param seatingLocation the location of the seat relative to the row (aisle, center, or window)
     * @return the array containing:
     *              - the index of the row to add,
     *              - the index of the column to begin adding,
     *              - the index of the column to stop after adding
     */
    public int[] availableSeatsOfLocationType(int numOfPassengers, String seatingLocation) {
        // the array of indexes to return
        int[] indexes = new int[3];
        // keep track of how many passengers are left to be seated
        int numOfPassengersToSeat = numOfPassengers;

        // check the availability of the specified seats
        switch (seatingLocation) {
            case "window":
                // for each row in the seating section ...
                for (int i = 0, seatsLength = seats.length; i < seatsLength; ++i) {
                    String[] row = seats[i];
                    // check if there are there are enough available seats, beginning with the first seat
                    for (int j = 0, rowLength = row.length; j < rowLength / 2 - 1; ++j) {
                        if (row[j].equals(".")) {
                            --numOfPassengersToSeat;
                        } else {
                            // the seat is occupied; try the next row
                            break;
                        }

                        // break out immediately if there are no more passengers to seat
                        if (numOfPassengersToSeat == 0) {
                            break;
                        }
                    }

                    // if there are no more passengers to seat, the row is available
                    if (numOfPassengersToSeat == 0) {
                        indexes[0] = i;
                        indexes[2] = numOfPassengers - 1;
                        return indexes;
                    }

                    // the left row is not available; now reset and check the right row
                    numOfPassengersToSeat = numOfPassengers;

                    for (int j = row.length - 1; j > row.length / 2; --j) {
                        if (row[j].equals(".")) {
                            --numOfPassengersToSeat;
                        } else {
                            // the seat is occupied; try the next row
                            break;
                        }

                        // break out immediately if there are no more passengers to seat
                        if (numOfPassengersToSeat == 0) {
                            break;
                        }
                    }

                    // if there are no more passengers to seat, the row is available
                    if (numOfPassengersToSeat == 0) {
                        indexes[0] = i;
                        indexes[1] = row.length - numOfPassengers;
                        indexes[2] = row.length - 1;
                        return indexes;
                    }

                    // reset the count for the next iteration
                    numOfPassengersToSeat = numOfPassengers;
                }
                // if we've reached this point, there are no available window seats
                return new int[0];

            case "aisle":
                // for each row in the seating section ...
                for (int i = 0, seatsLength = seats.length; i < seatsLength; ++i) {
                    String[] row = seats[i];
                    // check if there are there are enough available seats, beginning with the left aisle seat
                    for (int j = row.length / 2 - 1; j > 0; --j) {
                        if (row[j].equals(".")) {
                            --numOfPassengersToSeat;
                        } else {
                            // the seat is occupied; try the next row
                            break;
                        }

                        // break out immediately if there are no more passengers to seat
                        if (numOfPassengersToSeat == 0) {
                            break;
                        }
                    }

                    // if there are no more passengers to seat, the row is available
                    if (numOfPassengersToSeat == 0) {
                        indexes[0] = i;
                        indexes[1] = row.length / 2 - numOfPassengers;
                        indexes[2] = row.length / 2 - 1;
                        return indexes;
                    }

                    // the left row is not available; now reset and check the right row
                    numOfPassengersToSeat = numOfPassengers;

                    for (int j = row.length / 2, rowLength = row.length; j < rowLength - 1; ++j) {
                        if (row[j].equals(".")) {
                            --numOfPassengersToSeat;
                        } else {
                            // the seat is occupied; try the next row
                            break;
                        }

                        // break out immediately if there are no more passengers to seat
                        if (numOfPassengersToSeat == 0) {
                            break;
                        }
                    }

                    // if there are no more passengers to seat, the row is available
                    if (numOfPassengersToSeat == 0) {
                        indexes[0] = i;
                        indexes[1] = row.length / 2;
                        indexes[2] = row.length / 2 - 1 + numOfPassengers;
                        return indexes;
                    }

                    // reset the count for the next iteration
                    numOfPassengersToSeat = numOfPassengers;
                }
                // if we've reached this point, there are no available aisle seats
                return new int[0];

            case "center":
                // for each row in the seating section ...
                for (int i = 0, seatsLength = seats.length; i < seatsLength; i++) {
                    String[] row = seats[i];
                    // check if there are there are enough available seats, beginning with the left center-left row
                    for (int rowLength = row.length, j = rowLength / 2 - 2; j >= 0; --j) {
                        if (row[j].equals(".")) {
                            --numOfPassengersToSeat;
                        } else {
                            // the seat is occupied; try the next row
                            break;
                        }

                        // break out immediately if there are no more passengers to seat
                        if (numOfPassengersToSeat == 0) {
                            break;
                        }
                    }

                    // if there are no more passengers to seat, the row is available
                    if (numOfPassengersToSeat == 0) {
                        indexes[0] = i;
                        indexes[1] = row.length / 2 - 1 - numOfPassengers;
                        indexes[2] = row.length / 2 - 2;
                        return indexes;
                    }

                    // the left center-left row is not available; reset and try left center-right row
                    numOfPassengersToSeat = numOfPassengers;

                    for (int rowLength = row.length, j = rowLength / 2 - 2; j < rowLength / 2; ++j) {
                        if (row[j].equals(".")) {
                            --numOfPassengersToSeat;
                        } else {
                            // the seat is occupied; try the next row
                            break;
                        }

                        // break out immediately if there are no more passengers to seat
                        if (numOfPassengersToSeat == 0) {
                            break;
                        }
                    }

                    // if there are no more passengers to seat, the row is available
                    if (numOfPassengersToSeat == 0) {
                        indexes[0] = i;
                        indexes[1] = row.length / 2 - numOfPassengers;
                        indexes[2] = row.length / 2 - 1;
                        return indexes;
                    }

                    // the left center-right row is not available; reset and try right center-left row
                    numOfPassengersToSeat = numOfPassengers;

                    for (int rowLength = row.length, j = rowLength / 2 + 1; j >= rowLength / 2; --j) {
                        if (row[j].equals(".")) {
                            --numOfPassengersToSeat;
                        } else {
                            // the seat is occupied; try the next row
                            break;
                        }

                        // break out immediately if there are no more passengers to seat
                        if (numOfPassengersToSeat == 0) {
                            break;
                        }
                    }

                    // if there are no more passengers to seat, the row is available
                    if (numOfPassengersToSeat == 0) {
                        indexes[0] = i;
                        indexes[1] = row.length / 2 + 2 - numOfPassengers;
                        indexes[2] = row.length - 2;
                        return indexes;
                    }

                    // the right center-left row is not available; reset and try right center-right row
                    numOfPassengersToSeat = numOfPassengers;

                    for (int rowLength = row.length, j = rowLength / 2 + 1; j < rowLength; ++j) {
                        if (row[j].equals(".")) {
                            --numOfPassengersToSeat;
                        } else {
                            // the seat is occupied; try the next row
                            break;
                        }

                        // break out immediately if there are no more passengers to seat
                        if (numOfPassengersToSeat == 0) {
                            break;
                        }
                    }

                    // if there are no more passengers to seat, the row is available
                    if (numOfPassengersToSeat == 0) {
                        indexes[0] = i;
                        indexes[1] = row.length - 1 - numOfPassengers;
                        indexes[2] = row.length - 1;
                        return indexes;
                    }

                    // reset the count for the next iteration
                    numOfPassengersToSeat = numOfPassengers;
                }
                // if we've reached this point, there are no available center seats
                return new int[0];

            case "row":
                // for each row in the seating section ...
                for (int i = 0, seatsLength = seats.length; i < seatsLength; ++i) {
                    String[] row = seats[i];
                    // check the row on the left side of the aisle to see if it is fully available
                    for (int j = 0; j < row.length / 2; ++j) {
                        if (row[j].equals(".")) {
                            --numOfPassengersToSeat;
                        }
                    }

                    // if there are no more passengers to seat, the row is available
                    if (numOfPassengersToSeat == 0) {
                        indexes[0] = i;
                        indexes[2] = row.length / 2 - 1;
                        return indexes;
                    }

                    // the left row is not available; now reset and check the right row
                    numOfPassengersToSeat = numOfPassengers;

                    for (int j = row.length / 2; j < row.length; ++j) {
                        if (row[j].equals(".")) {
                            --numOfPassengersToSeat;
                        }
                    }

                    // if there are no more passengers to seat, the row is available
                    if (numOfPassengersToSeat == 0) {
                        indexes[0] = i;
                        indexes[1] = row.length / 2;
                        indexes[2] = row.length - 1;
                        return indexes;
                    }

                    // reset the count for the next iteration
                    numOfPassengersToSeat = numOfPassengers;
                }
                // if we've reached this point, there are no available row seats
                return new int[0];

            default:
                return new int[0];
        }
    }

    /**
     * Adds a passenger to the seating section. Fills from left to right.
     *
     * @param numOfPassengers the number of passengers to seat
     * @param seatingLocation the location of the seat relative to the row (aisle, center, or window)
     */
    public void addPassengers(int numOfPassengers, String seatingLocation) {
       /*
           Get the array containing:
               - the index of the row to add,
               - the index of the column to begin adding,
               - the index of the column to stop after adding
        */
        int[] indexes = availableSeatsOfLocationType(numOfPassengers, seatingLocation);

        // for (first index to start adding; first index <= last index to add; ++index)
        for (int i = indexes[1]; i <= indexes[2]; ++i) {
            // seats[row index][column index to add] = filled
            seats[indexes[0]][i] = "X";
        }
    }

    /**
     * Returns a description of the SeatingSection as a string.
     *
     * @return a description of the SeatingSection as a string
     */
    public String toString() {
        // use StringBuilder to avoid warning of string concatenation in loops
        StringBuilder description = new StringBuilder();

        // for each row in the section...
        for (String[] row : seats) {
            ++rowCount;
            description.append(String.format("%3s", rowCount)).append(": ");

            // for each column of the seating section's row...
            for (int i = 0; i < row.length; ++i) {
                if (i == 0) {
                    description.append(row[i]);
                } else {
                    // formatting to make sure the seats are evenly displayed in text format
                    if (row.length <= 4) {
                        description.append("   ").append(row[i]);
                        if (i == row.length / 2 - 1) {
                            description.append(" ");
                        }
                    } else {
                        description.append(" ").append(row[i]);
                        if (i == row.length / 2 - 1) {
                            description.append("   ");
                        }
                    }
                }
            }
            description.append("\n");
        }

        return description.toString();
    }
}
