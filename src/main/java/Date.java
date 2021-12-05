package main.java;

import java.io.Serializable;
import java.util.Scanner;

/**
 * Class that represents a date, along with its operations:
 * {@link Date#isValidDate(int, int, int)}, {@link Date#isBefore(Date)}, {@link Date#equals(Date)}, {@link Date#isAfter(Date)}.
 *
 */
class Date implements Serializable {
    /**
     * Day of the month
     */
    private int day;

    /**
     * Month of the year
     */
    private int month;

    /**
     * Year
     */
    private int year;

    /**
     * Constructor of the {@link Date} class, it checks if the given parameters form a valid date
     * and if they do, it sets the date.
     *
     * @throws IllegalArgumentException if the arguments of the date don't form a valid date
     *
     * @param day Day of the month
     * @param month Month of the year
     * @param year Year
     */
    public Date(int day, int month, int year) {
        if (isValidDate(day, month, year)) { //check if the date is valid
            this.day = day; //set the day
            this.month = month; //set the month
            this.year = year; //set the year
        }
        else {
            throw new IllegalArgumentException("Invalid date.");
        }
    }

    @Deprecated
    public Date(Scanner sc){
        int d=0,m=0,y=0;

        do {
           //ask the user for a date.
           System.out.print("Enter a date (dd/mm/yyyy): ");


            String date = sc.nextLine();

            //split the date into an array
            String[] dateArray = date.split("/");

            //check if the array has 3 elements
            if (dateArray.length != 3) {
                //check if the elements are integers
                System.out.println("Please insert the data correctly (dd/mm/yy).");
                continue;
            }

            try {
                d = Integer.parseInt(dateArray[0]);
                m = Integer.parseInt(dateArray[1]);
                y = Integer.parseInt(dateArray[2]);
            } catch (NumberFormatException e) {
                System.out.println("Please, write numbers in the respective date.");
                continue;
            }

        } while (!isValidDate(d,m,y));


        this.day = d;
        this.month = m;
        this.year = y;
    }

    /**
     * Checks if the given date is valid.
     *
     * @param day Day of the month
     * @param month Month of the year
     * @param year Year
     * @return true if the date is valid, false otherwise
     */
    private boolean isValidDate(int day, int month, int year) {
        if (day > 0 && day <= 31) { // check if the day is valid
            if (month > 0 && month <= 12) { // check if the month is valid
                if (year > 0) { // check if the year is valid
                    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) { // check if the month has 31 days
                        if (day <= 31) { //if it has
                            return true; // the date is valid
                        }
                    } else if (month == 4 || month == 6 || month == 9 || month == 11) { // check if the month has 30 days
                        if (day <= 30) { //if it has
                            return true; // the date is valid
                        }
                    } else { //it's February
                        if (year % 4 == 0) {  //check for leap year
                            if (day <= 29) { // if the year is leap, and It's February the day must be less than 29
                                return true; // the date is valid
                            }
                        }
                        else {
                            if (day <= 28) { // if the year is not leap, and It's February the day must be less than 28
                                return true; // the date is valid
                            }
                        }
                    }
                }
            }
        }
        // Otherwise, the date is not valid
        System.out.println("Data is not valid.");
        return false;
    }

    /**
     * Checks if a Date is equal to another Date.
     *
     * @param date Date to compare
     * @return true if the dates are equal, false otherwise
     */
    private boolean equals(Date date) {
        return this.day == date.day && this.month == date.month && this.year == date.year;
    }

    /**
     * Check if the date is before another date
     *
     * @param date Date to compare
     * @return true if the date is before the other date, false otherwise
     */
    public boolean isBefore(Date date) {
        if (this.year < date.year) { // check if the year is before
            return true; // the date is before
        }
        else if (this.year == date.year) { // check if the year is the same
            if (this.month < date.month) { // check if the month is before
                return true; // the date is before
            }
            else if (this.month == date.month) { // check if the month is the same
                if (this.day < date.day) { // check if the day is before
                    return true; // the date is before
                }
            }
        }
        return false; // otherwise, the date is after
    }

    /**
     * Check if the date is after another date, by simply inverting {@link #isBefore(Date)} and {@link #equals(Date)}.
     *
     * @param date Date to compare
     * @return true if the date is after the other date, false otherwise
     */
    public boolean isAfter(Date date) {
        return !this.isBefore(date) && !this.equals(date);
    }

    /**
     * Method to print the date in the format dd/mm/yy
     *
     * @return String representation of the date
     */
    public String toString() {
         return day + "/" + month + "/" + year;
    }
}
