import java.io.*;
import java.util.ArrayList;

class Products implements Serializable {
    private ArrayList<Product> products;

    private final String INIT_TXT = "products.txt";
    private final File t = new File(INIT_TXT);


    private final String FILE_NAME = "products.obj";
    private final File f = new File(FILE_NAME);


    public Products() {
        products = new ArrayList<>();
        loadProducts();
    }

    private void loadProducts() {
        if (f.exists() && f.isFile()) {
            loadcustomersOBJ();
        }
        else {
            loadProductstxt();
        }
    }

    private void addProduct(Product product) {
        for (Product p : products) {
            if (p.getName().equals(product.getName()) || p.getBarcode().equals(product.getBarcode())) {
                throw new IllegalArgumentException("Product already exists.");
            }
        }
        this.products.add(product);
    }


    private void loadcustomersOBJ(){
        if (f.isFile()) {
            try {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Products lido = (Products) ois.readObject();

                this.products = lido.getProducts();
                //System.out.println("Products loaded successfully from OBJ file.");
                //printProducts();
                ois.close();
                fis.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Error while opening object file.");
            } catch (IOException ex) {
                System.out.println("Error while reading object file. Probably due to it being corrupted.\n"+ex);
            } catch (ClassNotFoundException ex) {
                System.out.println("Error while converting object.");
            }
        }
    }

    private void saveproductsOBJ() {
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

    private ArrayList<Product> getProducts() {
        return products;
    }


    private void loadProductstxt(){
        if(t.exists() && t.isFile()) {
            try {
                FileReader fr = new FileReader(t);
                BufferedReader br = new BufferedReader(fr);

                String line;
                while((line= br.readLine()) != null) {
                    try {
                        String[] arrOfStr = line.split(";");
                        String type = arrOfStr[0];

                        Product p;
                        try {
                            String name = arrOfStr[1];
                            String id = arrOfStr[2];
                            double price = Double.parseDouble(arrOfStr[3]);
                            int quantity = Integer.parseInt(arrOfStr[4]);


                            switch (type) {
                                case "C":
                                    int toxicity = Integer.parseInt(arrOfStr[5]);
                                    p = new CleaningProduct(name, id, price, quantity, toxicity);
                                    break;
                                case "A":
                                    int calories = Integer.parseInt(arrOfStr[5]);
                                    double percentageofFat = Double.parseDouble(arrOfStr[6]);

                                    p = new FoodProduct(name, id, price, quantity, calories, percentageofFat);
                                    break;
                                case "F":
                                    double weight = Double.parseDouble(arrOfStr[5]);
                                    double height = Double.parseDouble(arrOfStr[6]);
                                    double width = Double.parseDouble(arrOfStr[7]);
                                    double depth = Double.parseDouble(arrOfStr[8]);


                                    p = new FurnitureProduct(name, id, price, quantity, weight, height, width, depth);
                                    break;
                                default:
                                    System.out.println("Text file - "+INIT_TXT+" - corrupted (Product type is invalid).");
                                    return;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Invalid product format on product.\n Text file is corrupted (not in the correct format).\n"+e);
                            return;
                        }

                        try {
                            this.addProduct(p);
                        } catch (Exception e) {
                            System.out.println("Error while adding product "+p+ "from the prebuilt text file.");
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException e){

                        System.out.println("Invalid date format. Couldn't even get the names and id's from the Products\n Text file is corrupted (not in the correct format) or nonexistent.");
                        System.exit(1);
                    }

                }


                //System.out.println("Products loaded successfully:");
                saveproductsOBJ();
                //this.printProducts();

                br.close();
                fr.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Error while opening object file.");
            } catch (IOException ex) {
                System.out.println("Error while reading object file. Probably due to it being corrupted.\n"+ex);
            }

        } else {
            System.out.println("Error. No DataBase not found. Exiting...");
            System.exit(1);
        }
    }

    public int getProductslen(){
        return products.size();
    }


    public void printProductsSelection() {
        System.out.println("\nProducts:");
        int i =0;
        for(Product p : this.products) {
            System.out.println(i+". -"+p);
        }
    }



    @Override
    public String toString() {
        String toprint = "";
        for (Product product : products) {
            toprint += product.toString() + "\n";
        }
        return toprint;
    }
}
