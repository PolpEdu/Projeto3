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

class Order implements Serializable {
    private ArrayList<Product> chosenProducts;
    private Promotions proms;
    private Date orderDate;
    private Customer orderedby;

    private double transportvalue;
    private double withdiscountsapplied;
    private double totalprice;

    public Order(ArrayList<Product> chosenProducts, Date orderDate, Customer orderedby, Promotions promotions) {
        this.chosenProducts = chosenProducts;
        this.orderDate = orderDate;
        this.proms = promotions;
        this.orderedby = orderedby;

        this.withdiscountsapplied = this.calcPrice(); //with discounts applied
        this.transportvalue = this.orderedby.getTransportValue(withdiscountsapplied);
        this.totalprice = this.transportvalue+ this.withdiscountsapplied;
    }

    public double getDefaulttotalprice() {
        double price = 0;
        for (Product p1 : chosenProducts) {
            price += p1.getPrice();
        }
        //round price by 2 decimals
        return Math.round(price * 100.0) / 100.0;
    }

    private double calcPrice() {
        double price = 0;
        // check if there is a promotion on the current date
        Promotion p = this.proms.checkPromotions(this);
        if (p != null) {
            System.out.println("Promotion on date found! Trying to apply promotion...");
            price = p.calcPrice(this);
        }
        else {
            System.out.println("Order made! However; No promotions are running on this date....");
            getDefaulttotalprice();
        }

        return price;
    }

    public Date getDate() {
        return this.orderDate;
    }

    public double getPrice() {
        return this.totalprice;
    }

    public ArrayList<Product> getProducts() {
        return this.chosenProducts;
    }

    public ArrayList<Product> getProductNames() {
        //add only the names of the products
        ArrayList<Product> names = new ArrayList<>();
        for (Product p : chosenProducts) {
            if (!names.contains(p)) {
                names.add(p);
            }
        }
        return names;
    }

    public int getTimesbought(Product p) {
        int times = 0;
        for (Product p1 : chosenProducts) {
            if (p1.getName().equals(p.getName())) {
                times++;
            }
        }
        return times;
    }

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
                    this.cs.savecustomersOBJ();
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
                System.out.println("Invalid input. Please try again: ");
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
                    i = new Order(carrinho, loggedDate, this.customer, this.availablePromotions);
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
