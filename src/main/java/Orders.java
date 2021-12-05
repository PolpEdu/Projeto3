package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Orders implements Serializable {

    private ArrayList<Order> orders;

    public Orders() {
        this.orders = new ArrayList<>();
    }

    //add orders
    public void addOrder(Order order) {
        this.orders.add(order);
    }

    private ArrayList<Order> getOrders() {
        return this.orders;
    }


    //get total price
    public double getTotalPricebyClient() {
        double totalPrice = 0;
        for (Order order : orders) {
            totalPrice += order.getPrice();
        }
        return totalPrice;
    }

    //to String method
    public String toString() {
        if (this.orders.size() == 0) {
            return "No orders\n\n";
        }

        String result = "";
        for (Order order : orders) {
            result += "\n\nOrder at: "+ order.getDate() + "\n" + order;
        }
        return result+"\n";
    }

}

/**
 * Class that contains information about an order.
 *
 * @author Eduardo Nunes
 *
 */
class Order implements Serializable {
    private ArrayList<Product> chosenProducts; // list of products chosen by the client
    private Promotions proms; // all promotions to check if the order is eligible for a promotion
    private Date orderDate; // date of the order
    private Customer orderedby; // customer that ordered the products

    private double transportvalue; // value of the transport of the order
    private double withdiscountsapplied; // value of the order with the discounts applied
    private double totalprice; // total price of the order (transport + products + discounts)

    /**
     * Constructor of the class Order.
     * The Order contains information such as:
     * <ul>
     *      <li>The products ordered in an array list with the Product type</li>
     *      <li>The order date</li>
     *      <li>The customer that made the order</li>
     *      <li>All the prices that affected the price of the order:
     *          <ul>
     *              <li>The price of the products with the discount applied</li>
     *              <li>The price of the delivery (transport)</li>
     *              <li>The total order price (price of products + price of the transport + price of the discount applied)</li>
     *          </ul>
     *      </li>
     * </ul>
     *
     * @param chosenProducts ArrayList of products ordered
     * @param orderDate Date of the order
     * @param orderedby Customer that made the order
     * @param promotions Promotions to check if the order is eligible for a promotion
     */
    public Order(ArrayList<Product> chosenProducts, Date orderDate, Customer orderedby, Promotions promotions) {
        this.chosenProducts = chosenProducts; // assign the products ordered to the chosenProducts array list
        this.orderDate = orderDate; // assign the date of the order
        this.proms = promotions; // assign the promotions to the proms variable
        this.orderedby = orderedby; // assign the customer that made the order to the orderedby variable

        this.withdiscountsapplied = this.calcPrice(); // price of the order with the discounts applied
        this.transportvalue = this.orderedby.getTransportValue(withdiscountsapplied); // value of the transport of the order
        this.totalprice = this.transportvalue+ this.withdiscountsapplied; // total price of the order (transport + products + discounts)
    }

    /**
     * Method that calculates the price of the order without any promotions or delivery prices.
     *
     * @return default price of the order
     */
    public double getDefaulttotalprice() {
        double price = 0;
        for (Product p1 : chosenProducts) {
            price += p1.getPrice();
        }
        //round price by 2 decimals
        return Math.round(price * 100.0) / 100.0;
    }

    /**
     * Method that calculates the price of the order with the discounts applied if the order is eligible for a promotion.
     *
     * @return price of the order with the discounts applied
     */
    private double calcPrice() {
        double price = 0;
        // check if there is a promotion on the current date
        Promotion p = this.proms.checkPromotions(this); // check promotion will return a specific type of promotion
        if (p != null) { // if there is a promotion at the current date
            System.out.println("Promotion on date found! Trying to apply promotion...");
            price = p.calcPrice(this); // calculate the price of the order with the promotion applied
        }
        else { // if there is no promotion at the current date
            System.out.println("Order made! However; No promotions are running on this date....");
            getDefaulttotalprice(); // calculate the default price of the order, no promotion applied
        }

        return price; // return the price of the order
    }

    /**
     * Method that returns the date of the order.
     * @return date of the order
     */
    public Date getDate() {
        return this.orderDate;
    }

    /**
     * Method that returns the price of the order with the discounts and transports applied.
     * @return total price of the order
     */
    public double getPrice() {
        return this.totalprice;
    }

    /**
     * Method that returns all the products ordered.
     * @return ArrayList of products ordered
     */
    public ArrayList<Product> getProducts() {
        return this.chosenProducts;
    }


    /**
     * Method that returns only the names of the products ordered (skipping the same products).
     * @return ArrayList of names of the products ordered
     */
    public ArrayList<Product> getProductNames() {
        // Adding only the names of the products
        ArrayList<Product> names = new ArrayList<>(); // create a new array list of products
        for (Product p : chosenProducts) { // for each product in the chosenProducts array list
            if (!names.contains(p)) { // if the product is not already in the names array list
                names.add(p); // add the product to the names array list
            }
        }
        return names; // return the names of the products ordered
    }

    /**
     * Check how many times a given product is in the order.
     *
     * @param p Product to check
     * @return number of times the given product is in the order
     */
    public int getTimesbought(Product p) {
        int times = 0;
        for (Product p1 : chosenProducts) { // for each product in the chosenProducts array list
            if (p1.getName().equals(p.getName())) { // if the product is the same as the parameter product
                times++; // increase the number of times the product is in the order
            }
        }
        return times; // return the number of times the product is in the order
    }

    /**
     * Method toString of the order.
     *
     * @return String representation of the order
     */
    public String toString() {
        String result = "";

        for (Product p : chosenProducts) {
            result += p.getName() + ", " +
                    p.getPrice() +" euro ("+p.getPricePerUnit()+"+"+p.getShippingPrice() +")\n";
        }
        System.out.println("");
        return result + "Total price without discounts and transport: "+ this.getDefaulttotalprice() + " euro\n" +
                "With discounts applied and no transports: "+this.withdiscountsapplied +" euro\n" +
                "Total price with discounts and transports: "+this.totalprice+" euro (transport value is "+this.transportvalue+" euro) \n";
    }
}

/**
 * Class that represents a customer LoggedIn.
 *
 * @author Eduardo Nunes
 */
class LoggedIn{
    private Products availableProducts; //all products in the database
    private Promotions availablePromotions; //all promotions in the database
    private Customers cs; //all customers in the database
    private Customer customer; //the customer that is logged in
    private Date loggedDate; //the date the customer logged in

    /**
     * Constructor for the LoggedIn class that initializes the customer, along with the current date,
     * available products, customers and promotions and prints the LoggedIn menu.
     *
     * @param loggedC the customer that is logged in to apply transport fees associated with the type of customer
     * @param logged Date the date that the customer logged in to check what promotions are available at that time
     * @param ap Products object to access the products that the store has to offer
     * @param sc Scanner object to read inputs
     * @param apromo promotions Promotions object to search for promotions and apply them
     * @param availableCustomers All customers object to update the customers file object
     */
    public LoggedIn(Customer loggedC, Date logged, Products ap, Scanner sc, Promotions apromo, Customers availableCustomers) {
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
     * In this method the user is asked:
     *
     * <ul>
     *     <li>If he wants to make an order</li>
     *     <li>If he wants to check is previous orders</li>
     *     <li>If he wants to Log out (exit to the previous menu)</li>
     * </ul>
     *
     * @param sc Scanner object to read inputs
     */
    private void menu(Scanner sc) {
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
                case 3:
                    System.out.println("\n\n\n\nThank you for using the Java SuperMarket Chain!");
                    return;

                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        }
    }

    /**
     * Method that allows logged in customers to make an order.
     *
     * @param sc Scanner object to read inputs
     * @return Order object with the order made
     */
    private Order makeOrder(Scanner sc) {
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
     * <p style="font-weight: bold;">There's no need to load the customers from the .obj file
     * because if the customer made orders after the customer object is constructed,
     * they are already in the customer object.</p>
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
