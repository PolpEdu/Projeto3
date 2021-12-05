package main.java;
import java.util.Scanner;

/**
 * Main class that runs the application and initializes the default objects,
 * such as menus, customers, products, discounts, and orders.
 * @author Eduardo Nunes
 */
public class Main {
    /**
     * Application starts here
     * @param args Given in the command line
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in); // Scanner for user input
        Date now = new Date(5, 12, 2022); // Current date (change for testing)

        // loop where the application runs
        while (true) { // Loop until user from inside the loop
            Products products = new Products(); // load Products object
            Promotions promotions = new Promotions(); // load Promotions object


            new Auth(sc,products,now,promotions);  //Interface for login
        }
    }
}

/**
 * Class that handles the authentication of the application,
 * here the user is asked to enter his/her email and redirected to the logged In menu.
 *
 * @author Eduardo Nunes
 */
class Auth {
    private Customers cs; //all customers in the database
    private Products products; //all products in the database
    private Promotions promotions; //all promotions in the database
    private Date d; //current date

    /**
     * Constructor for the Auth class that initializes the customers and prints the Authentication menu
     *
     * @param sc Scanner object to read inputs
     * @param products Products object that contains all products in the database
     * @param d Date object that contains the current date
     * @param promotions Promotions object that contains all promotions in the database
     */
    public Auth(Scanner sc,Products products, Date d, Promotions promotions) {
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
     *
     * <ul>
     *     <li>If the user wants to log in</li>
     *     <li>If the user wants to exit</li>
     * </ul>
     *
     * @param sc Scanner object to read inputs
     */
    private void welcome(Scanner sc) {
        // welcome message
        System.out.println("\nWelcome to the Java SuperMarket Chain!\n" +
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
                    System.out.println("\n\nThank you for using the Java SuperMarket Chain!"); //inform the user that the application is closing
                    sc.close(); // close the scanner
                    System.exit(0); // exit the application
                default: // if the user input is not valid
                    System.out.print("Invalid input. Please try again: "); // inform the user
                    break; // redo the loop
            }

        } while ((choiceInt>3 || choiceInt<1)); // loop until the user enters a valid input, which is 1 or 2 (if choiceInt is -1 the loop will be redone)
    }

    /**
     * Method that asks for login. If the login was successful, the loggedIn variable
     * is set to the customer and the current date is set to the date loggedIn.
     *
     * @param sc Scanner object to read inputs
     */
    private void login(Scanner sc) {
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
