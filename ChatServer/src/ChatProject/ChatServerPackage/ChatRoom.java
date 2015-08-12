package ChatProject.ChatServerPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 12.08.2015.
 */
public class ChatRoom {
    private String name;
    private Map<String, User> members = new HashMap<>();
    private List<ChatMessage> messageList = new ArrayList<>();

    public ChatRoom(String name) {
        this.name = name;
    }

    public void addMessage(ChatMessage message)
    {
        messageList.add(message);
    }

    public void addMember(User user)
    {
        members.put(user.getName(), user);
    }

    public User getMember(String name)
    {
        return members.get(name);
    }

    public List<ChatMessage> getMessageList() {
        return messageList;
    }
}
