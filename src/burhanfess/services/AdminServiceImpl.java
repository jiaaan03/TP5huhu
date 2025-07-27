package burhanfess.services;

import java.util.List;

import burhanfess.exceptions.InvallidPasswordException;
import burhanfess.exceptions.MenfessNotFoundException;
import burhanfess.exceptions.UserAlreadyExistsException;
import burhanfess.exceptions.UserNotFoundException;
import burhanfess.menfess.Menfess;
import burhanfess.repositories.MenfessRepository;
import burhanfess.repositories.UserRepository;
import burhanfess.users.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

public class AdminServiceImpl implements AdminService{
    private UserRepository userRepository;
    private MenfessRepository menfessRepository;

    public AdminServiceImpl(UserRepository userRepository, MenfessRepository menfessRepository) {
        this.userRepository = userRepository;
        this.menfessRepository = menfessRepository;
    }

    @Override
    public void addAdmin(String username, String password) {
        User newAdmin = new User(username, password);
        userRepository.addUser(newAdmin);
        System.out.println("Admin baru berhasil ditambahkan: " + username);
    }

    @Override
    public void resetPassword(String username, String newPassword) {
        User user = userRepository.getUserByUsername(username);
        user.setPassword(newPassword);
        userRepository.changePassword(user, newPassword);
    }

    @Override
    public List<Menfess> getAllHiddenMenfesses() {
        List<Menfess> allmenfess = menfessRepository.getAllMenfesses();
        List<Menfess> hiddenmenfess = new ArrayList<>();
        for (Menfess menfess : allmenfess) {
            if (menfess.isHidden()) {
                hiddenmenfess.add(menfess);
            }
        }
        return hiddenmenfess;
        
    }

    @Override
    public List<Menfess> getAllUnhiddenMenfesses() {
        List<Menfess> allmenfess = menfessRepository.getAllMenfesses();
        List<Menfess> unhiddenmenfess = new ArrayList<>();
        for (Menfess menfess : allmenfess) {
            if (!menfess.isHidden()) {
                unhiddenmenfess.add(menfess);
            }
        }
        return unhiddenmenfess;
    }

    public void hideMenfess(int menfessId) throws MenfessNotFoundException  {
        List<Menfess> allMenfessList = menfessRepository.getAllMenfesses(); // Use the correct method name
        if (allMenfessList == null) {
            throw new MenfessNotFoundException("Menfess dengan ID '" + menfessId + "' tidak ditemukan.");
        }
        for (Menfess menfess : allMenfessList) {
            if (menfess.getId() == menfessId) {
                menfess.setHidden(true);
                return;
            }
        }
    }

    @Override
    public void unhideMenfess(int menfessId) throws MenfessNotFoundException {
        List<Menfess> allMenfessList = menfessRepository.getAllMenfesses(); // Use the correct method name
        if (allMenfessList == null) {
            throw new MenfessNotFoundException("Menfess dengan ID '" + menfessId + "' tidak ditemukan.");
        }
        for (Menfess menfess : allMenfessList) {
            if (menfess.getId() == menfessId) {
                menfess.setHidden(false);
                return;
            }
        }
    }

    public void logout() {
        System.out.println("Kamu telah berhasil logout.");
        // Additional logic for logout if needed
    }

    @Override
    public List<User> getAllUsers(Comparator<User> comparator) {
        List<User> users = userRepository.getAllUsers();
        users.sort(comparator);
        return users;
    }

    @Override
    public List<User> getAllUsers(burhanfess.users.comparators.Comparator<User> comparator) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllUsers'");
    }
}
