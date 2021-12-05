package main.java;

import java.io.Serializable;

/**
 * Class that represents a usual Product, this class extends into different types of Products.
 *
 * @author Eduardo Nunes
 */
abstract class Product implements Serializable {
    /**
     * Name of the product
     */
    private String name;

    /**
     * Identifier of the product (barcode)
     */
    private String identifier;

    /**
     * Current stock of the product
     */
    private int currentStock;

    /**
     * Price per unit of the product - this variable is protected because it is used in the different types of products
     */
    protected double pricePerUnit;

    /**
     *  Total price of the product - this variable is protected because it is used in the different types of products
     */
    protected double totalPrice;

    /**
     * Shipping price associated with the product - this variable is protected because it is used in the different types of products
     */
    protected double shippingprice;


    /**
     * Constructor of the class {@link Product}, it initializes the {@link #name}, {@link #identifier}, {@link #currentStock current stock}, {@link #pricePerUnit price per unit} and {@link #totalPrice total price of the product}.
     *
     * @param name Name of the product
     * @param identifier Identifier of the product (barcode)
     * @param pricePerUnit Price per unit of the product
     * @param currentStock Current stock of the product
     */
    protected Product(String name, String identifier, double pricePerUnit, int currentStock) {
        this.name = name; // assigns the name of the product
        this.identifier = identifier; // assigns the identifier of the product
        this.pricePerUnit = pricePerUnit; // assigns the price per unit of the product
        this.currentStock = currentStock; // assigns the current stock of the product
        this.totalPrice = this.pricePerUnit + this.shippingprice; // assigns the total price of the product (price per unit + shipping price (that will change depending on the type of customer ordering))
    }

    /**
     * Method that updates the current stock of the product by removing the quantity of the product that was ordered in the {@link #currentStock} variable.
     * Usually called by {@link Products#removeProducts} method to remove one from the stock
     *
     * @param nr Number of units to be removed from the current stock
     */
    public void removeun(int nr) {
        this.currentStock -= nr;
    }

    /**
     * Method that returns the name of the product.
     *
     * @return Name of the product
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method that returns the shipping price of the product. This method is used in the different types of products.
     *
     * @return Shipping price of the product
     */
    public double getShippingPrice(){
        return this.shippingprice;
    }

    /**
     * Method that returns the identifier of the product (barcode).
     *
     * @return Identifier of the product
     */
    protected String getBarcode() {
        return this.identifier;
    }

    /**
     * Method that returns the current stock of the product.
     *
     * @return Current stock of the product
     */
    protected double getPrice() {
        return this.totalPrice;
    }

    /**
     * Method that returns the current stock of the product.
     *
     * @return Current stock of the product
     */
    protected int getCurrentStock() {
        return this.currentStock;
    }

    /**
     * Method that returns the price per unit of the product.
     *
     * @return Price per unit of the product
     */
    public double getPricePerUnit() {
        return this.pricePerUnit;
    }

}

/**
 * Class that represents a product associated with Food. This class contains specific attributes of a product associated with Food.
 *
 * @author Eduardo Nunes
 */
class FoodProduct extends Product implements Serializable{
    /**
     * Calories of the product/100g
     */
    private int calories;

    /**
     * Percentage of fat of the product
     */
    private double percentageOfFat;

    /**
     * Constructor of the class {@link FoodProduct Food Product}, it along with the initializer of the superclass {@link Product}, initializes the calories and percentage of fat of the product (two specific attributes of this class).
     *
     * @throws IllegalArgumentException If the percentage of fat is not between 0 and 100
     *
     * @param name Name of the product
     * @param identifier Identifier of the product (barcode)
     * @param pricePerUnit Price per unit of the product
     * @param currentStock Current stock of the product
     * @param calories Calories of the product (class specific attribute)
     * @param percentageOfFat Percentage of fat of the product (class specific attribute)
     */
    public FoodProduct(String name, String identifier, double pricePerUnit, int currentStock, int calories, double percentageOfFat) {
        super(name, identifier, pricePerUnit, currentStock); // initializes the superclass Product
        this.calories = calories; // assigns the calories of the product
        if (percentageOfFat < 0 || percentageOfFat > 101) { // checks if the percentage of fat is valid
            throw new IllegalArgumentException("percentageOfFat must be between 0 and 100");
        }
        this.percentageOfFat = percentageOfFat; // assigns the percentage of fat of the product
        this.shippingprice = 0; // assigns the shipping price of the product to 0 (food products do not have shipping costs)
        this.totalPrice = this.pricePerUnit + this.shippingprice; // assigns the total price of the product (price per unit + shipping price)
    }

    /**
     * Method toString of a Food Product.
     *
     * @return String representation of a Food Product
     */
    public String toString() {
        return "Food Product: " + this.getName() + " id:" + this.getBarcode() + " Total Price: "+
                this.getPrice() +" euro ("+this.getPricePerUnit()+"+"+this.getShippingPrice() +") , "+ this.getCurrentStock() + "un. " + this.calories + "/100g " + this.percentageOfFat+"%";
    }
}

/**
 * Class that represents a product associated with Cleaning. This class has a specific attribute associated with Cleaning.
 *
 * @author Eduardo Nunes
 */
class CleaningProduct extends Product implements Serializable {
    /**
     * Toxicity of the product
     */
    private int toxicity;

    /**
     * Constructor of the class {@link CleaningProduct Cleaning Product}, it along with the initializer of the superclass {@link Product}, initializes the toxicity of the product (one specific attribute of this class).
     *
     * @throws IllegalArgumentException If the toxicity is not between 0 and 100
     *
     * @param name Name of the product
     * @param identifier Identifier of the product (barcode)
     * @param pricePerUnit Price per unit of the product
     * @param currentStock Current stock of the product
     * @param toxicity Toxicity of the product (class specific attribute)
     */
    public CleaningProduct(String name, String identifier, double pricePerUnit, int currentStock, int toxicity) {
        super(name, identifier, pricePerUnit, currentStock); // initializes the superclass Product
        if (toxicity < 0 || toxicity > 10) { // checks if the toxicity is valid
            throw new IllegalArgumentException("Toxicity must be between 0 and 10");
        }
        this.toxicity = toxicity; // assigns the toxicity of the product
        this.shippingprice = 0; // assigns the shipping price of the product to 0 (cleaning products do not have shipping costs)
        this.totalPrice = this.pricePerUnit + this.shippingprice; // assigns the total price of the product (price per unit + shipping price)
    }

    /**
     * Method that returns the shipping price of the product.
     *
     * @return Shipping price of the product
     */
    @Override
    public double getShippingPrice(){
        return this.shippingprice;
    }

    /**
     * Method toString of a Cleaning Product.
     *
     * @return String representation of a Cleaning Product
     */
    @Override
    public String toString() {
        return "Cleaning Product: " + this.getName() + " id:" + this.getBarcode() + " " + ", Total Price:" +
                this.getPrice() +" euro ("+this.getPricePerUnit()+"+"+this.getShippingPrice() +") - "+ this.getCurrentStock() + "un. " + this.toxicity;
    }
}

/**
 * Class that represents a product associated with Furniture. This class contains the specific attributes of a product associated with Furniture.
 *
 * @author Eduardo Nunes
 */
class FurnitureProduct extends Product implements Serializable {
    /**
     * Weight of the product
     */
    private double weight;

    /**
     * Height of the product
     */
    private double height;

    /**
     * Width of the product
     */
    private double width;

    /**
     * Depth of the product
     */
    private double depth;

    /**
     * Dimension of the product
     */
    private double dimension;

    /**
     * Constructor of the class {@link FurnitureProduct Furniture Product}, it along with the initializer of the superclass {@link Product}, initializes the weight, height, width, depth and dimension of the product (specific attributes of this class).
     *
     * @param name Name of the product
     * @param identifier Identifier of the product (barcode)
     * @param pricePerUnit Price per unit of the product
     * @param currentStock Current stock of the product
     * @param weight Weight of the product (class specific attribute)
     * @param height Height of the product (class specific attribute)
     * @param width Width of the product (class specific attribute)
     * @param depth Depth of the product (class specific attribute)
     */
    public FurnitureProduct(String name, String identifier, double pricePerUnit, int currentStock, double weight, double height,double width, double depth) {
        super(name, identifier, pricePerUnit, currentStock); // initializes the superclass Product
        this.weight = weight; // assigns the weight of the product
        this.height = height; // assigns the height of the product
        this.width = width; // assigns the width of the product
        this.depth = depth; // assigns the depth of the product
        this.dimension = height * width * depth; // assigns the dimension of the product (height * width * depth)
        this.shippingprice = this.weight > 15 ? 10 : 0; // assigns the shipping price of the product (if the weight is greater than 15, the shipping price is 10, otherwise, it is 0)
        this.totalPrice = this.pricePerUnit + this.shippingprice; // assigns the total price of the product (price per unit + shipping price)
    }

    /**
     * Method toString of a Furniture Product.
     *
     * @return String representation of a Furniture Product
     */
    @Override
    public String toString() {
        return "Furniture Product: " + this.getName() + " id:" + this.getBarcode() + ", Total Price:" +
                this.getPrice() +" euro ("+this.getPricePerUnit()+"+"+this.getShippingPrice() +") - " + this.getCurrentStock() + "un. " + this.weight + " kg " + this.height + "cm " + this.width + "cm " + this.depth + "cm " + this.dimension+"cm^3";
    }

}