package burhanfess.repositories;

import java.util.ArrayList;
import java.util.List;

import burhanfess.users.User;

public class UserRepositoryImpl implements UserRepository {
    private List<User> users;
    private UserRepositoryImpl instance;

    public UserRepositoryImpl() {
        users = new ArrayList<>();
        users.add(new User("admin", "admin")); // Example admin user
    }

    public UserRepository getInstance() {
        if(instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // or throw an exception if preferred
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
    }
}
