package burhanfess.menfess;

import java.time.format.DateTimeFormatter;

import burhanfess.users.Cosmic;
import burhanfess.users.User;

public class ConfessFess extends Menfess {
    private User receiver;
    
    public ConfessFess(User user, String content, User receiveUsername) {
        super(user, content);
        this.receiver = receiveUsername;
    }

    public User getReceived() {
        return receiver;
    }

    @Override
    public String getType() {
        return "ConfessFess";
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
               "[Confession] oleh anonim" + "\n" +
                "ID: " + user.getId() + "\n" +
                content + "\n" +
                "Timestamp: " + timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n";
    }
}
