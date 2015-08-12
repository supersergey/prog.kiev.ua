package JavaEEChatClient.CommonClasses;

import java.util.Date;

/**
 * Created by user on 12.08.2015.
 */
public class ChatMessage {
    private User from;
    private Date timestamp;
    private String body;

    public ChatMessage(User from, String body) {
        this.from = from;
        this.body = body;
        this.timestamp = new Date();
    }

    public ChatMessage() {
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
