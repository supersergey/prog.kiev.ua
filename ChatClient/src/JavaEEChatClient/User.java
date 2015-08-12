package JavaEEChatClient;

import java.util.HashMap;

/**
 * Created by user on 10.08.2015.
 */
public class User {
    private String name;
    private String currentRoom;
    private HashMap<User, String> privateInbox;
    private HashMap<User, String> privateSent;

    public User(String name, String currentRoom) {
        this.name = name;
        this.currentRoom = currentRoom;
    }

    public String getName() {
        return name;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }
}
