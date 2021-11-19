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

    public Customer() {
        // ask for data to create a customer
        Scanner sc = new Scanner(System.in);


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

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "Nome: " + name + "; Address: " + address +"; Email: " + email + "; Phone number: " + phoneNumber+ "; Date of birth: " + dateOfBirth + ";\n";
    }
}


class Customers extends DataBase implements Serializable {
    private ArrayList<Customer> clients;

    public Customers() {
        this.clients = new ArrayList<>();
    }

    //testing purposes
    private void printClients() {
        for(Customer c : clients) {
            System.out.println(c);
        }
    }

    private void addCustomer(Customer c) {
        if (!checkExists(c)){
            this.clients.add(c);
        } else{
            System.out.println("Email already registered!");
        }
    }

    private boolean checkExists(Customer c) {
        this.clients = super.load();

        String email = c.getEmail();
        for(Customer i : clients) {
            if(i.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
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

class DataBase {
    private static final String FILE_NAME = "customers.obj";
    private Customers customers;
    private File f = new File(FILE_NAME);

    //constructor
    public DataBase() {

        customers = load();
    }
    
    
    //load to Customers
    private Customers load() {
        Customers clients = null;
        if(f.isFile()) {
            try {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                clients = (Customers) ois.readObject();
                System.out.println("Updated:");
                System.out.println(clients);
                ois.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Error while opening object file.");
            } catch (IOException ex) {
                System.out.println("Error while reading object file. Probably due to it being corrupted.");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error while converting object.");
            }

        } else{
            System.out.println("Database is empty.\n");
        }
        return clients;
    }

    private void save(Customers customers) {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(customers);
            oos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error while creating file.");
        } catch (IOException ex) {
            System.out.println("Error while writing to the text file.");
        }
    }
    
}
