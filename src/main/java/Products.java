package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that represents the list of the products in the store.
 *
 * @author Eduardo Nunes
 */
class Products implements Serializable {
    /**
     * List of all the Available {@link Product products} in the store.
     */
    private ArrayList<Product> products;

    /**
     * Constant that represents the default text file's name that contains all the available products in the store.
     */
    private final String INIT_TXT = "products.txt";

    /**
     * Constant that represents the default text file that contains all the available products in the store.
     */
    private final File t = new File(INIT_TXT);

    /**
     * Constant that represents the object file's name of all the available products in the store.
     */
    private final String FILE_NAME = "products.obj";

    /**
     * Constant that represents the object file of all the available products in the store.
     */
    private final File f = new File(FILE_NAME);

    /**
     * Constructor of the {@link Products} class that loads the products from the files.
     *
     * @throws IOException if we can't write to the file.
     * @throws ClassNotFoundException if the object file doesn't exist.
     *
     */
    public Products() throws IOException, ClassNotFoundException {
        products = new ArrayList<>(); // Initializes the list of products, an empty Array List.
        loadProducts(); // Loads the products from the files.
    }

    /**
     * Method that loads the {@link Products} from the files. t checks if the .obj file exists and if it does,
     * it loads the products from it. If it doesn't, it loads the products from the default .txt file.
     *
     * @throws IOException if we can't write to the file.
     * @throws ClassNotFoundException if the object file doesn't exist.
     *
     */
    private void loadProducts() throws IOException, ClassNotFoundException {
        if (f.exists() && f.isFile()) { // Checks if the object file exists and if it is a file.
            loadProductsOBJ(); // if it does , it loads the products from it.
        }
        else {
            loadProductstxt(); // if it doesn't, it loads the products from the default .txt file.
        }
    }

    /**
     * Method that adds a new {@link Product product} to the list of products.
     * It checks if a product is already in the available products, if it's not adds it.
     *
     * @throws IllegalArgumentException if the product is already in the list of products.
     *
     * @param product Name The name of the {@link Product product} to be found.
     */
    private void addProduct(Product product) throws IllegalArgumentException {
        for (Product p : products) { // loop for every product in the list of products.
            if (p.getName().equals(product.getName()) || p.getBarcode().equals(product.getBarcode())) { // Checks if the product is already in the list of products.
                throw new IllegalArgumentException("Product already exists."); // if it is, throws an exception.
            }
        }
        this.products.add(product); // if it isn't, adds the product to the list of products.
    }

    /**
     * Method that removes a {@link Product product} X times given as parameter, from the list of products.
     *
     * If there is none in stock, it removes it from the available products overall.
     *
     * @throws IOException if we can't write to the file.
     *
     * @param p product The {@link Product product} to be removed.
     * @param nr Number of times the {@link Product product} is to be removed.
     */
    public void removeProducts(Product p, int nr) throws IOException {
        p.removeun(nr); //removes that many times from the stock

        if(p.getCurrentStock()==0) { //if it reaches 0, removes it from the list of products overall
            this.products.remove(p); //removes it from the list of products
        }
        saveproductsOBJ(); // saves the current state from the list of products to the object file.
    }

    /**
     * Method that loads the {@link Products} from the .obj file and assigns them to the current list of products.
     *
     * @throws FileNotFoundException If we can't find the file.
     * @throws IOException If we can't read the file.
     * @throws ClassNotFoundException If we can't find the class that we are trying to load.
     */
    private void loadProductsOBJ() throws FileNotFoundException, IOException, ClassNotFoundException {
        if (f.isFile()) {
            try {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Products lido = (Products) ois.readObject();

                this.products = lido.products;
                //System.out.println("Products loaded successfully from OBJ file.");
                //printProducts();
                ois.close();
                fis.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Error while opening object file.");
            } catch (IOException ex) {
                System.out.println("Error while reading object file. Probably due to it being corrupted.\n"+ex);
            } catch (ClassNotFoundException ex) {
                System.out.println("Error while converting object.\n"+ex);
            }
        }
    }
    /**
     * Method that saves the current {@link Products} into to the .obj file.
     *
     * @throws FileNotFoundException If we can't find the file.
     * @throws IOException If we can't write to the file.
     *
     */
    private void saveproductsOBJ() throws FileNotFoundException, IOException {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            Products c = this;
            oos.writeObject(c);
            //System.out.println("Saved!");

            oos.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error while creating file.");
        } catch (IOException ex) {
            System.out.println("Error while writing to the OBJ file.\n"+ex);
        }
    }

    /**
     * Method that loads the {@link Products} from the default .txt file.
     *
     * @throws ArrayIndexOutOfBoundsException In case we try to access an index that contains data that doesn't exist.
     * @throws FileNotFoundException If we can't find the file.
     * @throws IOException If we can't read the file.
     *
     */
    private void loadProductstxt() throws ArrayIndexOutOfBoundsException, FileNotFoundException, IOException {
        if(t.exists() && t.isFile()) { //checks if the file exists and is a file
            try {
                FileReader fr = new FileReader(t);
                BufferedReader br = new BufferedReader(fr);

                String line; //line that will be read from the file
                while((line= br.readLine()) != null) { // loop line by line
                        String[] arrOfStr = line.split(";"); //splits the line into an array of strings by ;
                        String type = arrOfStr[0]; //type of the product is the first element of the array

                        Product p; //product that will be created
                        try {
                            String name = arrOfStr[1]; //name of the product is the second element of the array
                            String id = arrOfStr[2]; //id of the product is the third element of the array
                            double price = Double.parseDouble(arrOfStr[3]); //price of the product is the fourth element of the array
                            int quantity = Integer.parseInt(arrOfStr[4]); //quantity of the product is the fifth element of the array


                            switch (type) { //switch statement that creates the product based on the type
                                case "C":
                                    int toxicity = Integer.parseInt(arrOfStr[5]); //toxicity of the product is the sixth element of the array
                                    p = new CleaningProduct(name, id, price, quantity, toxicity); //assigns the product to the new CleaningProduct based on the data read from the file
                                    break;
                                case "A":
                                    int calories = Integer.parseInt(arrOfStr[5]); //calories of the product is the sixth element of the array
                                    double percentageofFat = Double.parseDouble(arrOfStr[6]); //percentage of fat of the product is the seventh element of the array

                                    p = new FoodProduct(name, id, price, quantity, calories, percentageofFat); //assigns the product to the new FoodProduct based on the data read from the file
                                    break;
                                case "F":
                                    double weight = Double.parseDouble(arrOfStr[5]); //weight of the product is the sixth element of the array
                                    double height = Double.parseDouble(arrOfStr[6]); //height of the product is the seventh element of the array
                                    double width = Double.parseDouble(arrOfStr[7]); //width of the product is the eighth element of the array
                                    double depth = Double.parseDouble(arrOfStr[8]); //depth of the product is the ninth element of the array

                                    p = new FurnitureProduct(name, id, price, quantity, weight, height, width, depth); //assigns the product to the new FurnitureProduct based on the data read from the file
                                    break;
                                default:
                                    System.out.println("Text file - "+INIT_TXT+" - corrupted (Product type is invalid)."); //if the type is invalid, the text file is corrupted
                                    return;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) { //if the array is not long enough, the text file is corrupted
                            System.out.println("Invalid product format on product.\n Text file is corrupted (not in the correct format).\n"+e);
                            return;
                        }

                        try {
                            this.addProduct(p); //try to add the product to the available products
                        } catch (Exception e) {
                            System.out.println("Error while adding product "+p+ "from the prebuilt text file.");
                        }
                }


                saveproductsOBJ(); //save the products to the .obj file

                //? System.out.println("Products loaded successfully:");
                //? this.printProducts();

                //close the streams
                br.close();
                fr.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Error while opening object file.");
            } catch (IOException ex) {
                System.out.println("Error while reading object file. Probably due to it being corrupted.\n"+ex);
            }
        } else { //if file does not exist
            System.out.println("Error. No DataBase not found. Exiting...");
            System.exit(1); //close the program
        }
    }

    /**
     * Test Method that prints all the current available products.
     */
    @SuppressWarnings("unused")
    private void printProducts() {
        for (Product p : products) {
            System.out.println(p);
        }
    }

    /**
     * Method that prints all the available products for the customer to choose from in {@link LoggedIn#makeOrder(Scanner)}.
     */
    public void printProductsSelection() {
        System.out.println("\nProducts:");
        int i =1; //counter for the products to make the user experience more pleasant when choosing a product
        for(Product p : this.products) { //for each product in the products array
            //print the product name, price and the current stock along with an identifier for the customer to choose from
            System.out.println(i+" - "+p.getName()+ ", "+
                    p.getPrice() +" euro ("+p.getPricePerUnit()+"+"+p.getShippingPrice() +") - un: "+p.getCurrentStock());
            i++;
        }
    }

    /**
     * Method that gets a Product from a given index. This method complements the method above ({@link #printProductsSelection()}) that shows every available product.
     *
     * @param nr the index of the product to get
     * @return the product at the given index
     */
    public Product getProduct(int nr) {
        System.out.print("\nChosen Product: ");
        int i =1;
        for(Product p : this.products) { //for each product in the products array
            if(i==nr) { //if the index is the same as the given index

                System.out.println("Chosen Product - "+ p.getName()+ ", "+
                        p.getPrice() +" euro ("+p.getPricePerUnit()+"+"+p.getShippingPrice() +") - un: "+p.getCurrentStock());
                return p; //return the product chosen
            }
            i++;
        }
        return null; //if the index is not found, return null
    }

    /**
     * Method toString of the Products.
     *
     * @return A string representation of the Products.
     */
    @Override
    public String toString() {
        String toprint = "";
        for (Product product : products) { //loop through the products array
            toprint += product.toString() + "\n"; //add the toString of each product to the string to print
        }
        return toprint; //return the string to print
    }
}
