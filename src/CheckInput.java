
import java.util.Scanner;

/**
 * Validates the user's input.
 */
public class CheckInput {

    /**
     * Checks if the inputted value is an integer and
     * within the specified range (ex: 1-10)
     * Modified from CheckInput.java.
     *
     * @param low  lower bound of the range.
     * @param high upper bound of the range.
     * @return the valid input.
     */
    public static int checkIntRange(int low, int high) {
        Scanner in = new Scanner(System.in);
        int input = 0;
        boolean valid = false;

        while (!valid) {
            if (in.hasNextInt()) {
                input = in.nextInt();
                if (input <= high && input >= low) {
                    valid = true;
                } else {
                    System.out.print("Invalid range; please try again: ");
                }
            } else {
                in.next(); //clear invalid string
                System.out.print("Invalid input; please try again: ");
            }
        }
        return input;
    }

    /**
     * Takes an option as a string and returns add, show, or quit.
     *
     * @return a string as add, show, or quit.
     */
    public static String checkAddShowQuit() {
        Scanner in = new Scanner(System.in);
        String input;

        do {
            input = in.nextLine();
            if (input.equalsIgnoreCase("a")) {
                return "add";
            } else if (input.equalsIgnoreCase("s")) {
                return "show";
            } else if (input.equalsIgnoreCase("q")) {
                return "quit";
            } else {
                System.out.print("Invalid input; please try again: ");
            }
        } while (true);
    }

    /**
     * Takes an option as a string and returns first or economy.
     *
     * @return a string as first or economy.
     */
    public static String checkFirstEconomy() {
        Scanner in = new Scanner(System.in);
        String input;

        do {
            input = in.nextLine();
            if (input.equalsIgnoreCase("f")) {
                return "first";
            } else if (input.equalsIgnoreCase("e")) {
                return "economy";
            } else {
                System.out.print("Invalid input; please try again: ");
            }
        } while (true);
    }

    /**
     * Takes an option as a string and returns aisle or window.
     *
     * @return a string as aisle or window.
     */
    public static String checkAisleWindow() {
        Scanner in = new Scanner(System.in);
        String input;

        do {
            input = in.nextLine();
            if (input.equalsIgnoreCase("a")) {
                return "aisle";
            } else if (input.equalsIgnoreCase("w")) {
                return "window";
            } else {
                System.out.print("Invalid input; please try again: ");
            }
        } while (true);
    }

    /**
     * Takes an option as a string and returns aisle, center, or window.
     *
     * @return a string as aisle, center, or window.
     */
    public static String checkAisleCenterWindow() {
        Scanner in = new Scanner(System.in);
        String input;

        do {
            input = in.nextLine();
            if (input.equalsIgnoreCase("a")) {
                return "aisle";
            } else if (input.equalsIgnoreCase("c")) {
                return "center";
            } else if (input.equalsIgnoreCase("w")) {
                return "window";
            } else {
                System.out.print("Invalid input; please try again: ");
            }
        } while (true);
    }
}
