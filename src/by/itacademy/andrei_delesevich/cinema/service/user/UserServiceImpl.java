package by.itacademy.andrei_delesevich.cinema.service.user;

import by.itacademy.andrei_delesevich.cinema.dao.userdao.UserDao;
import by.itacademy.andrei_delesevich.cinema.exception.UserDaoException;
import by.itacademy.andrei_delesevich.cinema.model.user.User;
import by.itacademy.andrei_delesevich.cinema.model.user.UserAccessLevel;
import by.itacademy.andrei_delesevich.cinema.model.user.UserEntry;

import static by.itacademy.andrei_delesevich.cinema.service.HashFunction.md5Custom;


public class UserServiceImpl implements UserService {
    UserDao userDao = null;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User userLogin(UserEntry userEntry) {
        passHash(userEntry);
        User user = userDao.readUser(userEntry.getName());
        if (user != null && userDao.getUserPass(userEntry.getName()).equals(userEntry.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public boolean userRegistration(UserEntry userEntry) {
        passHash(userEntry);
        User user = new User(userEntry.getName(), userEntry.getPassword(), UserAccessLevel.USER);
        try {
            return (userDao.createUser(user) != null);
        } catch (UserDaoException e) {
            System.err.println("\nОшибка регистрации (создания) пользователя");
            return false;
        }
    }

    @Override
    public void passHash(UserEntry userEntry) {
        userEntry.setPassword(md5Custom(userEntry.getPassword()));
    }

    @Override
    public User userDelete(String userLogin) {
        User user = userDao.readUser(userLogin);
        if (user == null) {
            return null;
        }

        if (user.getAccess() == UserAccessLevel.ADMIN || user.getAccess() == UserAccessLevel.MANAGER) {
            return null;
        }

        if (!userDao.deleteUser(userLogin)) {
            return null;
        }

        return user;

    }

    @Override
    public boolean userUpdate(String userLogin, User newUser) {
        User user = userDao.readUser(userLogin);
        if (user != null) {
            newUser.setPassword(md5Custom(newUser.getPassword()));
            if (user.getAccess() == UserAccessLevel.ADMIN || user.getAccess() == UserAccessLevel.MANAGER) {
                return false;
            } else {
                try {
                    return userDao.updateUser(userLogin, newUser);
                } catch (UserDaoException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
            }
        } else return false;
    }

    @Override
    public User userRead(String login) {
        return userDao.readUser(login);
    }
}


