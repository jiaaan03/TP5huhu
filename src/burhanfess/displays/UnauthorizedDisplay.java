package burhanfess.displays;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import burhanfess.exceptions.InvalidLoginCredentialsException;
import burhanfess.exceptions.UserAlreadyExistsException;
import burhanfess.repositories.MenfessRepository;
import burhanfess.repositories.UserRepository;
import burhanfess.services.AdminService;
import burhanfess.services.AdminServiceImpl;
import burhanfess.services.CosmicService;
import burhanfess.services.CosmicServiceImpl;
import burhanfess.services.UnauthorizedService;
import burhanfess.users.Admin;
import burhanfess.users.Cosmic;
import burhanfess.users.User;
public class UnauthorizedDisplay implements Display{
    private UnauthorizedService unauthorizedService;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private UserRepository userRepository;
    private MenfessRepository menfessRepository;

    public UnauthorizedDisplay(UnauthorizedService unauthorizedService, UserRepository userRepository, MenfessRepository menfessRepository) {
        this.unauthorizedService = unauthorizedService;
        this.userRepository = userRepository;
        this.menfessRepository = menfessRepository;
    }

    @Override
    public void showMenu() {
        Scanner input = new Scanner(System.in);
        showHeader();
        showCurrentDate();
        showFooter();
        while (true) {
            System.out.println("""
            Silakan pilih salah satu opsi berikut:
            1. Registrasi
            2. Login
            3. Keluar
                    """);
            System.out.print("Masukkan pilihan: ");
            String pilihan = input.nextLine();

            try {
                if(pilihan.equals("1")) {
                    System.out.println("Silakan masukkan username dan password untuk registrasi.");
                    System.out.print("Masukkan username: ");
                    String username = input.nextLine();
                    System.out.print("Masukkan password: ");
                    String password = input.nextLine();
                    unauthorizedService.register(username, password);
                } else if(pilihan.equals("2")) {
                    System.out.println("Silakan masukkan username dan password untuk login.");
                    System.out.print("Masukkan username: ");
                    String username = input.nextLine();
                    System.out.print("Masukkan password: ");
                    String password = input.nextLine();
                    try {
                        User login = unauthorizedService.login(username, password);
                        if (login instanceof Cosmic) {
                            Cosmic cosmicUser = (Cosmic) login;
                            CosmicService cosmicService = new CosmicServiceImpl(cosmicUser, userRepository, menfessRepository);
                            CosmicDisplay cosmicDisplay = new CosmicDisplay(cosmicUser, cosmicService, userRepository, menfessRepository);
                            cosmicDisplay.showMenu();
                        } else if (login instanceof Admin) {
                            Admin adminUser = (Admin) login;
                            AdminService adminService = new AdminServiceImpl(adminUser, userRepository, menfessRepository);
                            AdminDisplay adminDisplay = new AdminDisplay(adminUser, adminService);
                            adminDisplay.showMenu();
                        } else {
                            System.out.println("Role user tidak dikenali.");
                        }
                    } catch (InvalidLoginCredentialsException e) {
                        System.out.println(e.getMessage());
                    }
                } else if(pilihan.equals("3")) {
                    unauthorizedService.exit();
                } else {
                System.out.println("Input tidak valid. Silakan coba lagi.");
                }
        
            } catch (UserAlreadyExistsException e)  {
                System.out.println(e.getMessage());
            }
            
        }
    }

    public void showHeader() {
        System.out.println("Selamat datang di BurhanFess!");
    }

    public void showFooter() {
        System.out.println("=============================");
    }

    public void showCurrentDate() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        System.out.println("Tanggal dan Waktu: " + now.format(formatter));
    }
    
}
