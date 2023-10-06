import java.io.Serializable;

public class UserAccountInfo implements Serializable {
    private String username;
    private String password;
    private String userText;
    public UserAccountInfo(String user, String pass){
        username=user;
        password=pass;

    }

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }

    public void setText(String text){
        userText = text;
    }
    public String getText(){
        return userText;
    }

    public String toString(){
        return String.format("USERNAME:\n%s\nPASSWORD:\n%s\nUSER_TEXT:\n%s\n", username, password, userText);
    }

}
