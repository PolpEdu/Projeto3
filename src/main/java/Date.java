import java.io.Serializable;
import java.util.Scanner;

//class that controls dates
class Date implements Serializable {
    private int day;
    private int month;
    private int year;

    //constructor
    public Date(int day, int month, int year) {
        if (isValidDate(day, month, year)) {
            this.day = day;
            this.month = month;
            this.year = year;
        }
        else {
            throw new IllegalArgumentException("Invalid date.");
        }
    }

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

    //check if the date is valid
    private boolean isValidDate(int day, int month, int year) {
        if (day > 0 && day <= 31) {
            if (month > 0 && month <= 12) {
                if (year > 0) {
                    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                        if (day <= 31) {
                            return true;
                        }
                    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                        if (day <= 30) {
                            return true;
                        }
                    } else { //it's February

                        //check for leap year
                        if (year % 4 == 0) {
                            if (day <= 29) {
                                return true;
                            }
                        }
                        else {
                            if (day <= 28) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Data doesn't exist.");
        return false;
    }

    public String toString() {
         return day + "/" + month + "/" + year;
    }
}
