package burhanfess;

import burhanfess.displays.UnauthorizedDisplay;
import burhanfess.menfess.Menfess;
import burhanfess.repositories.MenfessRepositoryImpl;
import burhanfess.repositories.UserRepositoryImpl;
import burhanfess.services.UnauthorizedService;
import burhanfess.services.UnauthorizedServicelmpl;

public class BurhanFess {
    public static void main(String[] args) {
        // Gunakan interface sebagai tipe referensi
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        MenfessRepositoryImpl menfessRepository = new MenfessRepositoryImpl();
        UnauthorizedService unauthorizedService = new UnauthorizedServicelmpl(userRepository);
        UnauthorizedDisplay display = new UnauthorizedDisplay(unauthorizedService, userRepository, menfessRepository);
        
        display.showMenu();
    }
}