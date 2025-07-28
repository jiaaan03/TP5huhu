package burhanfess.displays;

import java.util.List;
import java.util.Scanner;

import burhanfess.exceptions.MenfessNotFoundException;
import burhanfess.exceptions.UserAlreadyExistsException;
import burhanfess.exceptions.UserNotFoundException;
import burhanfess.menfess.ConfessFess;
import burhanfess.menfess.Menfess;
import burhanfess.services.AdminService;
import burhanfess.users.Admin;
import burhanfess.users.User;
import java.util.Comparator;
import burhanfess.users.comparators.UserIdComparator;
import burhanfess.users.comparators.UserUsernameComparator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class AdminDisplay implements Display {
    private Admin admin;
    private AdminService adminService;

    public AdminDisplay(Admin admin, AdminService adminService) {
        this.admin = admin;
        this.adminService = adminService;
    }
    
    @Override
    public void showMenu() {
        Scanner input = new Scanner(System.in);
        showHeader();
        while (true) {
            System.out.println("""
            Silakan pilih salah satu opsi berikut: 
            1. Lihat daftar pengguna
            2. Tambah admin
            3. Reset password pengguna
            4. Sembunyikan menfess
            5. Tampilkan menfess
            6. Logout 
                    """);
            System.out.print("Masukkan pilihan: ");
            int pilihan = input.nextInt();
            input.nextLine(); // Clear the newline character
            try {
                if(pilihan == 1) {
                    showAllUsers();
                } else if (pilihan == 2) {
                    addAdmin();
                } else if (pilihan == 3) {
                    resetPassword();
                } else if (pilihan == 4) {
                    hideMenfess();
                } else if (pilihan == 5) {
                    unhideMenfess();
                } else if (pilihan == 6) {
                    logout();
                    return;
                } else {
                    System.out.println("Input tidak valid. Silakan coba lagi.");
                }
            } catch (NumberFormatException e ) {
                System.out.println("Input tidak valid. Silakan masukkan angka yang sesuai.");
            } 
        }
    }

    @Override
    public void showHeader() {
        System.out.println("Halo Admin " + admin.getUsername() + "!");
        System.out.println("Waktu: " + formatter.format(java.time.LocalDateTime.now()));
        System.out.println("=====================================");
    }

    public void showAllUsers() {
        Scanner input = new Scanner(System.in);
        System.out.println("""
        Silakan pilih opsi pengurutan:
        1. Berdasarkan ID
        2. Berdasarkan username
                """);
        System.out.println("Masukkan pilihan: ");
        int pilihan = input.nextInt();
        input.nextLine(); // Clear the newline character
        try {
            Comparator<User> comparator;
            String sortBy;
            if (pilihan == 1) {
                comparator = new UserIdComparator();
                sortBy = "ID";
            } else if (pilihan == 2) {
                comparator = new UserUsernameComparator();
                sortBy = "Username";
            } else {
                System.out.println("Pilihan tidak valid. Harap masukkan 1 atau 2.");
                return;
            }
            List<User> users = adminService.getAllUsers(comparator);
            System.out.println("\nDaftar pengguna diurutkan berdasarkan " + sortBy + ":");
            for (User user : users) {
                System.out.println("ID: " + user.getId());
                System.out.println("Username: " + user.getUsername());
                System.out.println("Role: " + user.getRole());
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat mengambil daftar pengguna: " + e.getMessage());
        }
    }

    public void addAdmin() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("Silakan masukkan username dan password untuk admin baru: ");
            System.out.print("Username:");
            String username = input.nextLine();
            System.out.print("Password: ");
            String password = input.nextLine();
            Comparator<User> comparator = new UserUsernameComparator();
            for (User user : adminService.getAllUsers(comparator)) {
                if (user.getUsername().equals(username)) {
                    throw new UserAlreadyExistsException("User dengan username '" + username + "' sudah ada.");
                }
            }
            
            adminService.addAdmin(username, password);
            System.out.println("Admin " + username + " baru berhasil ditambahkan.");
        } catch (UserAlreadyExistsException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat menambahkan admin: " + e.getMessage());
        }
    }

    public void resetPassword() {
        Scanner input = new Scanner(System.in);
        String username = "";
        try {
            System.out.println("Masukkan username pengguna yang passwordnya ingin direset: ");
            username = input.nextLine();
            System.out.print("Masukkan password baru: ");
            String newPassword = input.nextLine();
            Comparator<User> comparator = new UserUsernameComparator();
            List<User> users = adminService.getAllUsers(comparator);
            boolean userFound = false;
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    userFound = true;
                    break;
                }
            }
            if(!userFound) {
                throw new UserNotFoundException("User dengan username '" + username + "' tidak ditemukan.");
            }
            adminService.resetPassword(username, newPassword);
            System.out.println("Password berhasil direset.");
        } catch (UserNotFoundException e) {
            System.out.println("User dengan username '" + username + "' tidak ditemukan.");
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat mereset password: " + e.getMessage());
        }
    }

    public void hideMenfess() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("\nDaftar menfess yang ditampilkan: ");
            List<Menfess> unhiddenMenfesses = adminService.getAllUnhiddenMenfesses();
            if (unhiddenMenfesses.isEmpty()) {
                System.out.println("Tidak ada menfess yang ditampilkan saat ini");
                return;
            }

            for(Menfess menfess : unhiddenMenfesses) {
                System.out.println("[" + menfess.getType() + "]" + "oleh ");
                if (menfess instanceof ConfessFess) {
                    System.out.println("anonim");
                } else {
                    System.out.println(menfess.getUser().getUsername());
                }
                System.out.println("ID: " + menfess.getId());
                System.out.println(menfess.getContent());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String waktu = menfess.getTimestamp().format(formatter);
                System.out.println("Dikirim pada: " + waktu);
            }
            System.out.println("Masukkan ID menfess yang ingin disembunyikan: ");
            String inputID = input.nextLine();
            try {
                int menfessID = Integer.parseInt(inputID);
                adminService.hideMenfess(menfessID);
                System.out.println("Menfess berhasil menyembunyikan");
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Silakan masukkan angka yang sesuai");
            } catch (MenfessNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
        
    }

    public void unhideMenfess() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("\nDaftar menfess yang disembunyikan");
            List<Menfess> hiddMenfesses = adminService.getAllHiddenMenfesses();
            if (hiddMenfesses.isEmpty()) {
                System.out.println("Tidak ada menfess yang disembunyikan saat ini");
                return;
            }

            for (Menfess menfess : hiddMenfesses) {
                System.out.println("[" + menfess.getType() + "] oleh " );
                if (menfess instanceof ConfessFess) {
                    System.out.println("anonim");
                } else {
                    System.out.println(menfess.getUser().getUsername());
                }
                System.out.println("ID: " + menfess.getId());
                System.out.println(menfess.getContent());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String waktu = menfess.getTimestamp().format(formatter);
                System.out.println("Dikirim pada: " + waktu);
            }
            System.out.println("Masukkan ID menfess yang ingin disembunyikan: ");
            String inputID = input.nextLine();
            try {
                int menfessID = Integer.parseInt(inputID);
                adminService.unhideMenfess(menfessID);
                System.out.println("Menfess berhasil ditampilkan");
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Silakan masukkan angka yang sesuai");
            } catch (MenfessNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    public void logout() {
        System.out.println("Kamu telah berhasil logout.");
        showFooter();
        System.out.println("""
        BuhranFess - 2025
        Created by Burhan
                """);
        
    }

    @Override
    public void showFooter() {
        System.out.println("=============================");
    }

    @Override
    public void showCurrentDate() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        System.out.println("Tanggal dan Waktu: " + now.format(formatter));
    }

}
