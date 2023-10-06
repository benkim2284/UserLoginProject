import java.io.*;
import java.util.ArrayList;

public class Master {
    private ArrayList<UserAccountInfo> accountList;
    private void getAllInfo(){

        accountList = new ArrayList<>();
        try{
            //READ BINARY FILE AS AN ARRAYLIST AND STORE IT INTO VARIABLE "accountList"
            FileInputStream istream = new FileInputStream("Accounts.dat");
            ObjectInputStream objectReader = new ObjectInputStream(istream);
            accountList = (ArrayList<UserAccountInfo>) objectReader.readObject();
            objectReader.close();
            for (UserAccountInfo current: accountList){
                System.out.println(current);
            }
        } catch (FileNotFoundException e){
            System.out.println("File doesn't exist");
        } catch (IOException e){
            System.out.println("Structure of data has changed. Restart File");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } 

    }

    public static void main(String[] args) {
        Master test = new Master();
        test.getAllInfo();
    }
}
