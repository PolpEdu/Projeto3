package main.java;

import java.io.*;
import java.util.ArrayList;

/**
 * Class that represents a list of all the Promotions.
 *
 * @author Eduardo Nunes
 */
class Promotions implements Serializable {
    /**
     * Constant that represents the object file name of the Promotions.
     */
    private final String FILE_NAME = "promotions.obj";

    /**
     * Constant that represents the object file of the Promotions.
     */
    private File f = new File(FILE_NAME);

    /**
     * Constant that represents the text file name of the Promotions
     */
    private final String INIT_TXT = "promotions.txt";

    /**
     * Constant that represents the text file of the Promotions
     */
    private File t = new File(INIT_TXT);

    /**
     * List of all the {@link Promotion Promotions}.
     */
    private ArrayList<Promotion> promotions;

    /**
     * Constructor of the {@link Promotions} class that loads the promotions from the files.
     *
     * @throws IOException If we can't read the file.
     * @throws ClassNotFoundException If we can't find the class that we are trying to load.
     *
     */
    public Promotions() throws IOException, ClassNotFoundException {
        this.promotions = new ArrayList<>(); //initialize the list of promotions with an empty list
        loadpromos(); //load the promotions from the file
    }

    /**
     * Method that loads the {@link Promotions} from the files.
     *
     * It checks if the .obj file exists and if it does, it loads the promotions from it.
     * If it doesn't, it loads the promotions from the default .txt file.
     *
     * @throws IOException If we can't read the file.
     * @throws ClassNotFoundException If we can't find the class that we are trying to load.
     *
     */
    private void loadpromos() throws IOException, ClassNotFoundException {
        if (f.exists() && f.isFile()) { //if the file exists and is a file
            loadpromotionsOBJ(); // load the promotions from the .obj file
        }
        else {
            loadpromotionstxt(); // load the promotions from the .txt file
        }
    }

    /**
     * Method that adds a {@link Promotion} to the {@link #promotions} list.
     *
     * @throws IllegalArgumentException If the promotion already exists in the current {@link #promotions} list.
     *
     * @param p {@link Promotion} to be added.
     */
    private void addPromotion(Promotion p) throws IllegalArgumentException {
        for (Promotion pros : promotions) { //check if the promotion is already in the list
            if (pros.getStartDate().equals(p.getStartDate()) && pros.getEndDate().equals(p.getEndDate())) {
                throw new IllegalArgumentException("Promotion already exists.");
            }
        }
        this.promotions.add(p); //add the promotion to the list
    }

    /**
     * Method that saves the current {@link Promotions} into to the .obj file.
     *
     * @throws FileNotFoundException If we can't find the file.
     * @throws IOException If we can't write to the file.
     *
     */
    public void savepromotionsOBJ() throws FileNotFoundException, IOException {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this); //write this class to the file
            //? System.out.println("Saved!");

            //close the stream
            oos.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error while creating file.");
        } catch (IOException ex) {
            System.out.println("Error while writing to the OBJ file.\n"+ex);
        }
    }

    /**
     * Method that loads the {@link Promotions} from the .obj file and assigns them to the current {@link #promotions} list.
     *
     * @throws IOException If we can't read the file.
     * @throws ClassNotFoundException If we can't find the class that we are trying to load.
     * @throws FileNotFoundException If we can't find the file.
     */
    private void loadpromotionsOBJ() throws IOException, ClassNotFoundException, FileNotFoundException {
        if (f.isFile()) { // check if the file exists to prevent errors
            try {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Promotions lido = (Promotions) ois.readObject();

                this.promotions = lido.promotions; //assign the promotions from the file to the current promotions list
                //? System.out.println("Promotions loaded successfully from OBJ file.");

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
     * Method that loads the {@link Promotions} from the default .txt file.
     *
     * @throws ArrayIndexOutOfBoundsException In case we try to access an index that contains data that doesn't exist.
     * @throws FileNotFoundException If we can't find the file.
     * @throws IOException If we can't read the file.
     *
     */
    private void loadpromotionstxt() throws ArrayIndexOutOfBoundsException, FileNotFoundException, IOException {
        if(t.exists() && t.isFile()) { //check if the file exists to prevent errors
            try {
                FileReader fr = new FileReader(t);
                BufferedReader br = new BufferedReader(fr);

                String line; // string that represents a line of the file
                while((line= br.readLine()) != null) { //loop line by line
                    try {
                        String[] arrOfStr = line.split(";"); //split the line by the ; character

                        String[] arrOfStr2 = arrOfStr[1].split("/"); //split the date (the second element of the line) by the / character

                        Date start = new Date(Integer.parseInt(arrOfStr2[0]), Integer.parseInt(arrOfStr2[1]), Integer.parseInt(arrOfStr2[2])); //create a new date object with the date from the file
                        String[] arrOfStr3 = arrOfStr[2].split("/"); //split the second date (the third element of the line) by the / character
                        Date end = new Date(Integer.parseInt(arrOfStr3[0]), Integer.parseInt(arrOfStr3[1]), Integer.parseInt(arrOfStr3[2])); //create a new date object with the date from the file


                        Promotion p; // Promotion object that will be created
                        if (arrOfStr[0].equals("PL")) { //if the promotion type is PL
                            p = new PL(start,end); //create a new PL object and assign it to the Promotion object
                        } else if (arrOfStr[0].equals("PTTF")) { //if the promotion type is PTTF
                            p = new PTTF(start,end); //create a new PTTF object and assign it to the Promotion object
                        }
                        else {
                            System.out.println("Invalid date format.\n Text file is corrupted (not in the correct format)."); // if the promotion type is not PL or PTTF
                            return;
                        }


                        try {
                            this.addPromotion(p); //try to add the promotion to the list, if it fails, it means that the promotion is already in the list
                        } catch (Exception e) {
                            System.out.println("Error while adding a promotion: "+p+ "from the prebuilt text file.");
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Invalid date format.\n Text file is corrupted (not in the correct format).");
                        System.exit(1); //Exit the program with an error code (1)
                    }
                }

                savepromotionsOBJ(); //save the promotions to the .obj file

                //? System.out.println("Promotions loaded successfully:");
                //? this.printPromotions();

                //close the streams
                br.close();
                fr.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Error while opening object file.");
            } catch (IOException ex) {
                System.out.println("Error while reading object file. Probably due to it being corrupted.\n"+ex);
            }

        } else {
            System.out.println("Error. No DataBase not found. Exiting...");
            System.exit(1); //Exit the program with an error code (1)
        }
    }

    /**
     * Method to check if at the date of a given {@link Order} there is a promotion that can be applied.
     *
     * @param c The {@link Order} that contains a {@link Order#orderDate}.
     *
     * @return The {@link Promotion} that can be applied or null, if there is no promotion that can be applied.
     */
    public Promotion checkPromotions(Order c){
        for (Promotion p: promotions){ //loop through all the promotions
            if (p.isApplicable(c.getDate())){ //if the promotion is applicable
                return p; //return the promotion
            }
        }
        return null; //if there's no promotion that can be applied, return null
    }

    /**
     * Method to print all the {@link #promotions} in the list.
     */
    @SuppressWarnings("unused")
    public void printPromotions(){
        for (Promotion p : this.promotions) { //loop through all the promotions in the list
            System.out.println(p); //print the promotion
        }
    }

}