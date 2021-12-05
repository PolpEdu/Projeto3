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
     * Method to check how many times a given product is in the order.
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
        System.out.println(); // new line
        return result + "Total price without discounts and transport: "+ this.getDefaulttotalprice() + " euro\n" +
                "With discounts applied and no transports: "+this.withdiscountsapplied +" euro\n" +
                "Total price with discounts and transports: "+this.totalprice+" euro (transport value is "+this.transportvalue+" euro) \n";
    }
}


