package main.java;

import java.io.*;
import java.util.ArrayList;

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
