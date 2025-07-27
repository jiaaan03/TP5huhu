package burhanfess.menfess;

import java.time.format.DateTimeFormatter;

import burhanfess.users.User;

public class PromosiFess extends Menfess {
    public PromosiFess(User user, String content) {
        super(user, content);
    }

    @Override
    public String getType() {
        return "PromosiFess";
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "[Promosi] oleh " + user.getUsername() + "\n" +
                "ID: " + user.getId() + "\n" +
                content + "\n" +
                "Timestamp: " + timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n";
    }
    
}
