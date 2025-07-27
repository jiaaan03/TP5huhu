package burhanfess.users.comparators;

import java.util.Comparator;
import burhanfess.users.User;

public class UserIdComparator implements Comparator<User> {
    @Override
    public int compare(User u1, User u2) {
        return Integer.compare(u1.getId(), u2.getId());
    }
    
}
