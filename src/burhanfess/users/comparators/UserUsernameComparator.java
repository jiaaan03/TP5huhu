package burhanfess.users.comparators;

import java.util.Comparator;
import burhanfess.users.User;

public class UserUsernameComparator implements Comparator<User> {
    @Override
    public int compare(User u1, User u2) {
        return u1.getUsername().compareTo(u2.getUsername());
    }
    
}
