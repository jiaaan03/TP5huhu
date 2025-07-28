package burhanfess.users;

public class User {
    private static int idGenerator;
    private int id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.id = ++idGenerator;
        this.username = username;
        this.password = password;
    }

    public String getRole() {
        return "User";
    }
    
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() { // default
        return "ID: " + id + "\n" +
               "Username: " + username + "\n" +
               "Password: " + password + "\n" +
               "Role: " + getRole() + "\n"; 
    }

}
