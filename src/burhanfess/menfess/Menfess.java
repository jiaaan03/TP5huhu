package burhanfess.menfess;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import burhanfess.users.User;

public class Menfess {
    private int idGenerator;
    private DateTimeFormatter formatter;
    private int id;
    protected LocalDateTime timestamp;
    protected String content;
    private boolean isHidden;
    protected User user;

    public Menfess(User user, String content) {
        this.user = user;
        this.content = content;
        this.id = ++idGenerator;
        this.timestamp = LocalDateTime.now();
        this.isHidden = false;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public User getUser() {
        return user;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public int getId() {
        return id;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public String getType() {
        if (this instanceof ConfessFess) return "Confession";
        if (this instanceof PromosiFess) return "Promosi";
        return "Curhat";
    }

    public String toString() { // default copilot
        return "ID: " + id + "\n" +
               "User: " + user.getUsername() + "\n" +
               "Content: " + content + "\n" +
               "Timestamp: " + timestamp.format(formatter) + "\n" +
               "Hidden: " + isHidden;
    }
    

    


}
