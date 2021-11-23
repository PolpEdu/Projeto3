import java.io.*;


class DataToSave {
    //class que tem tudo o que pretendo guardar na base de dados



    private Customers customers;
    private Products products;
    private Orders orders;

    public DataToSave(Customers customers, Products products, Orders orders) {
        this.customers = customers;
        this.products = products;
        this.orders = orders;
    }



}