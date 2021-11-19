import java.util.Scanner;

/*
The	application should manage customers, products, discounts, and orders. Customers	are	characterized by name, address,	email, telephone number, and date of birth.
Products are described by name, identifier, price per unit	and	current	stock.
There are three product categories: food, cleaning, and	furniture.
Food products are also characterized by	the	number of calories/100g, and the percentage of fat.
In cleaning	products,the toxicity(scale	from 0 to 10) needs to be considered.
In	furniture, the weight and dimension (height*width*depth) need also to be considered.
 */

/*
To increase sales, the company makes temporary discounts.
Each discount is associated	with a single product.
There are two types	of discounts: the pay-three-take-four and the pay-less.

In the pay-three-take-four type, customers pay three out of	four items.
For example, if a customer buys 9 units	of a product, he/she will only pay 7.

In the pay-less type, the first unit is	paid at 100%, being the	cost decreased by 5% for each additional unit, until a maximum discount	of 50% is reached.

Each order can have several	products and in different quantities.
For frequent customers,	delivery is	free for orders over 40 Euros. Below this value, delivery cost is 15 Euros.

For other customers, the delivery cost is 20 Euros.
Furniture products weighing more than 15 kg have a delivery cost of 10 Euros.
This cost is applicable	to all Customers.

 */

/*
The application should allow the following operations:
    1)Login.
    2)Make an order.
    3)View previous orders
 */

/*
Text files containing data for regular customers, frequent customers, products, and discounts (at least 5 items	from each category) should be provided.
The	structure of these files should enable easy	editing and parsing.
After application first	run, all data must be saved	into object	files. These object files should be loaded every time the application is run again.
To simplify application	testing, it	should be possible to change the current date. The login is made by	the	email address.
 */


public class Main {
    // Main method
    public static void main(String[] args) {
        //Interface
        DataBase db = new DataBase(); //loads customers from .obj file in to memory
        AuthInterface menu = new AuthInterface();


    }
}

class AuthInterface extends Customers{

    public AuthInterface() {
        System.out.println("Welcome to the Java SuperMarket Chain!");
        System.out.println("Please register or login to continue.");

        Scanner input = new Scanner(System.in);
        System.out.print(" 1 - Register\n 2 - Login\n 3 - Exit\nInput: ");
        int choice = input.nextInt();

        do {

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Thank you for using the Java SuperMarket Chain!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }

        } while (choice>3 || choice<1);

    }

    private void login() {
        System.out.print("Please login by using your email:");

    }

    private void register() {
        System.out.println("Welcome! Please Register yourself:");
        Customer client = new Customer();
        super.addCustomer(client);


        System.out.println("Registration Complete!\n");
        login();
    }

}
