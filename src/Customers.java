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


        System.out.print("Enter customer name: ");
        this.name = sc.nextLine();


        System.out.print("Enter customer address: ");
        this.address = sc.nextLine();



        System.out.print("Enter customer email: ");
        String email = sc.nextLine();


        while (!email.contains("@")) {
            System.out.print("Invalid email address. Please enter again: ");
            email = sc.nextLine();

        }
        this.email = email;

        System.out.print("Enter customer phone number: ");
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
    private static final String FILE_NAME = "customers.obj";
    private File f = new File(FILE_NAME);
    private ArrayList<Customer> clients;

    public Customers() {
        System.out.println("Database Inicializada:");
        clients = new ArrayList<>();
        load();
    }

    //testing purposes
    protected void printClients() {
        System.out.println("\nCustomers:");
        for(Customer c : this.clients) {
            System.out.println(c);
        }
    }

    protected void addCustomer(Customer c) {
        if (!f.exists()){
            System.out.println("File doesnt exist.");
            return;
        }

        if (!checkExists(c)){
            this.clients.add(c); //update current clients
            save(); //save to the db new clients

        } else{
            System.out.println("Email already registered!");
        }
    }

    private boolean checkExists(Customer c) {
        //refresh db
        load();

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

    //load to Customers
    private void load() {
        if (!f.exists()){
            System.out.println("File not found. Creating new file.");

            try {
                f.createNewFile();
            }
            catch (IOException e) {
                System.out.println("Couldn't create file.");
            }
        }

        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Customers reloaded = (Customers) ois.readObject();
            System.out.println(reloaded);

            this.clients = reloaded.clients;

            ois.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error while opening object file.");
        } catch (IOException ex) {
            System.out.println("Error while reading object file. Probably due to it being corrupted.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error while converting object.");
        } /*else{
            System.out.println("Database is empty.\n");
        }*/
    }

    private void save() {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this);
            System.out.println("Saved!");
            oos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error while creating file.");
        } catch (IOException ex) {
            System.out.println("Error while writing to the text file.");
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
