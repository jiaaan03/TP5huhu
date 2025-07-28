package burhanfess.services;

import java.util.List;

import burhanfess.exceptions.MenfessNotFoundException;
import burhanfess.menfess.Menfess;
import burhanfess.users.User;
import java.util.Comparator;

public interface AdminService {
    public List<User> getAllUsers(Comparator<User> comparator);

    public void addAdmin(String username, String password);

    public void resetPassword(String username, String newPassword);

    public List<Menfess> getAllHiddenMenfesses();

    public List<Menfess> getAllUnhiddenMenfesses();

    public void hideMenfess(int menfessId) throws MenfessNotFoundException;

    public void unhideMenfess(int menfessId) throws MenfessNotFoundException;

    public void logout();
}
