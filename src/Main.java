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


public class Main {
    // Main method
    public static void main(String[] args) {
        //Interface

        Scanner sc = new Scanner(System.in);

        Products products = new Products();
        Auth initauth = new Auth(sc);
        new LoggedIn(initauth.getLoggedIn(), initauth.getLoggedDate(), sc);

    }
}

class Auth {
    private Customers cs;
    private Customer loggedIn;
    private Date now;

    public Auth(Scanner sc) {
        this.cs = new Customers();
        welcome(sc);
    }

    //Welcome message which is used to redirect the user to the login page
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
                    System.out.println("Thank you for using the Java SuperMarket Chain!");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }

        } while ((choiceInt>3 || choiceInt<1));
    }

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
        this.loggedIn = customer;
        this.now = new Date(27,12,2020);
    }

    public Customer getLoggedIn(){
        return this.loggedIn;
    }

    public Date getLoggedDate(){
        return this.now;
    }
}






class LoggedIn {
    private Customer customer;
    private Orders orders;
    private Products availableProducts;
    private Date loggedDate;

    public LoggedIn(Customer c, Date logged,Products ap, Scanner sc) {
        this.customer = c;
        this.loggedDate = logged;
        this.availableProducts = ap;
        this.orders = new Orders(customer);

        System.out.println("Welcome " + this.customer.getName() + "!");
        System.out.println("What do you wish to do?");
        this.menu(sc);
    }

    private void menu(Scanner sc) {
        System.out.print("1 - Make an order\n2 - View previous orders\n3 - Logout\nInput: ");
        String choice;
        int choiceInt;

        while (true) {
            choice = sc.nextLine();
            try {
                choiceInt = Integer.parseInt(choice);

            } catch (NumberFormatException e) {
                choiceInt = -1;
            }

            switch (choiceInt) {
                case 1:
                    makeOrder(sc);
                    break;
                case 2:
                    viewOrders();
                    break;
                case 3:
                    System.out.println("Thank you for using the Java SuperMarket Chain!");
                    return;

                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        }
    }

    private void makeOrder(Scanner sc) {
        System.out.println("Please enter the products you wish to order:");

        int len = this.availableProducts.getProductslen();

        do {
            //chose a product
            availableProducts.printProductsSelection();
            System.out.print("Input: ");

            String choice;
            choice = sc.nextLine();


        } while (true);

    }

    private void viewOrders() {
        System.out.println("Previous orders for the customer "+this.customer.getName()+":");
        System.out.println(this.orders);
    }

}