import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MakeAccountPage extends JFrame{
    private String username;
    private String password;

    private boolean usernameValid = false;
    private boolean passwordValid = false;
    private boolean passwordIsVerified = false;

    private ArrayList<UserAccountInfo> accountList;


    public MakeAccountPage(){
        accountList = new ArrayList<>();
        try{
            //READ BINARY FILE AS AN ARRAYLIST AND STORE IT INTO VARIABLE "accountList"
            FileInputStream istream = new FileInputStream("Accounts.dat");
            ObjectInputStream objectReader = new ObjectInputStream(istream);
            accountList = (ArrayList<UserAccountInfo>) objectReader.readObject();
            objectReader.close();
        } catch (FileNotFoundException e){

        } catch(IOException e){
            System.out.println("Structure of Contact data changed! \n Can't load existing file. Start new list");
        } catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }


        this.setSize(500,400);
        this.setTitle("Secret Message App: Create Account");
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
        TitledBorder panelTitle= new TitledBorder("Create Account");
        panelTitle.setTitleJustification(TitledBorder.CENTER);
        panelTitle.setTitlePosition(TitledBorder.TOP);
        mainPanel.setBorder(panelTitle);

        //subPanel a for menuheading, which will directly be inserted to super.
        JMenuBar menuBar = new JMenuBar();
        JMenu item = new JMenu("Settings");
        JMenuItem returnLogin = new JMenuItem("Return to Login");
        JMenuItem exit = new JMenuItem("Exit");

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        returnLogin.setBorder(border);
        exit.setBorder(border);
        menuBar.setBorder(border);
        //nesting it appropriately
        item.add(returnLogin);
        item.add(exit);
        menuBar.add(item);

        //subpanel 1 for username input
        JPanel subPanel1 = new JPanel();
        JLabel userLabel = new JLabel("               Email:");
        JTextField userInput = new JTextField(20);
        subPanel1.add(userLabel);
        subPanel1.add(userInput);

        //subpanel 2 for Message Displayed to User about username
        JPanel subPanel2 = new JPanel();
        JLabel userWarning = new JLabel("");
        subPanel2.add(userWarning);

        //subpanel 3 for password input
        JPanel subPanel3 = new JPanel();
        JLabel passLabel = new JLabel("            Password:");
        JPasswordField passInput = new JPasswordField(20);
        subPanel3.add(passLabel);
        subPanel3.add(passInput);

        //subpanel 3b for password confirmation input
        JPanel subPanel3b = new JPanel();
        JLabel passConfirmLabel = new JLabel("Re-enter Password:");
        JPasswordField passConfirm = new JPasswordField(20);
        subPanel3b.add(passConfirmLabel);
        subPanel3b.add(passConfirm);

        //subpanel 3c for message displayed whether re-entered password matches or not
        JPanel subPanel3c = new JPanel();
        JLabel passVerification = new JLabel("");
        subPanel3c.add(passVerification);

        //subpanel 4 for Message Displayed to User about password
        JPanel subPanel4 = new JPanel();
        JLabel passWarning = new JLabel("<html>Enter a Valid Password That Contains:<br/>One Lowercase Letter<br/>One Uppercase Letter<br/>One Digit<br/>One Special Character<br/>No White Space<br/>Minimum 8 characters, Maximum 20 characters</html>");
        subPanel4.add(passWarning);

        //subpanel 5 for Enter button
        JPanel subPanel5 = new JPanel();
        JButton enter = new JButton("Create Account");
        enter.setPreferredSize(new Dimension(130,40));
        enter.setVisible(false);
        subPanel5.add(enter);

        //adding all the subpanels to mainpanel
        mainPanel.add(subPanel1);
        mainPanel.add(subPanel2);
        mainPanel.add(subPanel3);
        mainPanel.add(subPanel4);
        mainPanel.add(subPanel3b);
        mainPanel.add(subPanel3c);

        mainPanel.add(subPanel5);

        //adding mainpanel to Frame
        super.add(menuBar, BorderLayout.NORTH);
        super.add(mainPanel, BorderLayout.CENTER);

        //ActionListener for the "Return to Login"
        returnLogin.addActionListener(event->{
            this.dispose();
            MainLoginPage test = new MainLoginPage();
        });

        //Actionlistener for Exit
        exit.addActionListener(event->{
            System.exit(0);
        });

        //keyboard listener for username requirements
        userInput.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                //VALID EMAIL CHECKER
                String email = userInput.getText();
                Pattern userPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");

                Matcher mat = userPattern.matcher(email);

                if(mat.matches()){
                    userWarning.setText("This is a Valid Email");
                    //reassigning value of boolean so that we can use it in the actionListener for the "enter" button
                    usernameValid = true;
                    //clause checking to see if ALL CONDITION ARE VALID:WE NEED IN EVERY ACTIONLISTENER SO THAT IT UPDATES CONSTANTLY
                    if (passwordValid && usernameValid && passwordIsVerified){
                        enter.setVisible(true);
                    } else{
                        enter.setVisible(false);
                    }
                    //username exists checking needed

                }else{
                    userWarning.setText("Please Enter A Valid Email");
                    usernameValid = false;
                    enter.setVisible(false);
                }

            }
        });

        //keyboard listener for password requirements
        passInput.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                //VALID PASSWORD CHECKER
                String hola = passInput.getText();
                Pattern passPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_@#$%^&--+=().])(?=\\S+$).{8,20}$");

                Matcher mat = passPattern.matcher(hola);

                if(mat.matches()){
                    passWarning.setText("This is a Valid Password");
                    //reassigning value of boolean so that we can use it in the actionListener for the "enter" button
                    passwordValid = true;
                    //chekcs to see if passwords are matched, and if not,the enter button stays invisible.
                    if(passConfirm.getText().equals(passInput.getText())){
                        passVerification.setText("Passwords Match!");
                        passwordIsVerified=true;
                    }else{
                        passVerification.setText("Passwords Do Not Match!");
                        passwordIsVerified=false;
                        enter.setVisible(false);
                    }
                    //clause checking to see if ALL CONDITION ARE VALID:WE NEED IN EVERY ACTIONLISTENER SO THAT IT UPDATES CONSTANTLY
                    if (passwordValid && usernameValid && passwordIsVerified){
                        enter.setVisible(true);
                    } else{
                        enter.setVisible(false);
                    }
                }else{
                    passWarning.setText("<html>Enter a Valid Password That Contains:<br/>One Lowercase Letter<br/>One Uppercase Letter<br/>One Digit<br/>One Special Character<br/>No White Space<br/>Minimum 8 characters, Maximum 20 characters</html>");
                    passwordValid=false;
                    enter.setVisible(false);
                }

            }
        });

        passConfirm.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {

                if(passConfirm.getText().equals(passInput.getText())){
                    passVerification.setText("Passwords Match!");
                    passwordIsVerified=true;
                }else{
                    passVerification.setText("Passwords Do Not Match!");
                    passwordIsVerified=false;
                    enter.setVisible(false);
                }
                //clause checking to see if ALL CONDITION ARE VALID:WE NEED IN EVERY ACTIONLISTENER SO THAT IT UPDATES CONSTANTLY
                if (passwordValid && usernameValid && passwordIsVerified){
                    enter.setVisible(true);
                } else{
                    enter.setVisible(false);
                }
            }
        });

        //ActionListener for the "enter" button
        enter.addActionListener(event->{
            //initializing boolean variable
            boolean alreadyExists=false;
            //first checks to see if password and username are valid before assigning these values to username and password
            if (usernameValid && passwordValid && passwordIsVerified) {
                username = userInput.getText();
                password = passInput.getText();
                //checking to see if any emails that are already in the database match with the inputted email.
                for (UserAccountInfo current : accountList) {
                    if (current.getUsername().equals(username)) {
                        alreadyExists = true;
                    }
                }

                //if there are no matching emails founds, then we make it store it as a new username and password in our database.
                if (!alreadyExists) {
                    //Adding username and password to our ArrayList of UserAccountInfo objects.
                    UserAccountInfo userInfoObj = new UserAccountInfo(username, password);
                    accountList.add(userInfoObj);
                    //Adding ArrayList to our database file "Accounts.dat";
                    try {
                        FileOutputStream ostream = new FileOutputStream("Accounts.dat");
                        ObjectOutputStream objectWriter = new ObjectOutputStream(ostream);
                        objectWriter.writeObject(accountList);
                        objectWriter.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }

                    JOptionPane.showMessageDialog(this, "Account Created! Directing you to the login page...");
                    this.dispose();
                    MainLoginPage test = new MainLoginPage();
                } else {
                    //If matching email has been found, it will display this message to the user.
                    JOptionPane.showMessageDialog(this, "Email is already being used!");
                }
            }
        });

    }


}
