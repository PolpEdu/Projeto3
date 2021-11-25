import javax.xml.crypto.Data;
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
To increase sales, the company makes temporary discounts.
Each discount is associated	with a single product.
There are two types	of discounts: the pay-three-take-four and the pay-less.

In the pay-three-take-four type, customers pay three out of	four items.
For example, if a customer buys 9 units	of a product, he/she will only pay 7.

In the pay-less type, the first unit is	paid at 100%, being the	cost decreased by 5% for each additional unit, until a maximum discount	of 50% is reached.

Each order can have several	products.txt and in different quantities.
For frequent customers,	delivery is	free for orders over 40 Euros. Below this value, delivery cost is 15 Euros.

For other customers, the delivery cost is 20 Euros.
Furniture products.txt weighing more than 15 kg have a delivery cost of 10 Euros.
This cost is applicable	to all Customers.

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


public class Main {
    // Main method
    public static void main(String[] args) {
        //Interface

        Scanner sc = new Scanner(System.in);
        Products products = new Products();
        AuthInterface menu = new AuthInterface(sc);

        sc.close();

    }
}

class AuthInterface {
    private Customers cs;

    public AuthInterface(Scanner sc) {
        cs = new Customers();

        System.out.println("Welcome to the Java SuperMarket Chain!");
        System.out.println("Please register or login to continue.");

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

            // System.out.println("Escolhido: " + choiceInt);
            switch (choiceInt) {
                case 1:
                    login(sc);
                    break;
                case 2:
                    System.out.println("Thank you for using the Java SuperMarket Chain!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }

        } while ((choiceInt>3 || choiceInt<1));
    }

    private void login(Scanner sc) {
        System.out.print("Please login by entring your email: ");

        String email = sc.nextLine();
        Customer customer = this.cs.getCustomer(email); //returns a customer by entering an email

        while (customer == null) {
                customer = this.cs.getCustomer(email); //returns a customer by entering an email
                if (customer == null) {
                    System.out.print("Email not found...\nPlease try again:");
                    email = sc.nextLine();
                }
                else {
                    LoggedIn loggedIn = new LoggedIn(customer, sc);
                }
            }
        }

        /*
        private void register(Scanner sc) {
            System.out.println("Welcome! Please Register yourself:");
            Customer client = new Customer(sc);
            this.cs.addCustomer(client);
            System.out.println("\n\n\nRegistration Complete!\n");
            login(sc);
        }        */
}






class LoggedIn {
    private Customer customer;

    public LoggedIn(Customer c, Scanner sc) {
        this.customer = c;
        this.welcomemsg(sc);
    }

    private void welcomemsg(Scanner sc) {
        System.out.println("Welcome " + this.customer.getName() + "!");
        System.out.println("What do you wish to do?");

        System.out.print("1 - Make an order\n2 - View previous orders\n3 - Logout\nInput: ");
        String choice;
        int choiceInt;

        do {
            choice = sc.nextLine();
            try {
                choiceInt = Integer.parseInt(choice);

            } catch (NumberFormatException e) {
                choiceInt = -1;
            }

            switch (choiceInt) {
                case 1:
                    //makeOrder(sc);
                    break;
                case 2:
                    //viewOrders();
                    break;
                case 3:
                    break;

                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        } while ((choiceInt>3 || choiceInt<1));
    }

    //private Order makeOrder(Scanner sc) {
        //Product product = new Product();
        //Order order = new Order(this.customer, );
    //}
}