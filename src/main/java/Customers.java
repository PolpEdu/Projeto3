package main.java;

import jdk.jfr.Experimental;

import java.io.*;
import java.util.ArrayList;

/**
 * Class that represents a list of all the Customers.
 *
 * @author Eduardo Nunes
 */
class Customers implements Serializable {
    /**
     * Constant that represents the object file name of the Customers.
     */
    private final String FILE_NAME = "customers.obj";

    /**
     * Constant that represents the object file of the Customers.
     */
    private File f = new File(FILE_NAME);


    /**
     * Constant that represents the text file name of the Customers
     */
    private final String INIT_TXT = "clients.txt";
    /**
     * Constant that represents the text file of the Customers
     */
    private File t = new File(INIT_TXT);


    /**
     * List of all the {@link Customer Customers}.
     */
    private ArrayList<Customer> clients;

    /**
     * Constructor of the {@link Customers} class that loads the customers from the files.
     */
    public Customers() {
        clients = new ArrayList<>(); //empty list
        loadcustomers(); //load customers from file
    }

    /**
     * Method that loads the {@link Customers} from the files.
     *
     * @throws IOException
     *
     * It checks if the .obj file exists and if it does, it loads the customers from it.
     * If it doesn't, it loads the customers from the default .txt file.
     *
     */
    private void loadcustomers() {
        if (f.exists() && f.isFile()) { //if the file exists
            loadcustomersOBJ(); //load customers from obj file
        }
        else {
            loadcustomerstxt(); //load customers from text file
        }
    }

    /**
     * Method that loads the {@link Customers} from the default .txt file.
     *
     * @throws ArrayIndexOutOfBoundsException
     * @throws FileNotFoundException
     * @throws IOException
     *
     */
    private void loadcustomerstxt() {
        if(t.exists() && t.isFile()) { //check if the file exists to prevent errors
            try {
                FileReader fr = new FileReader(t);
                BufferedReader br = new BufferedReader(fr);

                String line; // line of the file
                while((line= br.readLine()) != null) { // loop line by line
                    try {
                        String[] arrOfStr = line.split(";"); // split the line by ;

                        String[] arrOfStr2 = arrOfStr[5].split("/"); //split the date (the last element in the list) by /

                        Date date = new Date(Integer.parseInt(arrOfStr2[0]), Integer.parseInt(arrOfStr2[1]), Integer.parseInt(arrOfStr2[2])); //create a new date object

                        Customer c;
                        if (arrOfStr[0].equals("F")) {
                            c = new FrequentCustomer(arrOfStr[1], arrOfStr[2], arrOfStr[3], arrOfStr[4], date); // create a new Frequent Customer object if the first element is F
                        } else if (arrOfStr[0].equals("R")) {
                            c = new RegularCustomer(arrOfStr[1], arrOfStr[2], arrOfStr[3], arrOfStr[4], date); // create a new Regular Customer object if the first element is R
                        }
                        else {
                            System.out.println("Invalid date format.\n Text file is corrupted (not in the correct format)."); // if the first element is not F or R, it means that the file is corrupted
                            return;
                        }


                        try {
                            this.addCustomer(c); // try add the customer to the list, if it fails, it means that the customer already exists
                        } catch (Exception e) {
                            System.out.println("Error while adding a customer: "+c+ "from the prebuilt text file.");
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Invalid date format.\n Text file is corrupted (not in the correct format)."); // if the date is not in the correct format, it means that the file is corrupted
                        System.exit(1); //Exit the program with error code 1 - error.
                    }

                }

                savecustomersOBJ(); //save the customers to the .obj file

                //? System.out.println("Customers loaded successfully:");
                //? this.printClients();

                //close the streams
                br.close();
                fr.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Error while opening object file.");
            } catch (IOException ex) {
                System.out.println("Error while reading object file. Probably due to it being corrupted.\n"+ex);
            }

        } else {
            System.out.println("Error. No DataBase not found. Exiting..."); // Couldn't find the .txt file
            System.exit(1); //Exit the program with error code 1 - error.
        }
    }

    /**
     * Method that loads the {@link Customers} from the .obj file and assigns them to the current list.
     *
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void loadcustomersOBJ(){
        if (f.isFile()) { //check if the file exists to prevent errors
            try {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Customers lido = (Customers) ois.readObject(); //read the object from the file

                this.clients = lido.clients; //assign the clients list from the object read to the current object list

                //? System.out.println("Customers loaded successfully from OBJ file.");
                //? printClients();

                //close the streams
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

    /**
     * Method that saves the current {@link Customers} into to the .obj file.
     *
     * @throws FileNotFoundException
     * @throws IOException
     *
     */
    public void savecustomersOBJ() {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this);
            //System.out.println("Saved!");

            //close the streams
            oos.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error while creating file.");
        } catch (IOException ex) {
            System.out.println("Error while writing to the OBJ file.\n"+ex);
        }
    }

    /**
     * Test Method that prints all the current customers.
     */
    @SuppressWarnings("unused")
    private void printClients() {
        System.out.println("\nCustomers:");
        // loop in all customers
        for(Customer c : this.clients) {
            System.out.println(c); //print customer by customer
        }
    }

    /**
     * Method that checks if a {@link Customer} already exists in the list by email.
     *
     * @param c Customer to be checked.
     * @return true if the Customer already exists, false otherwise.
     */
    private boolean checkExists(Customer c) {
        String email = c.getEmail();
        if (this.clients != null) { //check if the list is not empty
            for(Customer i : this.clients) { //loop in all customers
                if(i.getEmail().equals(email)) { //check if the email is the same
                    return true; //if it is, return true
                }
            }
        }
        return false; //if it is not, return false
    }

    /**
     * Method that removes a {@link Customer} from the {@link #clients customers} list.
     *
     * (Deprecated because it is not used in the current version of the program)
     * @param c Customer to be removed.
     */
    @Deprecated
    private void removeCustomer(Customer c) {
        this.clients.remove(c);
    }

    /**
     * Method that adds a {@link Customer} to the {@link #clients customers} list.
     *
     * @param c Customer to be added.
     */
    public void addCustomer(Customer c) {
        if (!checkExists(c)){
            this.clients.add(c); //update current clients

        } else{
            System.out.println("Customer already registered!");
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




