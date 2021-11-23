import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Customer{
    private String name;
    private String address;
    private String email;
    private long phoneNumber;
    private Date dateOfBirth;

    public Customer(String name, String address, String email, String phoneNumber, Date dateOfBirth) {
        this.name = name;
        this.address = address;

        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address.");
        }
        this.email = email;


        if(!isValidPhoneNumber(phoneNumber)){
            throw new IllegalArgumentException("Invalid phone number.");
        }
        try {
            this.phoneNumber = Long.parseLong(phoneNumber);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Couldn't parse phone number.");
        }

        this.dateOfBirth = dateOfBirth;

    }

    public Customer(Scanner sc) {
        // ask for data to create a customer
        System.out.println("Enter customer name: ");
        String name = sc.nextLine();
        if (name.length() != 0) {
            this.name = name;
        }

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

        System.out.println("\nCustomer created successfully:\n" + this);
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

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Name: " + name + "; Address: " + address +"; Email: " + email + "; Phone number: " + phoneNumber+ "; Date of birth: " + dateOfBirth + ";\n";
    }
}


class Customers implements Serializable {
    private ArrayList<Customer> clients;
    

    protected Customers getCustomers() {
         loadcustomers();

         return this.customers;
    }
    
    public Customers() {
        System.out.println("Database Inicializada:");
        printClients();

        clients = new ArrayList<>();

    }

    //testing purposes
    protected void printClients() {
        System.out.println("\nCustomers:");
        for(Customer c : this.clients) {
            System.out.println(c);
        }
    }



    private boolean checkExists(Customer c) {
        String email = c.getEmail();
        if (this.clients != null) {
            for(Customer i : this.clients) {
                if(i.getEmail().equals(email)) {
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<Customer> getClients() {
        return this.clients;
    }

    protected void addCustomer(Customer c) {
        if (!checkExists(c)){
            this.clients.add(c); //update current clients

        } else{
            System.out.println("Email already registered!");
        }
    }

    protected Customer getCustomer(String email) {
        for(Customer c : this.clients) {
            if(c.getEmail().equals(email)) {
                return c;
            }
        }
        return null;
    }


    //Print all clients
    @Override
    public String toString() {
        String s = "";
        for(Customer c : clients) {
            s += c.toString()+"\n";
        }
        return s;
    }
}



