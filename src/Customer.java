import java.util.ArrayList;
import java.util.Scanner;

class Customer extends Customers {
    private String name;
    private String address;
    private String email;
    private long phoneNumber;
    private Date dateOfBirth;

    public Customer(String name, String address, String email, long phoneNumber, Date dateOfBirth) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public Customer() {
        // ask for data to create a customer
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter customer name: ");
        this.name = sc.nextLine();


        System.out.println("Enter customer address: ");
        this.address = sc.nextLine();


        System.out.println("Enter customer email: ");
        String email = sc.nextLine();

        while (!email.contains("@")) {
            System.out.println("Invalid email address. Please enter again: ");
            email = sc.nextLine();

        }
        this.email = email;

        System.out.println("Enter customer phone number: ");
        String phoneNumber = sc.nextLine();

        while (!isValidPhoneNumber(phoneNumber)) {
            System.out.println("Invalid phone number. Please enter again: ");
            phoneNumber = sc.nextLine();
        }
        this.phoneNumber = Long.parseLong(phoneNumber);


        System.out.println("Enter customer date of birth: ");
        this.dateOfBirth = new Date(sc); //pass the scanner in the date constructor

        System.out.println("Customer created successfully:\n" + this);
        sc.close();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 9) {
            return false;
        }

        for (int i = 0; i < phoneNumber.length(); i++) {
            if (!Character.isDigit(phoneNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cliente = {\nNome: " + name + "\n" + "Address: " + address + "\n" + "Email: " + email + "\n" + "Phone number: " + phoneNumber + "\n" + "Date of birth: " + dateOfBirth + "\n}";
    }
}

class Customers {
    private ArrayList<Customer> Clients;

    public Customers() {
        this.Clients = new ArrayList<>();


    }



}
