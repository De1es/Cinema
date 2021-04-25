package by.itacademy.andrei_delesevich.cinema.dao.moviedao;

import by.itacademy.andrei_delesevich.cinema.connection.AbstractConnection;
import by.itacademy.andrei_delesevich.cinema.exception.MovieDaoException;
import by.itacademy.andrei_delesevich.cinema.model.movie.Movie;

import java.net.ConnectException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovieDaoImpl implements MovieDao {
    @Override
    public boolean createMovie(Movie movie) throws MovieDaoException {
        try (Connection con = AbstractConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO movies (title, dateTime) " +
                    "VALUES (?,?)");
            stmt.setString(1, movie.getTitle());
            stmt.setTimestamp(2, movie.getTimestamp());
            return stmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw  new MovieDaoException("Ошибка создания киносеанса", throwables);
        }
    }

    @Override
    public Movie readMovie(String title, Timestamp ts) throws MovieDaoException {
        Movie movie = null;
        try (Connection con = AbstractConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM movies  WHERE (title, dateTime) = (?,?)");
            stmt.setString(1, title);
            stmt.setTimestamp(2, ts);

            ResultSet rs = stmt.executeQuery();
            int id;
            String title1;
            Timestamp timestamp;

            while (rs.next()) {
                id = rs.getInt("id");
                title1 = rs.getString("title");
                timestamp = rs.getTimestamp("dateTime");
                movie = new Movie(id, title1, timestamp);
            }

            return movie;
        } catch (SQLException throwables) {
            throw  new MovieDaoException("Ошибка чтения киносеанса", throwables);
        }
    }

    @Override
    public boolean updateMovie(int id, Movie newMovie) throws MovieDaoException {
        try (Connection con = AbstractConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("UPDATE movies SET title =?, dateTime=?  WHERE id = ?");
            stmt.setString(1, newMovie.getTitle());
            stmt.setTimestamp(2, newMovie.getTimestamp());
            stmt.setInt(3, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw  new MovieDaoException("Ошибка изменения киносеанса", throwables);
        }
    }

    @Override
    public boolean deleteMovie(int id) throws MovieDaoException {
        try (Connection con = AbstractConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM movies WHERE id =?");
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throw  new MovieDaoException("Ошбика удаления киносеанса", throwables);
        }
    }

    @Override
    public List<Movie> getAllMovies() throws MovieDaoException {
        ArrayList<Movie> list = new ArrayList<>();
        try (Connection con = AbstractConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM movies");

            ResultSet rs = stmt.executeQuery();
            int id;
            String title;
            Timestamp timestamp;

            while (rs.next()) {
                id = rs.getInt("id");
                title = rs.getString("title");
                timestamp = rs.getTimestamp("dateTime");

                list.add(new Movie(id, title, timestamp));
            }
            return list;

        } catch (SQLException throwables) {
            throw  new MovieDaoException("Ошибка получения списка киносеансов", throwables);
        }


    }
}

