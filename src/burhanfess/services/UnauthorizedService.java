package burhanfess.services;

import burhanfess.exceptions.InvalidLoginCredentialsException;
import burhanfess.exceptions.UserAlreadyExistsException;
import burhanfess.users.User;

public interface UnauthorizedService {
    public void register(String username, String password) throws UserAlreadyExistsException;

    public User login(String username, String password) throws InvalidLoginCredentialsException;

    public void exit();
}
