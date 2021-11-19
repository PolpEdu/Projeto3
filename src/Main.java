import java.util.Scanner;

public class Main {
    // Main method
    public static void main(String[] args) {

    }


}

/*
The	application should manage customers, products, discounts, and orders. Customers	are	characterized by name, address,	email, telephonenumber, and date of birth.
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
Furniture products weighing more than 15kg have a delivery cost of 10 Euros.
This cost is applicable	to all Customers.

 */
class Customer {
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;

    public Customer(String name, String address, String email, String phoneNumber, String dateOfBirth) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public Customer() {
        // ask for data to create a customer
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter customer name: ");

        this.name = sc.nextLine();
        System.out.println("Enter customer address: ");
        this.address = sc.nextLine();

        System.out.println("Enter customer email: ");
        this.email = sc.nextLine();

        System.out.println("Enter customer phone number: ");
        this.phoneNumber = sc.nextLine();

        System.out.println("Enter customer date of birth: ");
        this.dateOfBirth = sc.nextLine();

        sc.close();
    }
}


class Product {
    private String name;
    private String identifier;
    private double pricePerUnit;
    private int currentStock;

    public Product(String name, String identifier, double pricePerUnit, int currentStock) {
        this.name = name;
        this.identifier = identifier;
        this.pricePerUnit = pricePerUnit;
        this.currentStock = currentStock;
    }
}

class FoodProduct extends Product {
    private int calories;
    private int percentageOfFat;

    public FoodProduct(String name, String identifier, double pricePerUnit, int currentStock, int calories, int percentageOfFat) {
        super(name, identifier, pricePerUnit, currentStock);
        this.calories = calories;
        this.percentageOfFat = percentageOfFat;
    }


}

class CleaningProduct extends Product {
    private int toxicity;

    public CleaningProduct(String name, String identifier, double pricePerUnit, int currentStock, int toxicity) {
        super(name, identifier, pricePerUnit, currentStock);

        if (toxicity < 0 || toxicity > 10) {
            throw new IllegalArgumentException("Toxicity must be between 0 and 10");
        }
        this.toxicity = toxicity;
    }
}

class FurnitureProduct extends Product {
    private double weight;
    private double height;
    private double width;
    private double depth;

    public FurnitureProduct(String name, String identifier, double pricePerUnit, int currentStock, double weight, double height, double width) {
        super(name, identifier, pricePerUnit, currentStock);
        this.weight = weight;
        this.height = height;
        this.width = width;
        this.depth = height * width * depth;
    }



}
