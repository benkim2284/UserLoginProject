import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class MainLoginPage extends JFrame{
    private String username;
    private String password;

    public MainLoginPage(){
        this.setSize(500,400);
        this.setTitle("Secret Message App: Login");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildPanel();
        this.setVisible(true);
    }
    private void buildPanel(){

        //MAIN PANEL, there will be other subpanels within this panel.
        JPanel mainPanel = new JPanel();
        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);
        //creating title for the LOGIN PAGE
        TitledBorder panelTitle= new TitledBorder("User Login");
        panelTitle.setTitleJustification(TitledBorder.CENTER);
        panelTitle.setTitlePosition(TitledBorder.TOP);
        mainPanel.setBorder(panelTitle);

        //subpanel 1 for username input
        JPanel subPanel1 = new JPanel();
        JLabel userLabel = new JLabel("   Email:");
        JTextField userInput = new JTextField(20);
        subPanel1.add(userLabel);
        subPanel1.add(userInput);

        //subpanel 2 for password input
        JPanel subPanel2 = new JPanel();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passInput = new JPasswordField(20);
        subPanel2.add(passLabel);
        subPanel2.add(passInput);

        //subpanel 3 for Link to Create Account
        JPanel subPanel3 = new JPanel();

        JButton createAccount = new JButton("Create Account");
        createAccount.setPreferredSize(new Dimension(120,40));
        createAccount.setVisible(true);

        JLabel accWarning = new JLabel("");

        subPanel3.add(accWarning);
        subPanel3.add(createAccount);

        //subpanel 4 for Login button
        JPanel subPanel4 = new JPanel();
        JButton enter = new JButton("Login");
        enter.setPreferredSize(new Dimension(80,40));
        subPanel4.add(enter);

        //adding all the subpanels to mainpanel
        mainPanel.add(subPanel1);
        mainPanel.add(subPanel2);
        mainPanel.add(subPanel4);
        mainPanel.add(subPanel3);




        //adding mainpanel to Frame
        super.add(mainPanel);


        //ActionListener for the "Login" button
        enter.addActionListener(event->{
            //initialize boolean variable
            boolean wrongUserOrPass=false;
            username = userInput.getText();
            password = passInput.getText();
            //Tries to open the file
            try{
                //READ BINARY FILE AS AN ARRAYLIST AND STORE IT INTO VARIABLE "accountList"
                FileInputStream istream = new FileInputStream("Accounts.dat");
                ObjectInputStream objectReader = new ObjectInputStream(istream);
                ArrayList<UserAccountInfo> accountList = new ArrayList<>();
                accountList =  (ArrayList<UserAccountInfo>) objectReader.readObject();
                objectReader.close();

                //CHECKS TO SEE IF the user's account exists and if the username and password match.
                for (UserAccountInfo current: accountList){
                    if (current.getUsername().equals(username) && current.getPassword().equals(password)){
                        this.dispose();
                        UserSpecificPage test = new UserSpecificPage(current, accountList);
                    }else if (current.getUsername().equals(username) ^ current.getPassword().equals(password)){
                        wrongUserOrPass = true;
                    }
                }
                if (wrongUserOrPass){
                    //checks if at least username or password matched up
                    accWarning.setText("Username or Password is Incorrect");
                    createAccount.setVisible(false);
                }else{
                    //if none of the usernames and passwords matched, then
                    accWarning.setText("Account Does Not Exist --->");
                    createAccount.setVisible(true);
                }
            } catch (FileNotFoundException e){
                accWarning.setText("Account Does Not Exist --->");
            } catch(IOException e){
                System.out.println("Structure of Contact data changed! \n Can't load existing file. Start new list");
            } catch(ClassNotFoundException e){
                System.out.println(e.getMessage());
            }
        });

        //Once user clicks the "Create Account" button, it will redirect them to a site.
        createAccount.addActionListener(event-> {
            this.dispose();
            MakeAccountPage test = new MakeAccountPage();
        });


    }

    public static void main(String[] args) {
        MainLoginPage test = new MainLoginPage();
    }


}
