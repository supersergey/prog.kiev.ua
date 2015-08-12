package ChatProject.ChatServerPackage;

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

    public String getName() {
        return name;
    }
}
