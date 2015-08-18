package JSON;

/**
 * Created by user on 11.08.2015.
*/
public class LoginJSON {
    private String login;
    private String password;

    public LoginJSON(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public LoginJSON() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
