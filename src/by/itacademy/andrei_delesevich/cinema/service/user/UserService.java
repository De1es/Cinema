package by.itacademy.andrei_delesevich.cinema.service.user;

import by.itacademy.andrei_delesevich.cinema.model.user.User;
import by.itacademy.andrei_delesevich.cinema.model.user.UserEntry;

public interface UserService {
    User userLogin (UserEntry userEntry);
    boolean userRegistration (UserEntry userEntry);
    void passHash(UserEntry userEntry);
    User userDelete (String userLogin);
    boolean userUpdate (String userLogin, User newUser);

    User userRead (String login);

}
