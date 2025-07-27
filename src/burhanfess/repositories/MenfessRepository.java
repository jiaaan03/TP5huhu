package burhanfess.repositories;

import java.util.List;

import burhanfess.menfess.Menfess;
import burhanfess.users.User;

public interface MenfessRepository {
    public List<Menfess> getAllmenfessByUser(User user);

    public List<Menfess> getAllHiddenMenfesses();

    public List<Menfess> getAllUnhiddenMenfesses();

    public void addMenfess(Menfess menfess);

    public void hideMenfess(Menfess menfess);

    public void unhideMenfess(Menfess menfess);

    public List<Menfess> getAllMenfessesForUser(User user);

    public List<Menfess> getAllMenfesses();
}
