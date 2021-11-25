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

    public String getName() {
        return this.name;
    }

    public String getBarcode() {
        return this.identifier;
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