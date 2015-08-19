package JSON;

/**
 * Created by sergey on 12.08.2015.
 */
public class MessageJSON {
    private String name;
    private String body;
    private String date;
    private String chatRoom;

    public MessageJSON() {
    }

    public MessageJSON(String name, String body, String date) {
        this.name = name;
        this.body = body;
        this.date = date;
        this.chatRoom = "";
    }

    public MessageJSON(String name, String body, String date, String chatRoom) {
        this.name = name;
        this.body = body;
        this.date = date;
        this.chatRoom = chatRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(String chatRoom) {
        this.chatRoom = chatRoom;
    }
}
