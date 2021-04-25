package by.itacademy.andrei_delesevich.cinema.dao.userdao;

import by.itacademy.andrei_delesevich.cinema.connection.AbstractConnection;
import by.itacademy.andrei_delesevich.cinema.exception.UserDaoException;
import by.itacademy.andrei_delesevich.cinema.model.user.User;
import by.itacademy.andrei_delesevich.cinema.model.user.UserAccessLevel;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {


    @Override
    public User createUser(User user) throws UserDaoException {
        try (Connection con = AbstractConnection.getConnection()) {
            if (con != null) {
                PreparedStatement stmt = con.prepareStatement("INSERT INTO users (login, password, access) VALUES (?,?,?)");
                stmt.setString(1, user.getLogin());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getAccess().toString());
                boolean create = false;
                try {
                    create = stmt.executeUpdate()>0;
                } catch (MySQLIntegrityConstraintViolationException e) {
                    System.out.println("\nПользователь с таким логином уже сущеуствует");
                }
                if (create){
                    return user;
                }else return null;
            } else return null;
        } catch (SQLException e) {
            System.err.println("Ошибка создания пользователя");
            return null;
        }

    }

    @Override
    public User readUser(String login) {
        User user = null;
        try (Connection con = AbstractConnection.getConnection()) {
            if (con != null) {
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM users  WHERE login = (?)");
                stmt.setString(1, login);

                ResultSet rs = stmt.executeQuery();
                String log;
                String pass;
                int id;
                String acc;


                while (rs.next()) {
                    log = rs.getString("login");
                    id = rs.getInt("id");
                    acc = rs.getString("access");
                    user = new User(id, log, UserAccessLevel.valueOf(acc));
                }
                return user;
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return user;
        }
    }

    @Override
    public boolean updateUser(String login, User newUser) throws UserDaoException {
        try (Connection con = AbstractConnection.getConnection()) {

            User user = readUser(login);
            PreparedStatement stmt = con.prepareStatement("UPDATE users SET login =?, password=?  WHERE login = ?");
            stmt.setString(1, newUser.getLogin());
            stmt.setString(2, newUser.getPassword());
            stmt.setString(3, login);

            return stmt.executeUpdate()>0;
        } catch (SQLException throwables) {
            throw new UserDaoException("Ошибка изменения пользователя", throwables);
        }
    }

    @Override
    public boolean deleteUser(String login) {
        try (Connection con = AbstractConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM users WHERE login =?");
            stmt.setString(1, login);

            return stmt.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    @Override
    public List<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<>();
        try (Connection con = AbstractConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM users");

            ResultSet rs = stmt.executeQuery();
            String login;
            String pass;
            int id;
            UserAccessLevel access;

            while (rs.next()) {
                login = rs.getString("login");
                pass = rs.getString("password");
                id = rs.getInt("id");
                access = UserAccessLevel.valueOf(rs.getString("access"));

                list.add(new User(id, login, pass, access));
            }
            return list;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return list;
        }
    }

    @Override
    public String getUserPass(String userLogin) {
        String pass = "";
        try (Connection con = AbstractConnection.getConnection()) {
            if (con != null) {
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM users  WHERE login = (?)");
                stmt.setString(1, userLogin);

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    pass = rs.getString("password");
                }
            }
            return pass;

        } catch (SQLException throwables) {
            System.err.println("Ошибка чтения праоля");
            return pass;
        }
    }
}

