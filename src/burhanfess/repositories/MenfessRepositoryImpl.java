package burhanfess.repositories;

import java.util.ArrayList;
import java.util.List;

import burhanfess.menfess.ConfessFess;
import burhanfess.menfess.Menfess;
import burhanfess.users.User;

public class MenfessRepositoryImpl implements MenfessRepository {
    private List<Menfess> menfesses;
    private MenfessRepository menfessRepository;

    private MenfessRepositoryImpl() {
        this.menfesses = new ArrayList<>();
    }

    public MenfessRepository getInstance() {
        if(menfessRepository == null) {
            menfessRepository = new MenfessRepositoryImpl();
        }
        return menfessRepository;
    }

    @Override
    public List<Menfess> getAllmenfessByUser(User user) {
        List<Menfess> hasil = new ArrayList<>();
        for (Menfess menfess : menfesses) {
            if (menfess.getUser().equals(user)) {
                hasil.add(menfess);
            }
        }
        return hasil;
       
    }

    @Override
    public void unhideMenfess(Menfess menfess) {
        menfess.setHidden(true);
    }

    @Override
    public void hideMenfess(Menfess menfess) {
        menfess.setHidden(false);
    }

    @Override
    public void addMenfess(Menfess menfess) {
        this.menfesses.add(menfess);
    }

    @Override
    public List<Menfess> getAllHiddenMenfesses() {
        List<Menfess> hasil = new ArrayList<>();
        for(Menfess menfess : menfesses) {
            if(menfess.isHidden()) {
                hasil.add(menfess);
            }
        }
        return hasil;

    }

    @Override
    public List<Menfess> getAllUnhiddenMenfesses() {
        List<Menfess> hasil = new ArrayList<>();
        for(Menfess menfess : menfesses) {
            if(!menfess.isHidden()) {
                hasil.add(menfess);
            }
        }
        return hasil;

    }

    @Override
    public List<Menfess> getAllMenfessesForUser(User user) {
        List<Menfess> hasil = new ArrayList<>();
        for (Menfess m : menfesses) {
            if(m instanceof ConfessFess) {
                ConfessFess confessFess = (ConfessFess) m;
                if (confessFess.getReceived().equals(user)) {
                    hasil.add(m);
                }
            }
        }
        return hasil;
    }

    public List<Menfess> getAllMenfesses() {
        return new ArrayList<>(menfesses);
    }
}
