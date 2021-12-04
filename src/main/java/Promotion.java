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

import java.io.Serializable;


abstract class Promotion implements Serializable {
    protected Date promotionStart;
    protected Date promotionEnd;

    protected Promotion(Date promotionStart, Date promotionEnd) {
        this.promotionStart = promotionStart;
        this.promotionEnd = promotionEnd;
    }

    //used to calculate the full price to pay without any special promotions applied
    public double calcPrice(Order order) {
        double price = 0;
        System.out.println("Calculating price without any special promotions applied");
        for (Product p : order.getProducts()) {
            price += p.getPrice();

        }
        return price;
    }

    //Check if a promotion is applicable
    public boolean isApplicable(Date d) {
        return (promotionStart.isBefore(d) && promotionEnd.isAfter(d));
    }

    public Date getStartDate() {
        return this.promotionStart;
    }

    public Date getEndDate() {
        return this.promotionEnd;
    }

    //to string method
    public String toString() {
        return "Promotion start: " + promotionStart + " Promotion end: " + promotionEnd;
    }

}


class PTTF extends Promotion implements Serializable {

    public PTTF(Date promotionStart, Date promotionEnd) {
        super(promotionStart, promotionEnd);
    }

    //calculate the price after applying the promotion
    @Override
    public double calcPrice(Order o) {
        double total = 0;
        double pprice ;
        int timesbought;

        //check for 4 ocorrences of a product in a order
        for (Product p : o.getProductNames()) {
            pprice = p.getPrice();
            timesbought = o.getTimesbought(p);

            for (int i = 1; i <= timesbought; i++) {
                if (!(i % 4 == 0)) {
                    total += pprice;
                }
            }
        }
        return Math.round(total * 100.0) / 100.0;
    }

    //to string method
    @Override
    public String toString() {
        return "Promotion (PTTF) -start: " + this.promotionStart + " Promotion end: " + this.promotionEnd;
    }
}

class PL extends Promotion implements Serializable {

    public PL(Date promotionStart, Date promotionEnd) {
        super(promotionStart, promotionEnd);
    }

    @Override
    public double calcPrice(Order o) {
        double discount = 1.05;
        double total = 0;
        int timesbought;

        for (Product p : o.getProductNames()) {
            timesbought = o.getTimesbought(p);
            if(timesbought > 1) {
                while (timesbought > 0) {
                    if (discount > 0.5) {
                        discount -= 0.05;
                        discount = Math.round(discount * 100.0) / 100.0;
                    }
                    timesbought--;
                    total += p.getPrice() * discount*100.0/100.0;
                    System.out.println("Incremented in total: " + total);
                }
            }
            else {
                //if the product is bought only once, the discount is not applied
                total += p.getPrice()* timesbought;
            }
        }
        //round total * discount with 2 decimals and return it
        return Math.round(total * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "Promotion (PL) -start: " + this.promotionStart + " Promotion end: " + this.promotionEnd;
    }
}

