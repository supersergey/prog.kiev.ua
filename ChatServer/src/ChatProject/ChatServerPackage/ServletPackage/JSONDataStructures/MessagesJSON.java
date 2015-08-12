package ChatProject.ChatServerPackage.ServletPackage.JSONDataStructures;

import java.util.ArrayList;

/**
 * Created by sergey on 12.08.2015.
 */
public class MessagesJSON {
    private ArrayList<MessageJSON> messages = new ArrayList<>();

    public MessagesJSON() {
    }

    public ArrayList<MessageJSON> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageJSON> messages) {
        this.messages = messages;
    }
}
