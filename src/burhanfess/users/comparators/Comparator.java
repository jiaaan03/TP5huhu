package burhanfess.users.comparators;

import burhanfess.users.User;

public interface Comparator<User> {
    public int compare (User user1, User user2);
}
