package burhanfess.services;

import java.util.List;

import burhanfess.menfess.Menfess;
import burhanfess.users.User;

public interface CosmicService {
    public void sendCurhatFess(User user, String content);

    public void sendPromosiFess(String Content);

    public void sendConfessFess(User user, String Content, User receiveUsername);

    public List<Menfess> getAllUnhiddenMenfesses();

    public List<Menfess> getAllSentMenfesses();
    
    public List<Menfess> getAllReceivedMenfesses();

    public void changePassword(String newPassword);

    public void logout();
}
