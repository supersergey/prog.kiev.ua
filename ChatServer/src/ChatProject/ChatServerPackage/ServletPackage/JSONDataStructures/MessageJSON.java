package ChatProject.ChatServerPackage.ServletPackage.JSONDataStructures;

/**
 * Created by sergey on 12.08.2015.
 */
public class MessageJSON {
    private String name;
    private String body;
    private String date;

    public MessageJSON() {
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
}
