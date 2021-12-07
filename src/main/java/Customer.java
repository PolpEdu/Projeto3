package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that represents a customer LoggedIn.
 *
 * @author Eduardo Nunes
 */
class LoggedIn{
    /**
     * All products in the database
     */
    private Products availableProducts;

    /**
     * All customers in the database
     */
    private Promotions availablePromotions;

    /**
     * All orders in the database
     */
    private Customers cs;

    /**
     * The customer that is logged in
     */
    private Customer customer;

    /**
     * The {@link Date} the customer logged in
     */
    private Date loggedDate;

    /**
     * Constructor for the {@link LoggedIn} class that assigns the object {@link Customer} to the one that logged in, along with the current {@link Date},
     * available {@link Products}, all {@link Customers} and {@link Promotions} and prints the {@link #menu(Scanner) LoggedIn menu}.
     *
     * @throws IOException if the file to read the customers from is not found
     *
     * @param loggedC the customer that is logged in to apply transport fees associated with the type of customer
     * @param logged Date the date that the customer logged in to check what promotions are available at that time
     * @param ap Products object to access the products that the store has to offer
     * @param sc Scanner object to read inputs
     * @param apromo Promotions object to search for promotions and apply them
     * @param availableCustomers All customers object to update the customers file object
     */
    public LoggedIn(Customer loggedC, Date logged, Products ap, Scanner sc, Promotions apromo, Customers availableCustomers) throws IOException {
        //assign the parameters to the class variables
        this.customer = loggedC;
        this.loggedDate = logged;
        this.availableProducts = ap;
        this.availablePromotions = apromo;
        this.cs = availableCustomers;

        //print out a simple logged-in menu with the customer logged in name
        System.out.println("\n\n\nWelcome " + this.customer.getName() + "!");
        System.out.println("What do you wish to do?");
        this.menu(sc); // print out the menu
    }

    /**
     * Method that prints out the LoggedIn menu
     *
     * <p> In this method the user is asked: </p>
     *
     * <ul>
     *     <li>If he wants to make an order - {@link #menu(Scanner)}</li>
     *     <li>If he wants to check is previous orders - {@link #viewOrders()}</li>
     *     <li>If he wants to Log out (exit to the previous menu)</li>
     * </ul>
     *
     * @throws IOException if the file to read the customers from is not found
     *
     * @param sc Scanner object to read inputs
     */
    private void menu(Scanner sc) throws IOException {
        String choice; //the user's choice
        int choiceInt; //the user's choice as an integer

        while (true) {

            System.out.print("1 - Make an order\n2 - View previous orders\n3 - Logout\nInput: ");
            choice = sc.nextLine(); //read the user's choice
            try {
                choiceInt = Integer.parseInt(choice); // try to convert the choice to an integer

            } catch (NumberFormatException e) { //if the choice is not an integer
                continue; //go back to the beginning of the loop
            }

            switch (choiceInt) {
                case 1:
                    Order Ords = makeOrder(sc); //returns an Order object created by the user
                    this.customer.appendOrders(Ords); //updates all customers with the new orders
                    this.cs.savecustomersOBJ(); //saves the customers in .obj file
                    break;
                case 2:
                    viewOrders(); //prints out the customer's previous orders
                    break;

                case 3: // if the user wants to log out, return to the previous menu which is in the main class {@link Main#main(String[])}
                    System.out.println("\n\n\n\nThank you for using the Java SuperMarket Chain!");
                    return;

                default: // if the choice is not 1, 2 or 3
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        }
    }

    /**
     * Method that allows logged in customers to make an order.
     *
     * @throws IOException if we cannot read/write the products file
     *
     * @param sc Scanner object to read inputs
     * @return Order object with the order made
     */
    private Order makeOrder(Scanner sc) throws IOException {
        System.out.println("Please enter the products you wish to order:");

        String choice; //the user's choice
        int choiceInt; //the user's choice as an integer

        ArrayList<Product> carrinho = new ArrayList<>();  // list of products that the user wants to order

        Order i; //the order to be made
        while(true) {
            availableProducts.printProductsSelection(); //prints out the available products

            //asks the user to enter the product's index from the list above
            System.out.print("Input: ");
            choice = sc.nextLine();
            try {
                choiceInt = Integer.parseInt(choice); //try to convert the choice to an integer
            } catch (NumberFormatException e) { //if the choice is not an integer
                System.out.println("Invalid input. Please try again: "); //inform the user
                continue; //go back to the beginning of the loop
            }

            Product pchosen = availableProducts.getProduct(choiceInt); //get the product chosen by the user
            if (pchosen == null) { // if the product chosen is not in the list
                System.out.println("Invalid input. Please try again.");
                //go back to the beginning of the loop (i == null)
            }
            else {
                carrinho.add(pchosen); //add the product to the list of products chosen by the user

                //remove the product unit from the list of available products, so it can't be chosen again
                this.availableProducts.removeProducts(pchosen,1);

                System.out.println("Do you want to keep ordering? (y)"); // ask the user if he wants to order more products
                choice = sc.nextLine(); //read the user's choice
                if (!choice.equals("y")) { //if the user doesn't want to order more products
                    i = new Order(carrinho, loggedDate, this.customer, this.availablePromotions); //create the order
                    break; //break out of the loop
                }
            }
        }
        return i; //return the order made by the user
    }

    /**
     * Method that prints out the customer's previous orders,
     * this method checks if the customer has any previous orders.
     * If he does, it prints out the orders.
     *
     * <p style="font-weight: bolder;">On a important Note:
     * <p style="font-weight: bold;">There's no need to load the customers from the .obj file
     * because if the customer made orders after the {@link Customers#Customers() Customers is constructed},
     * they are already in the customer {@link Customer#orders} object.</p>
     *
     */
    private void viewOrders() {
        if (this.customer.getOrders() == null) { // if the customer has no orders
            System.out.println("No previous orders."); // there's nothing to print
        } else {
            System.out.println("Previous orders for the customer "+this.customer.getName()+":"); // print the customer's name

            //! There's no needing loading the orders from the file again because:
            //! we already did it on the customer's constructor and if the customer has made any orders since,
            //! the orders are already in the customer's orders list.
            System.out.println(this.customer.getOrders()); //print the orders by this customer
        }
    }

}

/**
 * Class that represents a default Customer, this class extends into different types of Customers.
 * We need more than one type of Customer since the transport value is calculated differently.
 *
 * @author Eduardo Nunes
 */
abstract class Customer implements Serializable {
    /**
     * The customer's name.
     */
    private String name;

    /**
     * The customer's address.
     */
    private String address;

    /**
     * The customer's email.
     */
    private String email;

    /**
     * The customer's phone number.
     */
    private long phoneNumber;

    /**
     * The customer's {@link Date} of birth.
     */
    private Date dateOfBirth;

    /**
     * The customer's orders.
     */
    private Orders orders;

    /**
     * The customer's transport value (which is set to 0, because the default customer has no transport value).
     */
    protected double transportValue=0;

    /**
     * Constructor of the class {@link Customer}, it initializes the {@link #name customer's name}, {@link #address}, {@link #email}, {@link #phoneNumber phone number}, {@link #dateOfBirth date of birth} and {@link #orders}.
     * It is protected since it is only used by the subclasses and the registration of new customers is not implemented.
     *
     * @throws IllegalArgumentException if the {@link #email customer's email} is invalid.
     * @throws IllegalArgumentException if the {@link #phoneNumber customer's phone number} is invalid.
     * @throws NumberFormatException if the {@link #phoneNumber customer's phone number} is not a real phone number.
     *
     * @param name The customer's name.
     * @param address The customer's address.
     * @param email The customer's email.
     * @param phoneNumber The customer's phone number.
     * @param dateOfBirth The customer's date of birth.
     */
    protected Customer(String name, String address, String email, String phoneNumber, Date dateOfBirth) throws IllegalArgumentException, NumberFormatException {
        this.name = name; // assign the name
        this.address = address; // assign the address

        if (!email.contains("@")) { // if the email doesn't contain the @ symbol, it's not valid
            throw new IllegalArgumentException("Invalid email address.");
        }
        this.email = email; // else assign the email


        if(!isValidPhoneNumber(phoneNumber)){ //check if the phone number is valid
            throw new IllegalArgumentException("Invalid phone number. On client: "+this.name);
        }
        try {
            this.phoneNumber = Long.parseLong(phoneNumber); //try to parse the phone number to a long

        } catch (NumberFormatException e) {
            throw new NumberFormatException("Couldn't parse phone number.");
        }

        this.dateOfBirth = dateOfBirth; // assign the date of birth

        this.orders = new Orders(); // create a new empty orders object and assign it to the customer
    }

    /**
     * Constructor of the class {@link Customer}, it instead of giving the class variables as arguments, it receives a {@link Scanner} object in order to the user register a {@link Customer} itself.
     * @param sc Scanner object used to read the user's inputs.
     */
    @Deprecated
    protected Customer(Scanner sc) {
        // ask for data to create a customer
        System.out.println("Enter customer name: ");
        String name = sc.nextLine();
        if (name.length() != 0) {
            this.name = name;
        }

        System.out.println("Enter customer address: ");
        this.address = sc.nextLine();



        System.out.println("Enter customer email: ");
        String email = sc.nextLine();


        while (!email.contains("@")) {
            System.out.println("Invalid email address. Please enter again: ");
            email = sc.nextLine();

        }
        this.email = email;

        System.out.println("Enter customer phone number: ");
        String phoneNumber = sc.nextLine();

        while (!isValidPhoneNumber(phoneNumber)) {
            System.out.println("Invalid phone number. Please enter again: ");
            phoneNumber = sc.nextLine();
        }
        this.phoneNumber = Long.parseLong(phoneNumber);


        System.out.println("Enter customer date of birth: ");
        this.dateOfBirth = new Date(sc); //pass the scanner in the date constructor

        System.out.println("\nCustomer created successfully:\n" + this);
    }


    /**
     * Method that checks if the phone number is valid.
     *
     * @param phoneNumber The phone number to be checked.
     *
     * @return true if the phone number is valid, false otherwise.
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 9) { // if the phone number is not 9 digits, it's not valid
            return false;
        }

        for (int i = 0; i < phoneNumber.length(); i++) { // for each character in the phone number
            if (!Character.isDigit(phoneNumber.charAt(i))) { // if the character is not a digit, it's not valid
                return false;
            }
        }
        return true;
    }

    /**
     * Method that adds an order to the customer's orders.
     *
     * @param order The order to be added.
     */
    public void appendOrders(Order order) {
        this.orders.addOrder(order); // add the order to the customer's list of orders
    }

    /**
     * Method that returns the customer's orders.
     *
     * @return The customer's orders.
     */
    public Orders getOrders() {
        return this.orders;
    }

    /**
     * Method that returns a customer's transport value depending on the type of customer.
     * This method will be overridden by the type of customers.
     *
     * @param orderValue The value of the order.
     * @return The transport value of the customer.
     */
    public double getTransportValue(double orderValue) {
        return this.transportValue;
    }

    /**
     * Method that returns the customer's email.
     *
     * @return The customer's email.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Method that returns the customer's name.
     *
     * @return The customer's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method toString of a Customer.
     *
     * @return A string representation of the customer.
     */
    @Override
    public String toString() {
        return "Name: " + name + "; Address: " + address +"; Email: " + email + "; Phone number: " + phoneNumber+ "; Date of birth: " + dateOfBirth+"\n" +
                "Orders: " + orders;
    }
}

/**
 * Subclass that represents a Regular Customer. A type of Customer.
 *
 * @author Eduardo Nunes
 */
class RegularCustomer extends Customer implements Serializable {
    /**
     * The transport value of a regular customer which is 20 for a Regular Customer.
     */
    private double TRANSPORTVALUEREGULARCUSTOMER = 20;

    /**
     * Constructor of a {@link RegularCustomer Regular Customer}. This constructor simply calls the super constructor, and sets the default of the transport value of a {@link RegularCustomer Regular Customer}.
     *
     * @param name The name of the customer.
     * @param address The address of the customer.
     * @param email The email of the customer.
     * @param phoneNumber The phone number of the customer.
     * @param dateOfBirth The date of birth of the customer.
     *
     */
    public RegularCustomer(String name, String address, String email, String phoneNumber, Date dateOfBirth) {
        super(name, address, email, phoneNumber, dateOfBirth);
        this.transportValue = TRANSPORTVALUEREGULARCUSTOMER;
    }

    /**
     * Method that returns the customer's transport value.
     *
     * @param orderValue The value of the order.
     * @return The transport value of the customer.
     */
    @Override
    public double getTransportValue(double orderValue) {
        return this.transportValue;
    }

    /**
     * Method that returns a string representation of a Regular Customer.
     *
     * @return A string representation of a Regular Customer.
     */
    @Override
    public String toString() {
        return "Regular customer: " + super.toString();
    }
}

/**
 * Subclass that represents a Frequent Customer. A type of Customer.
 *
 * @author Eduardo Nunes
 */
class FrequentCustomer extends Customer implements Serializable {
    /**
     * Constructor of a {@link FrequentCustomer Frequent Customer}. This constructor simply calls the super constructor, and sets the default of the transport value of a {@link FrequentCustomer Frequent Customer}.
     *
     * @param name The name of the customer.
     * @param address The address of the customer.
     * @param email The email of the customer.
     * @param phoneNumber The phone number of the customer.
     * @param dateOfBirth The date of birth of the customer.
     */
    public FrequentCustomer(String name, String address, String email, String phoneNumber, Date dateOfBirth) {
        super(name, address, email, phoneNumber, dateOfBirth);
    }

    /**
     * Method that calculates and sets the customer's transport value for a Frequent Customer based on his order value.
     *
     * @param orderValue The value of the order.
     */
    private void calctransportValue(double orderValue) {
        this.transportValue = orderValue > 40 ? 0.0 : 15.0; // If the order value is greater than 40, the transport value is 0.0, otherwise, it is 15.0.
    }

    /**
     * Method that calculates and then returns the Frequent Customer's transport value.
     *
     * @param orderValue The value of the order.
     * @return The transport value of the Frequent Customer.
     */
    @Override
    public double getTransportValue(double orderValue) {
        calctransportValue(orderValue); //assign correct value to transportValue
        return this.transportValue;
    }

    /**
     * Method that returns a string representation of a Frequent Customer.
     *
     * @return A string representation of a Frequent Customer.
     */
    @Override
    public String toString() {
        return "Frequent customer: " + super.toString();
    }
}


