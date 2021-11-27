import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Customers implements Serializable {
    private final String FILE_NAME = "customers.obj";
    private File f = new File(FILE_NAME);


    private final String INIT_TXT = "clients.txt";
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
                        System.exit(1);

                    }

                }

                savecustomersOBJ();
                //System.out.println("Customers loaded successfully:");
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
                //System.out.println("Customers loaded successfully from OBJ file.");
                //printClients();
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

            oos.writeObject(this);
            //System.out.println("Saved!");

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




