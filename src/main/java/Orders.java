package main.java;

import java.io.*;
import java.util.ArrayList;

class Orders implements Serializable {

    private ArrayList<Order> orders;

    public Orders() {
        this.orders = new ArrayList<>();
    }

    public int size() {
        return this.orders.size();
    }

    //add orders
    public void addOrder(Order order) {
        this.orders.add(order);
    }

    private ArrayList<Order> getOrders() {
        return this.orders;
    }


    //get total price
    public double getTotalPrice() {
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
        Date dant = orders.get(0).getDate();
        Date dasgr;
        result += "Orders for the Date: "+ dant + "\n";
        for (Order order : orders) {
            dasgr = order.getDate();

            if (!dant.equals(dasgr)) {
                result += "Orders for the Date "+dasgr+": \n";
            }

            result += order+ "\n";
        }
        return result;
    }

}

class Order implements Serializable {
    private Date orderDate;
    private ArrayList<Product> chosenProducts;
    private double totalprice;

    public Order(ArrayList<Product> chosenProducts, Date orderDate) {
        this.chosenProducts = chosenProducts;

        this.orderDate = orderDate;
        this.totalprice = calcPrice();
    }


    private double calcPrice() {
        double price = 0;
        for (Product p : this.chosenProducts) {
            price += p.getPrice();
        }
        return price;
    }

    public int size() {
        return this.chosenProducts.size();
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

    public String toString() {
        String result = "";

        for (Product p : chosenProducts) {
            result += p.getName() + ", " + p.getPrice() + " euro\n";
        }
        return result + "Total price: " + totalprice +" euro\n";
    }
}
