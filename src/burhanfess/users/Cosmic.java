package burhanfess.users;

public class Cosmic extends User{
    public Cosmic(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "Cosmic";
    }
    
    
}
