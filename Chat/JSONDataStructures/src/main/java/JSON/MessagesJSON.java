package JSON;

import java.util.ArrayList;

/**
 * Created by sergey on 12.08.2015.
 */
public class MessagesJSON {
    private String chatRoomName;
    private ArrayList<MessageJSON> messages = new ArrayList<>();

    public MessagesJSON() {
    }

    public MessagesJSON(String chatRoomName, ArrayList<MessageJSON> messages) {
        this.chatRoomName = chatRoomName;
        this.messages = messages;
    }

    public ArrayList<MessageJSON> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageJSON> messages) {
        this.messages = messages;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomName(String charRoomName) {
        this.chatRoomName = charRoomName;
    }
}
