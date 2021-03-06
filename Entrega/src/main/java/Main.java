package main.java;
import java.util.Scanner;

/**
 * Main class that runs the application and initializes the default objects,
 * such as initial {@link Auth Authentication Menu}, {@link Products}, {@link Promotions}, current time (by a class {@link Date}) and {@link Scanner} to read inputs.
 * It also contains the main loop that keeps the application running until the user decides to exit.
 *
 * @author Eduardo Nunes
 */
public class Main {
    /**
     * Application Main Loop starts here. It initializes the default objects and asks the user for his credentials.
     * If the user decides to logout, the loop restarts and asks for credentials again.
     *
     * @throws Exception If we can't read/write to the files of our application
     *
     * @param args Given in the command line
     */
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in); // Scanner for user input
        Date now = new Date(21, 12, 2022); // Current date (change for testing)

        // loop where the application runs
        while (true) { // Loop until user from inside the loop
            Products products = new Products(); // load Products object
            Promotions promotions = new Promotions(); // load Promotions object


            new Auth(sc,products,now,promotions);  //Interface for login
            //if the user decides to Logout, we will return to the top of the loop and ask for a new login credentials
        }
    }
}

/**
 * Class that handles the authentication of the application,
 * here the user is asked to enter his/her email and, when the login is successful will be redirected to the logged In menu.
 *
 * @author Eduardo Nunes
 */
class Auth {
    /**
     * All customers in the database
     */
    private Customers cs;

    /**
     * All products in the database
     */
    private Products products;

    /**
     * All promotions in the database
     */
    private Promotions promotions;

    /**
     * Current date
     */
    private Date d;

    /**
     * Constructor for the {@link Auth} class that initializes the customers {@link Customers} and prints the Authentication menu {@link Auth#welcome(Scanner)}.
     *
     * @throws Exception If we can't read/write to the files of our application
     *
     * @param sc Scanner object to read inputs
     * @param products Products object that contains all products in the database
     * @param d Date object that contains the current date
     * @param promotions Promotions object that contains all promotions in the database
     */
    public Auth(Scanner sc,Products products, Date d, Promotions promotions) throws Exception {
        this.cs = new Customers(); //initialize customers
        this.products = products; // assign the products given as parameter to the products object of the Auth object
        this.promotions = promotions; // assign the promotions given as parameter to the promotions object of the Auth object
        this.d = d; // assign the given date to the date object of the Auth class
        this.welcome(sc); // print the Authentication menu
    }

    /**
     * Method that welcomes the user and asks for login
     *
     * In this method the user is asked:
     * <ul>
     *     <li>If the user wants to log in, {@link #login(Scanner)}</li>
     *     <li>If the user wants to exit</li>
     * </ul>
     *
     * @throws Exception If we can't read/write to the files of our application
     *
     * @param sc Scanner object to read inputs
     */
    private void welcome(Scanner sc) throws Exception {
        // welcome message
        System.out.println("Welcome to the Java SuperMarket Chain!\n" +
                "Today's date is: "+ this.d+"\nPlease login to continue.\n");

        System.out.print("1 - Login\n2 - Exit\nInput: ");
        String choice; // user input
        int choiceInt; // user input converted to int

        do {  // loop where the user is asked to log in or exit
            choice = sc.nextLine(); // read user input
            try {
                choiceInt = Integer.parseInt(choice); // try to parse the user input to an int

            } catch (NumberFormatException e) { // if it's not successfully parsed to an int
                choiceInt = -1; // set choiceInt to -1 to indicate that it is not a valid input and redo the loop
            }

            //? System.out.println("chosen: " + choiceInt);
            switch (choiceInt) { // switch statement to handle the user input
                case 1: // if the user wants to log in
                    login(sc);
                    break;
                case 2: // if the user wants to exit
                    System.out.println("\n\n\nThank you for using the Java SuperMarket Chain!"); //inform the user that the application is closing
                    sc.close(); // close the scanner
                    System.exit(0); // exit the application
                default: // if the user input is not valid
                    System.out.print("Invalid input. Please try again: "); // inform the user
                    break; // redo the loop
            }

        } while ((choiceInt>3 || choiceInt<1)); // loop until the user enters a valid input, which is 1 or 2 (if choiceInt is -1 the loop will be redone)
    }

    /**
     * Method that asks for login.
     * <p>If the login was successful, creates a {@link LoggedIn Logged In Object} which contains the customer and {@link Date date} information</p>
     *
     * @throws Exception If we can't read our customers from file
     *
     * @param sc Scanner object to read inputs
     */
    private void login(Scanner sc) throws Exception {
        System.out.print("Please login by entering your email: ");

        String email = sc.nextLine(); //ask for the next line
        Customer customer = this.cs.getCustomer(email); // try to get a user with the given email

        while (customer == null) { //if the email does not exist in the users' database, keep asking for another email
            System.out.print("Email not found...\nPlease try again:"); // print out a simple message informing the user that the email was not found
            email = sc.nextLine(); // ask for the next line
            customer = this.cs.getCustomer(email); // try to get a user with the given email
        }

        // finally, when the user successfully logged in, create a loggedIn Object and set the user parameter to the loggedIn customer that we created along with the applicable promotions, products and users.
        new LoggedIn(customer, this.d, this.products,sc,this.promotions, this.cs);

        // if we reach here, then the user logged out.
    }
}
