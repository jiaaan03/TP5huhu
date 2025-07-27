package burhanfess.displays;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public interface Display {
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public Scanner scanner = new Scanner(System.in);

    public void showMenu();

    public void showHeader();

    public void showFooter();

    public void showCurrentDate();

    
}
