import java.io.*;
import java.util.ArrayList;

class Products {
    private ArrayList<Product> products;
    File f = new File("roducts.txt");

    public Products() {
        products = new ArrayList<>();
    }

    private void addProduct(Product product) {
        for (Product p : products) {
            if (p.getName().equals(product.getName()) || p.getBarcode().equals(product.getBarcode())) {
                throw new IllegalArgumentException("Product already exists.");
            }
        }
        this.products.add(product);
    }


    private void loadProducts(){
            if(f.exists() && f.isFile()) {
                try {
                    FileReader fr = new FileReader(f);
                    BufferedReader br = new BufferedReader(fr);

                    String line;
                    while((line= br.readLine()) != null) {
                        try {
                            String[] arrOfStr = line.split(";");
                            String type = arrOfStr[0];
                            Product p;
                            try {
                                if (type.equals("C")) {
                                    p = new CleaningProduct(arrOfStr[1], arrOfStr[2], arrOfStr[3], arrOfStr[4]);
                                } else if (type.equals("A")) {
                                    p = new FoodProduct(arrOfStr[1], arrOfStr[2], arrOfStr[3], arrOfStr[4]);
                                }
                                else if (type.equals("M")) {
                                    p = new FurnitureProduct(arrOfStr[1], arrOfStr[2], arrOfStr[3], arrOfStr[4]);
                                }
                                else {
                                    System.out.println("Invalid date format.\n Text file is corrupted (not in the correct format).");
                                    return;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("Invalid product format.\n Text file is corrupted (not in the correct format).");
                            }



                            try {
                                this.addProduct(p);
                            } catch (Exception e) {
                                System.out.println("Error while adding product "+p+ "from the prebuilt text file.");
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e){
                            System.out.println("Invalid date format.\n Text file is corrupted (not in the correct format).");
                            System.exit(0);
                        }

                    }

                    savecustomersOBJ();
                    //System.out.println("Customers loaded successfully:");
                    //this.printClients();

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

    @Override
    public String toString() {
        String toprint = "";
        for (Product product : products) {
            toprint += product.toString() + "\n";
        }
        return toprint;
    }
}
