import java.util.ArrayList;

class Orders {
    private ArrayList<Order> orders;



}

class Order {
    private Customer orderedby;
    private Date orderDate;
    private Product product;


    public Order(Customer orderedby, Product product) {
        this.orderedby = orderedby;
        this.product = product;
    }


}
