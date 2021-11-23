class DataToSave {
    //class que tem tudo o que pretendo guardar na base de dados
    private static final String FILE_NAME = "customers.obj";
    private File f = new File(FILE_NAME);
    private Customers customers;

    //load to Customers
    private void loadcustomers() {
        if (!f.exists()){
            System.out.println("File not found. Creating new file.");
            try {
                f.createNewFile();
                return;
            }
            catch (IOException e) {
                System.out.println("Couldn't create file.");
            }
        }
        if (f.isFile()) {
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.customers = (Customers) ois.readObject();

            ois.close();
            fis.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error while opening object file.");
        } catch (IOException ex) {
            System.out.println("Error while reading object file. Probably due to it being corrupted.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error while converting object.");
        }
        }
    }


    protected void savecustomers() {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this.customers);
            System.out.println("Saved!");
            oos.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error while creating file.");
        } catch (IOException ex) {
            System.out.println("Error while writing to the text file.");
        }
    }
}