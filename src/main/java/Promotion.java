package main.java;

// ao continuar aplicar o preço, é tudo o mesmo
class Promotion {
    private Product p;
    private int qtd;
    protected double price2pay;

    Promotion(Product p, int qtd) {
        this.p = p;
        this.qtd = qtd;
        this.price2pay = qtd* p.getPrice();
    }

    public double getPrice2pay() {
        return this.price2pay;
    }
}
