package JavaEEChatClient;

import java.util.HashMap;

/**
 * Created by user on 10.08.2015.
 */
public class User {
    private String name;
    private HashMap<User, String> privateInbox;
    private HashMap<User, String> privateSent;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
