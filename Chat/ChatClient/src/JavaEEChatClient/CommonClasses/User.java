package JavaEEChatClient.CommonClasses;

/**
 * Created by user on 19.08.2015.
 */
public class User {
    private String name;
    private String chatRoom;

    public User(String name, String chatRoom) {
        this.name = name;
        this.chatRoom = chatRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(String chatRoom) {
        this.chatRoom = chatRoom;
    }
}
