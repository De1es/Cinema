package by.itacademy.andrei_delesevich.cinema.dao.userdao;

import by.itacademy.andrei_delesevich.cinema.exception.UserDaoException;
import by.itacademy.andrei_delesevich.cinema.model.user.User;

import java.util.List;

public interface UserDao {

    User createUser(User user) throws UserDaoException;
    User readUser(String name);
    boolean updateUser(String name, User newUser) throws UserDaoException;
    boolean deleteUser(String name);
    List<User> getAllUsers();
    String getUserPass (String userLogin);


}
