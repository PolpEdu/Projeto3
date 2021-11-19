import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Customer{
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

        this.storeCustomer(); //store the customer in the file
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


        while (!email.contains("@") || email.contains("\t")) {
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
        this.storeCustomer(); //store the customer in the file
    }

    private void storeCustomer(){
        File f = new File("customers.txt");
        try {
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);

            String clienttoWrite = this.formatStore();
            bw.append(clienttoWrite);


            bw.close();
            fw.close();
        } catch (IOException ex) {
            System.out.println("Erro a escrever no ficheiro.");
        }
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

    private String formatStore() {
        return  name + "\t" + address +"\t" + email + "\t" + phoneNumber+ "\t" + dateOfBirth + "\n";
    }

    @Override
    public String toString() {
        return "Nome: " + name + "; Address: " + address +"; Email: " + email + "; Phone number: " + phoneNumber+ "; Date of birth: " + dateOfBirth + "\n";
    }
}

class Customers {
    private ArrayList<Customer> clients;

    public Customers() {
        this.clients = loadCustomersFromtxt();
    }

    //load to Customers
    private ArrayList<Customer> loadCustomersFromtxt() {
        ArrayList<Customer> customersList = new ArrayList<>();

        //load to Customers
        File f = new File("customers.txt");
        String line = "";
        if(f.exists() && f.isFile()) {
            try{
                FileReader fr= new FileReader(f);
                BufferedReader br= new BufferedReader(fr);

                // for each line get a customer
                while((line= br.readLine()) != null) {
                    addCustomer(line);
                }

                br.close();
                fr.close();
            } catch(FileNotFoundException ex) {
                System.out.println("Error while opening text file.");
            } catch(IOException ex) {
                System.out.println("Error while reading text file.");
            }

        } else{
            System.out.println("Database doesn't exist... Creating new Database...");
            try {
                f.createNewFile();
                System.out.println("File created.");
            } catch (IOException e) {
                System.out.println("Error while creating text file.");
            }
        }
        return customersList;
    }

    private void addCustomer(String line) {
        System.out.println(line);
        String[] customer = line.split("\t");
        String[] date = customer[4].split("/");
        Date d = new Date(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]));


        System.out.println(customer[0] +"?"+customer[1]+"?" + customer[2]+"?" +  Long.parseLong(customer[3])+"?"+ d);

        Customer c = new Customer(customer[0], customer[1], customer[2], Long.parseLong(customer[3]), d);

        addCustomer(c);
    }

    private void addCustomer(Customer client) {
        this.addCustomer(client);
    }

    //testing purposes
    private void printClients() {
        for(Customer c : clients) {
            System.out.println(c);
        }
    }

    private boolean validateClient(String email) {
        for(Customer c : clients) {
            if(c.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

}
