package main.java;

import java.io.*;
import java.util.Scanner;

abstract class Customer implements Serializable {
    private String name;
    private String address;
    private String email;
    private long phoneNumber;
    private Date dateOfBirth;
    private Orders orders;
    protected double transportValue=0;


    private final String FILE_NAME = "customerOrders.obj";
    private File f = new File(FILE_NAME);


    protected Customer(String name, String address, String email, String phoneNumber, Date dateOfBirth) {
        this.name = name;
        this.address = address;

        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address.");
        }
        this.email = email;


        if(!isValidPhoneNumber(phoneNumber)){
            throw new IllegalArgumentException("Invalid phone number. On client: "+this.name);
        }
        try {
            this.phoneNumber = Long.parseLong(phoneNumber);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Couldn't parse phone number.");
        }

        this.dateOfBirth = dateOfBirth;

        this.orders = new Orders();
    }

    /*protected Customer(Scanner sc) {
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
    }*/

    protected Customer(Customer customer) {
        this.name = customer.name;
        this.address = customer.address;
        this.email = customer.email;
        this.phoneNumber = customer.phoneNumber;
        this.dateOfBirth = customer.dateOfBirth;
        this.orders = customer.orders;
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

    public void appendOrders(Order order) {
        this.orders.addOrder(order);
    }

    public Orders getOrders() {
        return this.orders;
    }

    public double getTransportValue(double orderValue) {
        return this.transportValue;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Name: " + name + "; Address: " + address +"; Email: " + email + "; Phone number: " + phoneNumber+ "; Date of birth: " + dateOfBirth+"\n" +
                "Orders: " + orders;
    }
}

class RegularCustomer extends Customer implements Serializable {
    public RegularCustomer(String name, String address, String email, String phoneNumber, Date dateOfBirth) {
        super(name, address, email, phoneNumber, dateOfBirth);
    }

    @Override
    public double getTransportValue(double orderValue) {
        return 20;
    }

    @Override
    public String toString() {
        return "Regular customer: " + super.toString();
    }
}

class FrequentCustomer extends Customer implements Serializable {
    public FrequentCustomer(String name, String address, String email, String phoneNumber, Date dateOfBirth) {
        super(name, address, email, phoneNumber, dateOfBirth);
    }

    private void calctransportValue(double orderValue) {
        this.transportValue = orderValue > 40 ? 0.0 : 15.0;
    }

    @Override
    public double getTransportValue(double orderValue) {
        calctransportValue(orderValue); //assign correct value to transportValue
        return this.transportValue;
    }

    @Override
    public String toString() {
        return "Frequent customer: " + super.toString();
    }
}
