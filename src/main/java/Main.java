package main.java;

import java.util.ArrayList;
import java.util.Scanner;

/*
The	application should manage customers, products.txt, discounts, and orders. Customers	are	characterized by name, address,	email, telephone number, and date of birth.
Products are described by name, identifier, price per unit	and	current	stock.
There are three product categories: food, cleaning, and	furniture.
Food products.txt are also characterized by	the	number of calories/100g, and the percentage of fat.
In cleaning	products.txt,the toxicity(scale	from 0 to 10) needs to be considered.
In	furniture, the weight and dimension (height*width*depth) need also to be considered.
 */


/*
The application should allow the following operations:
    1)Login.
    2)Make an order.
    3)View previous orders
 */

/*
Text files containing data for regular customers, frequent customers, products.txt, and discounts (at least 5 items	from each category) should be provided.
The	structure of these files should enable easy	editing and parsing.
After application first	run, all data must be saved	into object	files. These object files should be loaded every time the application is run again.
To simplify application	testing, it	should be possible to change the current date. The login is made by	the	email address.
 */

/**
 * Main class that runs the application and initializes the default objects
 * Such as menus, customers, products, discounts, and orders.
 * @author Eduardo Nunes
 */
public class Main {
    /**
     * Application starts here
     * @param args given in the command line
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Date now = new Date(2, 12, 2019);
        while (true) {
            Products products = new Products();
            new Auth(sc,products,now);  //Interface for login
        }

    }
}

class Auth {
    private Customers cs;
    private Products products;
    private Date d;

    /**
     * Constructor for the Auth class that initializes the customers and prints the Authentication menu
     * @param sc Scanner object to read inputs
     *
     *
     */
    public Auth(Scanner sc,Products products, Date d){
        this.cs = new Customers();
        this.products = products;
        this.d = d;
        welcome(sc);
    }

    /**
     * Method that welcomes the user and asks for login
     *
     * In this method you can ask:
     *
     * <ul>
     *     <li>If the user wants to create a new account</li>
     *     <li>If the user wants to exit</li>
     * </ul>
     *
     * @param sc Scanner object to read inputs
     */
    private void welcome(Scanner sc) {
        System.out.println("Welcome to the Java SuperMarket Chain!");
        System.out.println("Please login to continue.");

        System.out.print("1 - Login\n2 - Exit\nInput: ");
        String choice;
        int choiceInt;

        do {
            choice = sc.nextLine();
            try {
                choiceInt = Integer.parseInt(choice);

            } catch (NumberFormatException e) {
                choiceInt = -1;
            }

            // System.out.println("chosen: " + choiceInt);
            switch (choiceInt) {
                case 1:
                    login(sc);
                    break;
                case 2:
                    System.out.println("\n\nThank you for using the Java SuperMarket Chain!");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }

        } while ((choiceInt>3 || choiceInt<1));
    }

    /**
     * Method that asks for login. If the login was successful, the loggedIn variable
     * is set to the customer and the current date is set to the date loggedIn.
     *
     * @param sc Scanner object to read inputs
     */
    private void login(Scanner sc) {
        System.out.print("Please login by entering your email: ");

        String email = sc.nextLine();
        Customer customer = this.cs.getCustomer(email); //returns a customer by entering an email

        while (customer == null) {
            customer = this.cs.getCustomer(email); //returns a customer by entering an email
            if (customer == null) {
                System.out.print("Email not found...\nPlease try again:");
                email = sc.nextLine();
            }
        }

        new LoggedIn(customer, this.d, this.products,sc);
        //agora que mudei o customer com tudo o que queria, salvo as mudanças.
        this.cs.savecustomersOBJ();
    }
}


/**
 * Class that represents a customer
 */
class LoggedIn{
    private Products availableProducts;
    private Customer customer;
    private Date loggedDate;

    public LoggedIn(Customer loggedC, Date logged,Products ap, Scanner sc) {
        this.customer = loggedC;
        this.loggedDate = logged;
        this.availableProducts = ap;

        System.out.println("Welcome " + this.customer.getName() + "!");
        System.out.println("What do you wish to do?");
        this.menu(sc);
    }

    private void menu(Scanner sc) {
        String choice;
        int choiceInt;

        while (true) {
            System.out.print("1 - Make an order\n2 - View previous orders\n3 - Logout\nInput: ");
            choice = sc.nextLine();
            try {
                choiceInt = Integer.parseInt(choice);

            } catch (NumberFormatException e) {
                continue;
            }

            switch (choiceInt) {
                case 1:
                    Order Ords = makeOrder(sc); //returns a customer with orders made.
                    this.customer.appendOrders(Ords);
                    break;
                case 2:
                    viewOrders();
                    break;
                case 3:
                    System.out.println("\n\n\n\nThank you for using the Java SuperMarket Chain!");
                    return;

                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        }
    }

    private Order makeOrder(Scanner sc) {
        System.out.println("Please enter the products you wish to order:");
        int choiceInt;
        String choice;
        ArrayList<Product> carrinho = new ArrayList<>();
        Order i;

        while(true) {
            //chose a product
            availableProducts.printProductsSelection();
            System.out.print("Input: ");


            choice = sc.nextLine();
            try {
                choiceInt = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                continue;
            }

            Product pchosen = availableProducts.getProduct(choiceInt);
            if (pchosen == null) {
                System.out.println("Invalid input. Please try again.");
            }
            else {
                carrinho.add(pchosen);
                //System.out.println(carrinho);
                this.availableProducts.removeProducts(pchosen,1);



                System.out.println("Do you want to keep ordering? (y)");
                choice = sc.nextLine();
                if (!choice.equals("y")) {
                    i = new Order(carrinho, loggedDate);

                    break;
                }
            }
        }
        return i;
    }

    private void viewOrders() {
        if (this.customer.getOrders() == null) {
            System.out.println("No previous orders.");
        } else {
            System.out.println("Previous orders for the customer "+this.customer.getName()+":");
            //still need to load order from obj
            System.out.println(this.customer.getOrders());
        }
    }

}