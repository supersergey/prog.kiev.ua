package ChatProject.ChatServerPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 12.08.2015.
 */
public class User {
    private String name;
    private List<ChatMessage> privateMessages = new ArrayList<>();
    private ChatRoom currentRoom;
    private boolean visible;
    private int color;
    private static int colorCounter = 0;

    public User(String name, ChatRoom currentRoom) {
        this.name = name;
        this.currentRoom = currentRoom;
        this.visible = true;
        if (!name.equals("Server"))
            this.color = colorCounter++;
        }


    public String getName() {
        return name;
    }

    public ChatRoom getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(ChatRoom currentRoom) {
        this.currentRoom = currentRoom;
    }

    public int getColor() {
        return color;
    }
}
