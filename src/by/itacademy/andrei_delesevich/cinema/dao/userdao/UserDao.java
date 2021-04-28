package by.itacademy.andrei_delesevich.cinema.dao.userdao;

import by.itacademy.andrei_delesevich.cinema.exception.UserDaoException;
import by.itacademy.andrei_delesevich.cinema.model.user.User;

import java.util.List;

public interface UserDao {

    User createUser(User user) throws UserDaoException;
    User readUser(String name) throws UserDaoException;
    boolean updateUser(String name, User newUser) throws UserDaoException;
    boolean deleteUser(String name) throws UserDaoException;
    List<User> getAllUsers() throws UserDaoException;
    String getUserPass (String userLogin) throws UserDaoException;


}
