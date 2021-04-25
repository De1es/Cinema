package by.itacademy.andrei_delesevich.cinema.model.user;

public class UserEntry {
    private String name;
    private String password;

    public UserEntry(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
