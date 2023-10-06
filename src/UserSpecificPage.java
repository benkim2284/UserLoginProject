import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class UserSpecificPage extends JFrame {
    private UserAccountInfo userObject;
    private ArrayList<UserAccountInfo> accountList;

    public UserSpecificPage(UserAccountInfo userObj, ArrayList<UserAccountInfo> list){
        userObject=userObj;
        accountList = list;
        super.setSize(650,650);
        super.setTitle("Your Secret Notes");
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildPanel();
        super.setVisible(true);
    }

    private void buildPanel(){
        //Creating a Menu Bar with "Resume", "Exit", "Logout".
        JMenuBar menuBar = new JMenuBar();
        JMenu item = new JMenu("Settings");
        JMenuItem resume = new JMenuItem("Resume");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem logout = new JMenuItem("Logout");

        //putting border around JMenu items
        Border border = BorderFactory.createLineBorder(Color.BLACK);

        resume.setBorder(border);
        exit.setBorder(border);
        logout.setBorder(border);
        menuBar.setBorder(border);
        //making sure to nest each item so that JMENU items works properly
        item.add(resume);
        item.add(logout);
        item.add(exit);
        menuBar.add(item);
        super.add(menuBar, BorderLayout.NORTH);

        //PANEL 1:Creating the text Area for the user's secret message and a Label for the text box
        JPanel panel1 = new JPanel();
        BoxLayout layout1 = new BoxLayout(panel1, BoxLayout.Y_AXIS);
        panel1.setLayout(layout1);
        //creating a label
        JLabel intro = new JLabel("Write your personal messages below and save it so that ONLY you can view it once you login");
        //making the text area with scroll bar and so that it wraps.
        JTextArea textArea = new JTextArea(userObject.getText());
        textArea.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(textArea);
        //SETTING TITLE TO THIS SPECIFIC JPANEL == just for more neat appearance
        TitledBorder panelTitle= new TitledBorder(userObject.getUsername() + "'s Eyes Only");
        panelTitle.setTitleJustification(TitledBorder.CENTER);
        panelTitle.setTitlePosition(TitledBorder.TOP);
        panel1.setBorder(panelTitle);
        //adding components
        panel1.add(intro);
        panel1.add(scroll);
        super.add(panel1, BorderLayout.CENTER);

        //PANEL 2: Adding a save button so the user's text saves.
        JPanel panel2 = new JPanel();
        FlowLayout layout2 = new FlowLayout();
        panel2.setLayout(layout2);

        JButton save = new JButton("Save");
        Dimension dimension = new Dimension(120,65);
        save.setPreferredSize(dimension);
        panel2.add(save);

        //adding components to Main JFrame
        super.add(panel2, BorderLayout.SOUTH);


        //ActionListener to the Save Button
        save.addActionListener(event->{
            //we are saving what the user wrote using the userObject's "setText" method.
            userObject.setText(textArea.getText());
            try {
                FileOutputStream ostream = new FileOutputStream("Accounts.dat");
                ObjectOutputStream objectWriter = new ObjectOutputStream(ostream);

                objectWriter.writeObject(accountList);
                objectWriter.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });


        //ActionListener to the Resume Button: NONE NEEDED lol

        //ActionListener to the Exit Button
        exit.addActionListener(event-> {
            System.exit(0);
        });

        //ActionListener to the Logout Button
        logout.addActionListener(event->{
            this.dispose();
            MainLoginPage testLogin = new MainLoginPage();
        });

    }


}
