package main.java;

import java.io.Serializable;

abstract class Product implements Serializable {
    private String name;
    private String identifier;
    private int currentStock;
    protected double pricePerUnit;
    protected double totalPrice;
    protected double shippingprice;


    protected Product(String name, String identifier, double pricePerUnit, int currentStock) {
        this.name = name;
        this.identifier = identifier;
        this.pricePerUnit = pricePerUnit;
        this.currentStock = currentStock;
        this.totalPrice = this.pricePerUnit + this.shippingprice;
    }

    public void removeun(int nr) {
        this.currentStock -= nr;
    }

    public String getName() {
        return this.name;
    }

    public double getShippingPrice(){
        return this.shippingprice;
    }

    protected String getBarcode() {
        return this.identifier;
    }

    protected double getPrice() {
        return this.totalPrice;
    }

    protected int getCurrentStock() {
        return this.currentStock;
    }

    public double getPricePerUnit() {
        return this.pricePerUnit;
    }

}

class FoodProduct extends Product implements Serializable{
    private int calories;
    private double percentageOfFat;

    public FoodProduct(String name, String identifier, double pricePerUnit, int currentStock, int calories, double percentageOfFat) {
        super(name, identifier, pricePerUnit, currentStock);
        this.calories = calories; // calories/100g
        if (percentageOfFat < 0 || percentageOfFat > 101) {
            throw new IllegalArgumentException("percentageOfFat must be between 0 and 100");
        }
        this.percentageOfFat = percentageOfFat;
        this.shippingprice = 0;
        this.totalPrice = this.pricePerUnit + this.shippingprice;
    }


    // Return tostring methods
    public String toString() {
        return "Food Product: " + this.getName() + " id:" + this.getBarcode() + " Total Price: "+
                this.getPrice() +" euro ("+this.getPricePerUnit()+"+"+this.getShippingPrice() +") , "+ this.getCurrentStock() + "un. " + this.calories + "/100g " + this.percentageOfFat+"%";
    }

}

class CleaningProduct extends Product implements Serializable {
    private int toxicity;

    public CleaningProduct(String name, String identifier, double pricePerUnit, int currentStock, int toxicity) {
        super(name, identifier, pricePerUnit, currentStock);

        if (toxicity < 0 || toxicity > 10) {
            throw new IllegalArgumentException("Toxicity must be between 0 and 10");
        }

        this.toxicity = toxicity;
        this.shippingprice = 0;
        this.totalPrice = this.pricePerUnit + this.shippingprice;

    }

    @Override
    public double getShippingPrice(){
        return this.shippingprice;
    }

    @Override
    public String toString() {
        return "Cleaning Product: " + this.getName() + " id:" + this.getBarcode() + " " + ", Total Price:" +
                this.getPrice() +" euro ("+this.getPricePerUnit()+"+"+this.getShippingPrice() +") - "+ this.getCurrentStock() + "un. " + this.toxicity;
    }
}

class FurnitureProduct extends Product implements Serializable {
    private double weight;
    private double height;
    private double width;
    private double depth;
    private double dimension;

    public FurnitureProduct(String name, String identifier, double pricePerUnit, int currentStock, double weight, double height,double width, double depth) {
        super(name, identifier, pricePerUnit, currentStock);
        this.weight = weight;
        this.height = height;
        this.width = width;
        this.depth = depth;
        this.dimension = height * width * depth;
        this.shippingprice = this.weight > 15 ? 10 : 0;
        this.totalPrice = this.pricePerUnit + this.shippingprice;
    }
    @Override
    public String toString() {
        return "Furniture Product: " + this.getName() + " id:" + this.getBarcode() + ", Total Price:" +
                this.getPrice() +" euro ("+this.getPricePerUnit()+"+"+this.getShippingPrice() +") - " + this.getCurrentStock() + "un. " + this.weight + " kg " + this.height + "cm " + this.width + "cm " + this.depth + "cm " + this.dimension+"cm^3";
    }

}