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
            pprice = p.getPrice();
            timesbought = o.getTimesbought(p);

            for (int i = 1; i <= timesbought; i++) { // loop for every time the product is bought
                if (!(i % 4 == 0)) { //if it's the fourth time bought a product, don't account for the price
                    total += pprice;
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

