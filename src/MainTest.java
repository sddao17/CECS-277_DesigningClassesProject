
/**
 * @author Alexander Loo, Phoenix Ngan, Steven Dao
 * @version 1.0
 * Due Date: March 3, 2021, 2:00pm
 *
 * Purpose: To demonstrate designing comprehension by writing a program that assigns seats in an airplane.
 *
 *        * Assume the airplane has 20 seats in first class (5 rows of 4 seats each, separated by an aisle)
 *          and 90 seats in economy class (15 rows of 6 seats each, separated by an aisle).
 *        * The program should take three commands: add passengers, show seating, and quit.
 *        * When passengers are added, ask for
 *            - the class (first or economy),
 *            - the number of passengers traveling together (1 or 2 in first class; 1 to 3 in economy),
 *            - and the seating preference (aisle or window in first class; aisle, center, or window in economy).
 *        * Then try to find a match and assign the seats.
 *        * If no match exists, print a message.
 *        * Print an error message if the user enters an incorrect value.
 *        * Allow the user to re-enter a value if an incorrect value was entered.
 *        * The solution should include a class Airplane that is not coupled with the Scanner or PrintStream classes.
 *
 * Target Output: Demonstrate the concepts above by being able to alter and display the seats of the airplane
 *                  in text format.
 */
public class MainTest {

    /**
     * Tests methods of Airplane and CheckInput.
     *
     * @param args command-line arguments for the application of type String array
     */
    public static void main(String[] args) {

        PrintMessage.welcome();

        // testing overloaded constructor
        Airplane testAirplane = new Airplane(5, 4, 15, 6);

        // testing CheckInput methods - below are the variables which store input
        String option;
        String seatingLocation;
        int numOfPassengers;

        // begin testing all methods
        do {
            PrintMessage.addShowQuitOptions();
            option = CheckInput.checkAddShowQuit();

            // if adding passengers, check if the plane is full first
            if (option.equals("add") && !testAirplane.isFull()) {

                PrintMessage.firstEconomyOptions();
                option = CheckInput.checkFirstEconomy();

                if (option.equals("first")) {
                    // check to see if the section is full first
                    if (testAirplane.isFullInSection("first")) {
                        PrintMessage.sectionIsFullError();
                    } else {
                        PrintMessage.firstClassPassengerOptions();
                        numOfPassengers = CheckInput.checkIntRange(1, 2);

                        // check if number of seats in a row matches the number of passengers
                        if (testAirplane.canFillRowInSection("first", numOfPassengers)) {
                            // check to see if there exists a row that can actually be filled
                            if (testAirplane.availableSeatsOfLocationType
                                    ("first", numOfPassengers, "row").length > 0) {
                                // test addPassengers()
                                testAirplane.addPassengers("first", numOfPassengers, "row");
                            } else {
                                PrintMessage.noFullyAvailableRowsError();
                            }
                        } else {
                            // check if there are enough available seats to seat the passenger(s)
                            if (testAirplane.hasAvailableSeatsInSection("first", numOfPassengers)) {
                                // validate the input for aisle / window seating options
                                PrintMessage.aisleWindowOptions();
                                seatingLocation = CheckInput.checkAisleWindow();

                                // check to see if there are enough matching seats of the specified location type
                                if (testAirplane.availableSeatsOfLocationType
                                        ("first", numOfPassengers, seatingLocation).length > 0) {
                                    // test addPassengers()
                                    testAirplane.addPassengers("first", numOfPassengers, seatingLocation);
                                } else {
                                    PrintMessage.notEnoughAvailableSeatsOfLocationError(seatingLocation);
                                }
                            } else {
                                PrintMessage.notEnoughAvailableSeatsError();
                            }
                        }
                    }
                } else if (option.equals("economy")) {
                    // check to see if the section is full first
                    if (testAirplane.isFullInSection("economy")) {
                        PrintMessage.sectionIsFullError();
                    } else {
                        PrintMessage.economyClassPassengerOptions();
                        numOfPassengers = CheckInput.checkIntRange(1, 3);

                        // check if number of seats in a row matches the number of passengers
                        if (testAirplane.canFillRowInSection("economy", numOfPassengers)) {
                            // check to see if there exists a row that can actually be filled
                            if (testAirplane.availableSeatsOfLocationType
                                    ("economy", numOfPassengers, "row").length > 0) {
                                // test addPassengers()
                                testAirplane.addPassengers("economy", numOfPassengers, "row");
                            } else {
                                PrintMessage.noFullyAvailableRowsError();
                            }
                        } else {
                            // check if there are enough available seats to seat the passenger(s)
                            if (testAirplane.hasAvailableSeatsInSection("economy", numOfPassengers)) {
                                PrintMessage.aisleCenterWindowOptions();
                                seatingLocation = CheckInput.checkAisleCenterWindow();

                                // check to see if there are enough matching seats of the specified location type
                                if (testAirplane.availableSeatsOfLocationType
                                        ("economy", numOfPassengers, seatingLocation).length > 0) {
                                    // test addPassengers()
                                    testAirplane.addPassengers("economy", numOfPassengers, seatingLocation);
                                } else {
                                    PrintMessage.notEnoughAvailableSeatsOfLocationError(seatingLocation);
                                }

                            } else {
                                PrintMessage.notEnoughAvailableSeatsError();
                            }
                        }
                    }
                }
            } else if (option.equals("add") && testAirplane.isFull()) {
                PrintMessage.airplaneIsFullError();
            } else if (option.equals("show")) {
                // testing toString()
                PrintMessage.display(testAirplane);
            }
        } while (!option.equals("quit"));


        // testing is done
        PrintMessage.goodbye();
    }

    /**
     * Handles all instances of needing to use System.out.println() for printing messages.
     */
    private static class PrintMessage {

        /**
         * Shows a welcome message.
         */
        private static void welcome() {
            System.out.println("--------------------------------------------");
            System.out.println("        Welcome to Cloud-9 Airlines.");
            System.out.println("--------------------------------------------\n");
        }

        /**
         * Show the options for adding passengers / showing the seating display / quitting the program.
         */
        private static void addShowQuitOptions() {
            System.out.println("Please choose between these options:\n" +
                               "\tA)dd passengers\n" +
                               "\tS)how display\n" +
                               "\tQ)uit");
        }

        /**
         * Show the options for firstClass / economyClass seats.
         */
        private static void firstEconomyOptions() {
            System.out.println("Select a seating section:\n" +
                               "\tF)irst\n" +
                               "\tE)conomy");
        }

        /**
         * Show the options for number of passengers for firstClass.
         */
        private static void firstClassPassengerOptions() {
            System.out.println("How many passengers?\t(1 - 2)");
        }

        /**
         * Show the options for number of passengers for economyClass.
         */
        private static void economyClassPassengerOptions() {
            System.out.println("How many passengers?\t(1 - 3)");
        }

        /**
         * Show the options for aisle / window seats.
         */
        private static void aisleWindowOptions() {
            System.out.println("Select a seating location:\n" +
                               "\tA)isle\n" +
                               "\tW)indow");
        }

        /**
         * Show the options for aisle / center / window seats.
         */
        private static void aisleCenterWindowOptions(){
            System.out.println("Select a seating location:\n" +
                               "\tA)isle\n" +
                               "\tC)enter\n" +
                               "\tW)indow");
        }

        /**
         * Displays an error message that the SeatingSection is completely full.
         */
        private static void sectionIsFullError() {
            System.out.println("This seating section is completely full; please try another section.");
        }

        /**
         * Displays an error message that all seats of the Airplane are completely full.
         */
        private static void airplaneIsFullError() {
            System.out.println("All seats are full. Please try again another time.");
        }

        /**
         * Displays an error message that there are not enough available seats.
         */
        private static void notEnoughAvailableSeatsError() {
            System.out.println("There aren't enough available seats; please try again.");
        }

        /**
         * Displays an error message that there are not enough available seats of the specified location type.
         */
        private static void notEnoughAvailableSeatsOfLocationError(String seatingLocation) {
            System.out.println("There aren't enough " + seatingLocation + " seats; " + "please try again.");
        }

        /**
         * Displays an error message that there are no fully available rows.
         */
        private static void noFullyAvailableRowsError() {
            System.out.println("There are no fully available rows; please try again.");
        }

        /**
         * Prints an object using its toString() method.
         */
        private static void display(Object obj) {
            System.out.println(obj);
        }

        /**
         * Shows a goodbye message.
         */
        private static void goodbye() {
            System.out.println("\n-------------------------------------------");
            System.out.println("  Goodbye, and thanks for flying with us!");
            System.out.println("-------------------------------------------");
        }
    }
}
