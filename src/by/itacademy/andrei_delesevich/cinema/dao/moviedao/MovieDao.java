package by.itacademy.andrei_delesevich.cinema.dao.moviedao;

import by.itacademy.andrei_delesevich.cinema.exception.MovieDaoException;
import by.itacademy.andrei_delesevich.cinema.exception.UserDaoException;
import by.itacademy.andrei_delesevich.cinema.model.movie.Movie;
import by.itacademy.andrei_delesevich.cinema.model.user.User;

import java.sql.Timestamp;
import java.util.List;

public interface MovieDao {

    boolean createMovie(Movie movie) throws MovieDaoException;
    Movie readMovie(String title, Timestamp ts) throws MovieDaoException;
    boolean updateMovie(int id, Movie newMovie) throws MovieDaoException;
    boolean deleteMovie(int id) throws MovieDaoException;
    List<Movie> getAllMovies() throws MovieDaoException;


}
