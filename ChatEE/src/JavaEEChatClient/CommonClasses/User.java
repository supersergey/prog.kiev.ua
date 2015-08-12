package JavaEEChatClient.CommonClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 12.08.2015.
 */
public class User {
    private String name;
    private List<ChatMessage> privateMessages = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChatMessage> getPrivateMessages() {
        return privateMessages;
    }

    public void setPrivateMessages(List<ChatMessage> privateMessages) {
        this.privateMessages = privateMessages;
    }
}
