package burhanfess;

import burhanfess.displays.UnauthorizedDisplay;
import burhanfess.repositories.UserRepository;
import burhanfess.repositories.UserRepositoryImpl;
import burhanfess.services.UnauthorizedService;
import burhanfess.services.UnauthorizedServicelmpl;

public class BurhanFess {
    public static void main(String[] args) {
        // Gunakan interface sebagai tipe referensi
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        UnauthorizedService unauthorizedService = new UnauthorizedServicelmpl(userRepository);
        UnauthorizedDisplay display = new UnauthorizedDisplay(unauthorizedService);
        
        display.showMenu();
    }
}