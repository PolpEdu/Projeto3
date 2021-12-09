package main.java;

import java.io.Serializable;

/**
 * Class that represents a default Promotion, this class extends into different types of Promotions.
 * We need more than one type of Promotion since the price calculation of an {@link Order} is different for each type of Promotion.
 *
 * @author Eduardo Nunes
 */
abstract class Promotion implements Serializable {
    /**
     * The promotion' Start {@link Date}.
     */
    protected Date promotionStart;

    /**
     * The promotion's End {@link Date}.
     */
    protected Date promotionEnd;

    /**
     * Constructor for the {@link Promotion} class, it initializes the promotion' start and end {@link Date dates}.
     * It is protected since it's only used by the subclasses,
     *
     * @param promotionStart the promotion' start {@link Date date}
     * @param promotionEnd the promotion's end {@link Date date}
     */
    protected Promotion(Date promotionStart, Date promotionEnd) throws IllegalArgumentException {
        this.promotionStart = promotionStart; //assign the promotion start date
        this.promotionEnd = promotionEnd; //assign the promotion end date
    }

    /**
     * Method that calculates the price of a given {@link Order} applicable to this {@link Promotion}.
     * In this case, there is no special calculation for the price, so it just returns the {@link Order}'s price.
     *
     * @param order the {@link Order} that we want to calculate the price of
     * @return the price of the {@link Order}
     */
    public double calcPrice(Order order) {
        return order.getDefaulttotalprice(); //return the total price
    }

    /**
     * Method that checks if a Promotion is applicable given a certain Date
     *
     * @param d date
     * @return true if the Promotion is applicable, false otherwise
     */
    public boolean isApplicable(Date d) {
        return (promotionStart.isBefore(d) && promotionEnd.isAfter(d)); //check if the promotion start is before the date and the promotion end is after the date
    }

    /**
     * Method that returns the promotion's start {@link Date date}.
     *
     * @return the promotion's start {@link Date date}
     */
    public Date getStartDate() {
        return this.promotionStart;
    }

    /**
     * Method that returns the promotion's end {@link Date date}.
     *
     * @return the promotion's end {@link Date date}
     */
    public Date getEndDate() {
        return this.promotionEnd;
    }

    /**
     * Method toString of the {@link Promotion} class.
     *
     * @return the String representation of the {@link Promotion}
     */
    public String toString() {
        return "Promotion start: " + promotionStart + " Promotion end: " + promotionEnd;
    }
}

/**
 * Subclass that represents a type {@link Promotion} (Pay-Three-Take-Four) that is applicable to a {@link Order}.
 */
class PTTF extends Promotion implements Serializable {

    /**
     * Constructor for the {@link PTTF Pay-Three-Take-Four} class, it initializes the promotion' start and end {@link Date dates}.
     *
     * @param promotionStart the promotion' start {@link Date date}
     * @param promotionEnd   the promotion's end {@link Date date}
     */
    public PTTF(Date promotionStart, Date promotionEnd) {
        super(promotionStart, promotionEnd);
    }

    /**
     * Method that calculates the price of a given {@link Order} applicable to {@link PTTF Pay-Three-Take-Four} {@link Promotion}.
     *
     * @param o order the {@link Order} that we want to calculate the price of
     * @return the price of the {@link Order} with a {@link PTTF Pay-Three-Take-Four} {@link Promotion promotion} applied
     */
    @Override
    public double calcPrice(Order o) {
        double total = 0;
        double pprice;
        int timesbought;

        //check for 4 ocorrences of a product in an order
        for (Product p : o.getProductNames()) {
            pprice = p.getPrice(); //get the product's price
            timesbought = o.getTimesbought(p); //get the number of times the product was bought in the order

            for (int i = 1; i <= timesbought; i++) { // loop for every time the product is bought
                if (!(i % 4 == 0)) { //if it's the fourth time bought a product, don't account for the price
                    total += pprice; //add the price to the total
                }
            }
        }
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Method toString of the {@link PTTF} class.
     *
     * @return the String representation of the Pay-Three-Take-Four {@link Promotion}.
     */
    @Override
    public String toString() {
        return "Promotion(PTTF) - Promotion start: " + this.promotionStart + " Promotion end: " + this.promotionEnd;
    }
}
/**
 * Subclass that represents a type {@link Promotion} (Pay-Less) that is applicable to a {@link Order}.
 */
class PL extends Promotion implements Serializable {
    /**
     * Constructor for the {@link PL} class, it initializes the promotion' start and end {@link Date dates}.
     *
     * @param promotionStart the promotion' start {@link Date date}
     * @param promotionEnd   the promotion's end {@link Date date}
     */
    public PL(Date promotionStart, Date promotionEnd) {
        super(promotionStart, promotionEnd);
    }

    /**
     * Method that calculates the price of a given {@link Order} applicable to {@link PL Pay-Less} {@link Promotion}.
     *
     * @param o order the {@link Order} that we want to calculate the price of
     * @return the price of the {@link Order} with a {@link PL Pay-Less} {@link Promotion promotion} applied
     */
    @Override
    public double calcPrice(Order o) {
        double pricetopay = 1.05;
        double total = 0;
        int timesbought;

        for (Product p : o.getProductNames()) { //loop for every product name
            timesbought = o.getTimesbought(p); //get the number of times the product was bought in the order
            if(timesbought > 1) { //if the product was bought more than once we can apply this type of discount
                while (timesbought > 0) {  //loop for every time the product was bought
                    if (pricetopay > 0.5) { //if the discount is greater than 0.5 we can subtract to the price to pay
                        pricetopay -= 0.05; //apply the discount
                        pricetopay = Math.round(pricetopay * 100.0) / 100.0; //round the price to pay
                    }
                    timesbought--; //decrease the number of times the product was bought
                    total += p.getPrice() * pricetopay*100.0/100.0; //add the price to the total
                    // System.out.println("Incremented in total: " + total);
                }
            }
            else {
                //if the product is bought only once, the discount is not applied
                total += p.getPrice()* timesbought; //simply add the price of the product to the total
            }
        }
        //round total * discount with 2 decimals and return it
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Method toString of the {@link PL Pay-Less} class.
     *
     * @return the String representation of the {@link PL Pay-Less} {@link Promotion promotion}.
     */
    @Override
    public String toString() {
        return "Promotion(PL) - Promotion start: " + this.promotionStart + " Promotion end: " + this.promotionEnd;
    }
}

