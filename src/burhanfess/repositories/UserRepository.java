package burhanfess.repositories;

import java.util.List;

import burhanfess.users.User;

public interface UserRepository {
    public List<User> getAllUsers();

    public User getUserByUsername(String username);

    public void addUser(User user);

    public void changePassword(User user, String newPassword);

}
