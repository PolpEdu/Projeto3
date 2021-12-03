package main.java;

import java.io.Serializable;

abstract class Product implements Serializable {
    private String name;
    private String identifier;
    private double pricePerUnit;
    private int currentStock;

    protected Product(String name, String identifier, double pricePerUnit, int currentStock) {
        this.name = name;
        this.identifier = identifier;
        this.pricePerUnit = pricePerUnit;
        this.currentStock = currentStock;
    }

    public void removeun(int nr) {
        this.currentStock -= nr;
    }

    public String getName() {
        return this.name;
    }

    protected String getBarcode() {
        return this.identifier;
    }

    protected double getPrice() {
        return this.pricePerUnit;
    }

    protected int getCurrentStock() {
        return this.currentStock;
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
    }

    // Return tostring methods
    public String toString() {
        return "FoodProduct: " + this.getName() + " id:" + this.getBarcode() + " " + this.getPrice() + " euro " + this.getCurrentStock() + "un. " + this.calories + "/100g " + this.percentageOfFat+"%";
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
    }

    public String toString() {
        return "CleaningProduct: " + this.getName() + " id:" + this.getBarcode() + " " + this.getPrice() + " euro " + this.getCurrentStock() + "un. " + this.toxicity;
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
    }

    public String toString() {
        return "FurnitureProduct: " + this.getName() + " id:" + this.getBarcode() + " " + this.getPrice() + " euro " + this.getCurrentStock() + "un. " + this.weight + "kg " + this.height + "cm " + this.width + "cm " + this.depth + "cm " + this.dimension+"cm^3";
    }

}