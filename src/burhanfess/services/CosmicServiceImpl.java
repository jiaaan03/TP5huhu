package burhanfess.services;

import java.util.List;

import burhanfess.exceptions.InvallidPasswordException;
import burhanfess.exceptions.UserNotFoundException;
import burhanfess.menfess.ConfessFess;
import burhanfess.menfess.CurhatFess;
import burhanfess.menfess.Menfess;
import burhanfess.menfess.PromosiFess;
import burhanfess.repositories.MenfessRepository;
import burhanfess.repositories.UserRepository;
import burhanfess.users.Cosmic;
import burhanfess.users.User;

public class CosmicServiceImpl implements CosmicService {
    public List<Menfess> getAllUnhiddenMenfesses() {
        return null;
    }
    private Cosmic cosmic;
    private UserRepository userRepository;
    private MenfessRepository menfessRepository;

    public CosmicServiceImpl(Cosmic cosmic, UserRepository userRepository, MenfessRepository menfessRepository) {
        this.cosmic = cosmic;
        this.userRepository = userRepository;
        this.menfessRepository = menfessRepository;
    }

    public void sendPromosiFess(String content) {
        PromosiFess promosi = new PromosiFess(cosmic, content);
        menfessRepository.addMenfess(promosi);
    }

    public List<Menfess> getAllSentMenfesses() {
        return menfessRepository.getAllmenfessByUser(cosmic);
    }

    public List<Menfess> getAllReceivedMenfesses() {
        return menfessRepository.getAllMenfessesForUser(cosmic);
    }

    public void changePassword(String newPassword)  {
        cosmic.setPassword(newPassword);
    }

    public void logout() {
        this.cosmic = null;
        System.out.println("Logout berhasil.");
    }

    @Override
    public void sendCurhatFess(User user, String content) {
        CurhatFess curhat = new CurhatFess(user, content);
        menfessRepository.addMenfess(curhat);
    }

    @Override
    public void sendConfessFess(User user, String content, User receiveUsername) {
        ConfessFess confess = new ConfessFess(user, content, receiveUsername);
        menfessRepository.addMenfess(confess);
    }
}
