import java.io.*;
import java.util.ArrayList;

class Orders implements Serializable {
    private final String FILE_NAME = "orders.obj";
    private File ordersFile = new File(FILE_NAME);

    private Customer customer;
    private ArrayList<Order> orders;

    public Orders(Customer customer) {
        this.customer = customer;
        this.orders = new ArrayList<>();
        loadOrdersOBJ();
    }

    //add orders
    public void addOrder(Order order) {
        this.orders.add(order);
        saveOrdersOBJ();
    }

    private void loadOrdersOBJ() {

    }

    private void saveOrdersOBJ() {
        //save orders to object file
        try {
            FileOutputStream fos = new FileOutputStream(ordersFile);
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
        return "Orders made by "+customer.getName()+":\n"+ result;
    }

}

class Order {
    private Customer orderedby;
    private Date orderDate;
    private ArrayList<Product> chosenProducts;
    private double totalprice;

    public Order(Customer orderedby, ArrayList<Product> chosenProducts, Date orderDate) {
        this.orderedby = orderedby;


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
        return "Order made by " + orderedby.getName() + " on " + orderDate.toString() + ":\n" + result + "Total price: " + totalprice;
    }
}
