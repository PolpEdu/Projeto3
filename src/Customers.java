import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Customer implements Serializable {
    private String name;
    private String address;
    private String email;
    private long phoneNumber;
    private Date dateOfBirth;
    protected double transportValue=20;

    public Customer(String name, String address, String email, String phoneNumber, Date dateOfBirth) {
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
        return "Name: " + name + "; Address: " + address +"; Email: " + email + "; Phone number: " + phoneNumber+ "; Date of birth: " + dateOfBirth;
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

    private double calctransportValue(double orderValue) {
        return orderValue > 40 ? 0.0 : 15.0;
    }

    @Override
    public double getTransportValue(double orderValue) {
        this.transportValue = calctransportValue(orderValue);
        return this.transportValue;
    }

    @Override
    public String toString() {
        return "Frequent customer: " + super.toString();
    }
}



class Customers implements Serializable {
    private static final String FILE_NAME = "customers.obj";
    private File f = new File(FILE_NAME);


    private static final String INIT_TXT = "clients.txt";
    private File t = new File(INIT_TXT);

    private ArrayList<Customer> clients;

    public Customers() {
        clients = new ArrayList<>();
        loadcustomers();
    }

    private void loadcustomers() {
        if (f.exists() && f.isFile()) {
            loadcustomersOBJ();
        }
        else {
            loadcustomerstxt();
        }
    }

    //load to Customers
    private void loadcustomerstxt() {
        if(t.exists() && t.isFile()) {
            try {
                FileReader fr = new FileReader(t);
                BufferedReader br = new BufferedReader(fr);

                String line;
                while((line= br.readLine()) != null) {
                    try {
                        String[] arrOfStr = line.split(";");

                        String[] arrOfStr2 = arrOfStr[5].split("/"); //date

                        Date date = new Date(Integer.parseInt(arrOfStr2[0]), Integer.parseInt(arrOfStr2[1]), Integer.parseInt(arrOfStr2[2]));

                        Customer c;
                        if (arrOfStr[0].equals("F")) {
                            c = new FrequentCustomer(arrOfStr[1], arrOfStr[2], arrOfStr[3], arrOfStr[4], date);
                        } else if (arrOfStr[0].equals("R")) {
                            c = new RegularCustomer(arrOfStr[1], arrOfStr[2], arrOfStr[3], arrOfStr[4], date);
                        }
                        else {
                            System.out.println("Invalid date format.\n Text file is corrupted (not in the correct format).");
                            return;
                        }


                        try {
                            this.addCustomer(c);
                        } catch (Exception e) {
                            System.out.println("Error while adding a customer: "+c+ "from the prebuilt text file.");
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Invalid date format.\n Text file is corrupted (not in the correct format).");
                        System.exit(0);
                    }

                }

                System.out.println("Customers loaded successfully:");
                savecustomersOBJ();
                //this.printClients();

                br.close();
                fr.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Error while opening object file.");
            } catch (IOException ex) {
                System.out.println("Error while reading object file. Probably due to it being corrupted.\n"+ex);
            }

        } else {
            System.out.println("Error. No DataBase not found. Exiting...");
            System.exit(1);
        }



    }

    private void loadcustomersOBJ(){
        if (f.isFile()) {
            try {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Customers lido = (Customers) ois.readObject();

                this.clients = lido.getClients();

                ois.close();
                fis.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Error while opening object file.");
            } catch (IOException ex) {
                System.out.println("Error while reading object file. Probably due to it being corrupted.\n"+ex);
            } catch (ClassNotFoundException ex) {
                System.out.println("Error while converting object.");
            }
        }
    }

    private void savecustomersOBJ() {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            Customers c = this;
            oos.writeObject(c);
            System.out.println("Saved!");

            oos.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error while creating file.");
        } catch (IOException ex) {
            System.out.println("Error while writing to the OBJ file.\n"+ex);
        }
    }

    private ArrayList<Customer> getClients() {
        //System.out.println(this.clients);
        return this.clients;
    }


    //testing purposes
    private void printClients() {
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


    private void addCustomer(Customer c) {
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




