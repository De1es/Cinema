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
        try {
            User user = userDao.readUser(userEntry.getName());
            if (user != null && userDao.getUserPass(userEntry.getName()).equals(userEntry.getPassword())) {
                return user;
            }
            return null;
        } catch (UserDaoException e) {
            User.log(e);
            return null;
        }
    }

    @Override
    public boolean userRegistration(UserEntry userEntry) {
        passHash(userEntry);
        User user = new User(userEntry.getName(), userEntry.getPassword(), UserAccessLevel.USER);
        try {
            if (userDao.createUser(user) != null) {
                user.setLogger();
                user.log("Пользователь " + user.getLogin() + " зарегистрировался");
                User.logClose();
                return true;
            } else return false;
        } catch (UserDaoException e) {
            System.err.println("\nОшибка регистрации (создания) пользователя");
            User.log(e);
            return false;
        }
    }

    @Override
    public void passHash(UserEntry userEntry) {
        userEntry.setPassword(md5Custom(userEntry.getPassword()));
    }

    @Override
    public User userDelete(String userLogin) {
        try {
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
        } catch (UserDaoException e) {
            User.log(e);
            return null;
        }

    }

    @Override
    public boolean userUpdate(String userLogin, User newUser) {
        User user = null;
        try {
            user = userDao.readUser(userLogin);
        } catch (UserDaoException e) {
            User.log(e);
        }
        if (user != null) {
            newUser.setPassword(md5Custom(newUser.getPassword()));
            if (user.getAccess() == UserAccessLevel.ADMIN || user.getAccess() == UserAccessLevel.MANAGER) {
                return false;
            }
            try {
                return userDao.updateUser(userLogin, newUser);
            } catch (UserDaoException e) {
                System.out.println(e.getMessage());
                User.log(e);
                return false;
            }
        } else return false;
    }

    @Override
    public User userRead(String login) {
        try {
            return userDao.readUser(login);
        } catch (UserDaoException e) {
            User.log(e);
            return null;
        }
    }
}


