package burhanfess.displays;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import burhanfess.exceptions.InvalidPasswordException;
import burhanfess.exceptions.InvallidPasswordException;
import burhanfess.exceptions.UserNotFoundException;
import burhanfess.menfess.ConfessFess;
import burhanfess.menfess.Menfess;
import burhanfess.services.CosmicService;
import burhanfess.users.Cosmic;
import burhanfess.users.User;
import burhanfess.repositories.UserRepository;

public class CosmicDisplay implements Display {
    private Cosmic cosmic;
    private CosmicService cosmicService;
    private DateTimeFormatter formatter;
    private UserRepository userRepository;

    public CosmicDisplay(Cosmic cosmic, CosmicService cosmicService, UserRepository userRepository) {
        this.cosmic = cosmic;
        this.cosmicService = cosmicService;
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.userRepository = userRepository;
    }

    @Override
    public void showMenu() {
        Scanner input = new Scanner(System.in);
        showHeader();
        while(true) {
            System.out.println("""
            Silakan pilih salah satu opsi berikut:
            1. Mengirim satu menfess
            2. Melihat menfess publik
            3. Melihat menfess yang kamu kirim
            4. Melihat menfess yang kamu terima
            5. Ubah password
            6. Logout
                    """);
            System.out.print("Masukkan pilihan: ");
            int pilihan = input.nextInt();
            input.nextLine(); // Clear the newline character
            try {
                if(pilihan == 1) {
                    sendMessage();
                } else if (pilihan == 2) {
                    viewUnhiddenMenfesses();
                } else if (pilihan == 3) {
                    viewSentMenfesses();
                } else if (pilihan == 4) {
                    viewReceivedMenfesses();
                } else if (pilihan == 5) {
                    changePassword();
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

    public void showHeader() {
        System.out.println("Selamat datang di BurhanFess!");
        System.out.println("Waktu: " + formatter.format(java.time.LocalDateTime.now()));
        System.out.println("=====================================");
    }

    private void sendMessage() {
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan isi menfess yang ingin kamu kirim: ");
        String isi = input.nextLine();
        System.out.println("""
        Silakan pilih tipe menfess yang ingin dikirim.
        1. Curhat
        2. Promosi
        3. Confess
                """);
        System.out.println("Masukkan tipe menfess: ");
        int pilihanTipe = input.nextInt();
        input.nextLine(); // Clear the newline character
        String receiveUsername = "";
        try {
            if(pilihanTipe == 1) {
                cosmicService.sendCurhatFess(cosmic, isi);
                System.out.println("CurhatFess berhasil dikirim!");
            } else if (pilihanTipe == 2) {
                cosmicService.sendPromosiFess(isi);
                System.out.println("PromosiFess berhasil dikirim!");
            } else if (pilihanTipe == 3) {
                System.out.print("Masukkan username yang ingin kamu kirimkan menfess: ");
                receiveUsername = input.nextLine();
                if (userRepository.getUserByUsername(receiveUsername) == null) {
                    throw new UserNotFoundException("User dengan username '" + receiveUsername + "' tidak ditemukan.");
                }
                User receiver = userRepository.getUserByUsername(receiveUsername);
                cosmicService.sendConfessFess(cosmic, isi, receiver);
                System.out.println("ConfessFess berhasil dikirim ke " + receiveUsername + "!");
            } else {
                System.out.println("Input tidak valid. Silakan coba lagi.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid. Silakan masukkan angka yang sesuai.");
        } catch (UserNotFoundException e) {
            System.out.println("Gagal mengirim menfess: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat mengirim menfess: " + e.getMessage());
        }

    }

    private void viewUnhiddenMenfesses() {
        System.out.println("Daftar menfess bersifat publik:");
        List<Menfess> unhiddenMenfesses = cosmicService.getAllUnhiddenMenfesses();
        if (unhiddenMenfesses.isEmpty()) {
            System.out.println("Tidak ada menfess publik saat ini.");
        } 

        for (Menfess menfess : unhiddenMenfesses) {
            System.out.println("[" + menfess.getType() + "] ");
            if (menfess instanceof ConfessFess) {
                System.out.println("oleh anonim");
            } else {
                System.out.println("oleh " + menfess.getUser().getUsername());
            }

            System.out.println("ID: " + menfess.getId());
            System.out.println(menfess.getContent());
            String waktuKirim = menfess.getTimestamp().format(formatter);
            System.out.println("Dikirim pada: " + waktuKirim);
            System.out.println();
        }
    }

    private void viewSentMenfesses() {
        System.out.println("Daftar menfess yang kamu kirim: ");
        List<Menfess> sentMenfesses = cosmicService.getAllSentMenfesses();
        if (sentMenfesses.isEmpty()) {
            System.out.println("Kamu belum mengirimkan menfess apapun.");
            return;
        }

        for(Menfess menfess : sentMenfesses) {
            System.out.println("[" + menfess.getType() + "] ");
            if (menfess instanceof ConfessFess) {
                ConfessFess confessFess = (ConfessFess) menfess;
                System.out.println(" untuk " + confessFess.getReceived().getUsername());
            }

            System.out.println("ID: " + menfess.getId());
            System.out.println(menfess.getContent());
            String waktuKirim = menfess.getTimestamp().format(formatter);
            System.out.println("Dikirim pada: " + waktuKirim);
        }
    }

    private void viewReceivedMenfesses() {
        System.out.println("Daftar menfess yang kamu terima:");
        List<Menfess> receivedMenfesses = cosmicService.getAllReceivedMenfesses();
        if (receivedMenfesses.isEmpty()) {
            System.out.println("Kamu belum menerima menfess apapun.");
            return;
        }
        for (Menfess menfess : receivedMenfesses) {
            System.out.println("[" + menfess.getType() + "]");
            if (menfess instanceof ConfessFess) {
                System.out.println(" oleh anonim");
            } else {
                System.out.println(" oleh " + menfess.getUser().getUsername());
            }

            System.out.println("ID: " + menfess.getId());
            System.out.println(menfess.getContent());
            System.out.println("Dikirim pada: " + menfess.getTimestamp().format(formatter));
        }
    }

    private void changePassword() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Masukkan password baru: ");
            String newPassword = input.nextLine();
            if (newPassword.equalsIgnoreCase(cosmic.getPassword())) {
                throw new InvalidPasswordException("Password baru tidak boleh sama dengan password lama.");
            }
            cosmicService.changePassword(newPassword);
            System.out.println("Password berhasil diubah.");
        } catch (InvalidPasswordException e) {
            System.out.println("Gagal mengubah password: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat mengubah password: " + e.getMessage());
        }
    }

    private void logout() {
        cosmicService.logout();
        System.out.println("Anda telah berhasil logout.");
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
