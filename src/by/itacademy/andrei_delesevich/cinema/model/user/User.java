package by.itacademy.andrei_delesevich.cinema.model.user;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.*;

public class User {
    private int id;
    private String login;
    private String password;
    private UserAccessLevel access;
    private static final Logger logger = Logger.getLogger(User.class.getName());


    public User(String login, String password, UserAccessLevel access) {
        this.login = login;
        this.password = password;
        this.access = access;
    }

    public User(int id, String login, String password, UserAccessLevel access) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.access = access;

    }

    public User(int id, String login, UserAccessLevel access) {
        this.id = id;
        this.login = login;
        this.access = access;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserAccessLevel getAccess() {
        return access;
    }

    public void setAccess(UserAccessLevel access) {
        this.access = access;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", access=" + access +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(login, user.login) && Objects.equals(password, user.password) && access == user.access;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, access);
    }


    public void setLogger() {
        logger.setUseParentHandlers(false);
        Handler fileHandler = null;
        try {
            fileHandler = new FileHandler("logs/" + getLogin() + ".txt", true);
            fileHandler.setFormatter(new UserLogFormatter());
        } catch (IOException ignored) {
        }
        if (fileHandler != null) {
            logger.addHandler(fileHandler);
        }
    }

    public void log(String message) {
        logger.info(message + "\n");
    }

    public static void log(Throwable e) {
        logger.log(Level.SEVERE, "Exception: ", e);
    }

    public static void logClose() {
        for (Handler h : logger.getHandlers()) {
            h.close();
        }
    }


}

class UserLogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return record.getMessage();
    }
}




