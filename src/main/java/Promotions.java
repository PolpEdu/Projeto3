package main.java;
/*
To increase sales, the company makes temporary discounts.
Each discount is associated	with a single product.
There are two types	of discounts: the pay-three-take-four and the pay-less.

In the pay-three-take-four type, customers pay three out of	four items.
For example, if a customer buys 9 units	of a product, he/she will only pay 7.

In the pay-less type, the first unit is	paid at 100%, being the	cost decreased by 5% for each additional unit, until a maximum discount	of 50% is reached.

Each order can have several	products.txt and in different quantities.
For frequent customers,	delivery is	free for orders over 40 Euros. Below this value, delivery cost is 15 Euros.

For other customers, the delivery cost is 20 Euros.
Furniture products.txt weighing more than 15 kg have a delivery cost of 10 Euros.
This cost is applicable	to all Customers.
 */

class PTTF extends Promotion {

    public PTTF(Product product, int timesbought) {
        super(product, timesbought);
        this.price2pay = getPrice(timesbought, product.getPrice());
    }

    private double getPrice(int timesbought, double pprice) {

        int vezes = Math.floorDiv(timesbought ,4);
        int resto = timesbought % 4;

        return vezes * 3 * pprice+ resto * pprice;
    }
}

class PL extends Promotion {

    public PL(Product product, int timesbought) {
        super(product, timesbought);
        this.price2pay = getPrice(timesbought, product.getPrice());
    }


    private double getPrice(int timesbought, double pprice) {
        int vezes = timesbought;
        double discount = 1;


        double total = pprice * discount;
        vezes--;
        while (vezes > 0) {
            if (discount > 0.5) {
                discount -= 0.05;
            }
            total += pprice * discount;
            vezes--;
        }
        return total;
    }

}

