package main.java;

import java.io.*;
import java.util.ArrayList;

class Promotions implements Serializable {
    private final String FILE_NAME = "promotions.obj";
    private File f = new File(FILE_NAME);


    private final String INIT_TXT = "promotions.txt";
    private File t = new File(INIT_TXT);

    private ArrayList<Promotion> promotions;
    //load from the txt file

    public Promotions() {
        this.promotions = new ArrayList<>();
        loadpromos();
    }

    private void addPromotion(Promotion p) {
        for (Promotion pros : promotions) {
            if (pros.getStartDate().equals(p.getStartDate()) && pros.getEndDate().equals(p.getEndDate())) {
                throw new IllegalArgumentException("Promotion already exists.");
            }
        }
        this.promotions.add(p);
    }

    private void loadpromos() {
        if (f.exists() && f.isFile()) {
            loadpromotionsOBJ();
        }
        else {
            //loaded from TXT
            loadpromotionstxt();
        }
    }

    public void savepromotionsOBJ() {
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

    private void loadpromotionsOBJ(){
        if (f.isFile()) {
            try {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Promotions lido = (Promotions) ois.readObject();

                this.promotions = lido.promotions;
                //System.out.println("Promotions loaded successfully from OBJ file.");

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
    //load to Customers
    private void loadpromotionstxt() {
        if(t.exists() && t.isFile()) {
            try {
                FileReader fr = new FileReader(t);
                BufferedReader br = new BufferedReader(fr);

                String line;
                while((line= br.readLine()) != null) {
                    try {
                        String[] arrOfStr = line.split(";");

                        String[] arrOfStr2 = arrOfStr[1].split("/"); //date

                        Date start = new Date(Integer.parseInt(arrOfStr2[0]), Integer.parseInt(arrOfStr2[1]), Integer.parseInt(arrOfStr2[2]));
                        String[] arrOfStr3 = arrOfStr[2].split("/"); //date
                        Date end = new Date(Integer.parseInt(arrOfStr3[0]), Integer.parseInt(arrOfStr3[1]), Integer.parseInt(arrOfStr3[2]));


                        Promotion p;
                        if (arrOfStr[0].equals("PL")) {
                            p = new PL(start,end);
                        } else if (arrOfStr[0].equals("PTTF")) {
                            p = new PTTF(start,end);
                        }
                        else {
                            System.out.println("Invalid date format.\n Text file is corrupted (not in the correct format).");
                            return;
                        }


                        try {

                            this.addPromotion(p);
                        } catch (Exception e) {
                            System.out.println("Error while adding a promotion: "+p+ "from the prebuilt text file.");
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Invalid date format.\n Text file is corrupted (not in the correct format).");
                        System.exit(1);
                    }
                }

                savepromotionsOBJ();
                //System.out.println("Promotions loaded successfully:");
                //this.printPromotions();

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

    //check appliable promotions
    public Promotion checkPromotions(Order c){
        for (Promotion p: promotions){
            if (p.isApplicable(c.getDate())){
                return p;
            }
        }
        return null;
    }
    //print promotions
    public void printPromotions(){
        for (Promotion p : this.promotions) {
            System.out.println(p);
        }
    }

}