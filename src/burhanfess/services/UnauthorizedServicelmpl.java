package burhanfess.services;

import burhanfess.exceptions.InvalidLoginCredentialsException;
import burhanfess.exceptions.UserAlreadyExistsException;
import burhanfess.repositories.UserRepository;
import burhanfess.users.Admin;
import burhanfess.users.Cosmic;
import burhanfess.users.User;

public class UnauthorizedServicelmpl implements UnauthorizedService {
    private UserRepository userRepository;

    public UnauthorizedServicelmpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(String username, String password) throws UserAlreadyExistsException {
        if (userRepository.getUserByUsername(username) != null) {
           throw new UserAlreadyExistsException("User dengan username '" + username +"' sudah ada.");
        }

        User userBaru = new Cosmic(username, password);
        userRepository.addUser(userBaru);
        System.out.println("Registrasi berhasil! Silakan login");
    }

    @Override
    public void exit() {
        System.out.println("Terima kasih telah menggunakan BurhanFess. Sampai jumpa!");
        System.out.println("========================================================");
        System.out.println("BurhanFess - 2025");
        System.out.println("Created by Burhan");
        System.exit(0);
    }

    @Override
    public User login(String username, String password) throws InvalidLoginCredentialsException{
        User user = userRepository.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new InvalidLoginCredentialsException("Username atau password salah. Silakan coba lagi.");
        }

        if (user.getRole().equals("Admin")) {
            return new Admin(user.getUsername(), user.getPassword());
        } else if (user.getRole().equals("Cosmic")) {
            return new Cosmic(user.getUsername(), user.getPassword());
        }
        
        System.out.println("Login berhasil! Selamat datang, " + user.getUsername() + ".");
        System.out.println("=====================================");
        System.out.println("BurhanFess - 2025");
        System.out.println("Created by Burhan");
        return user;
    }
}
