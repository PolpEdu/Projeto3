import java.io.*;
import java.util.ArrayList;

class Orders implements Serializable {
    private final String FILE_NAME = "orders.obj";
    private File FOF = new File(FILE_NAME);

    private ArrayList<Order> orders;

    public Orders() {
        this.orders = new ArrayList<>();
        loadOrdersOBJ();
    }

    public int size() {
        return this.orders.size();
    }

    //add orders
    public void addOrder(Order order) {
        this.orders.add(order);
        saveOrdersOBJ();
    }



    private void loadOrdersOBJ() {
        //load orders from object file
        if (FOF.isFile()) {
            try {
                FileInputStream fis = new FileInputStream(FOF);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Orders lido = (Orders) ois.readObject();

                this.orders = lido.getOrders();
                //System.out.println("Customers loaded successfully from OBJ file.");
                //printClients();
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

    private void saveOrdersOBJ() {
        //save orders to object file
        try {
            FileOutputStream fos = new FileOutputStream(FOF);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this);
            //System.out.println("Saved!");

            oos.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error while creating file.");
        } catch (IOException ex) {
            System.out.println("Error while writing to the OBJ file.\n"+ex);
        }
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

        String result = "";
        for (Order order : orders) {
            result += order.toString() + "\n";
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




    public double getPrice() {
        return this.totalprice;
    }

    public ArrayList<Product> getProducts() {
        return this.chosenProducts;
    }

    public String toString() {
        String result = "";
        for (Product p : chosenProducts) {
            result += p.toString() + "\n";
        }
        return "Order made on the " + orderDate.toString() + ":\n" + result + "Total price: " + totalprice +" €";
    }
}
