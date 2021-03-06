package by.itacademy.andrei_delesevich.cinema.service.movie;

import by.itacademy.andrei_delesevich.cinema.dao.moviedao.MovieDao;
import by.itacademy.andrei_delesevich.cinema.exception.MovieDaoException;
import by.itacademy.andrei_delesevich.cinema.model.movie.Movie;
import by.itacademy.andrei_delesevich.cinema.model.user.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class MovieServiceImpl implements MovieService {
    MovieDao md = null;

    public MovieServiceImpl(MovieDao md) {
        this.md = md;
    }

    @Override
    public List<Movie> getNextMovies() {
        List<Movie> list = null;
        try {
            list = md.getAllMovies();
        } catch (MovieDaoException e) {
            System.err.println("Ошибка получения списка фильмов");
            User.log(e);
        }
        if (list!=null) {
            list = list.stream()
                    .filter(n -> n.getTimestamp().after(Timestamp.from(Instant.now())))
                    .collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public boolean movieUpdate(int movieId, Movie newMovie) {
        try {
            md.updateMovie(movieId, newMovie);
            return true;
        } catch (MovieDaoException m) {
            System.out.println(m.getMessage());
            User.log(m);
            return false;
        }
    }

    @Override
    public boolean movieDelete(int movieId) {
        try {
            return md.deleteMovie(movieId);

        } catch (MovieDaoException e) {
            System.out.println(e.getMessage());
            User.log(e);
            return false;
        }
    }

    @Override
    public boolean movieCreate(Movie movie) {
        try {
            return md.createMovie(movie);
        } catch (MovieDaoException e) {
            System.out.println(e.getMessage());
            User.log(e);
            return false;
        }
    }

    @Override
    public Movie getMovie(String title, String year, String month, String day, String hour, String minute) {
        Movie movie=null;
        try {
            movie = md.readMovie(title, Timestamp.valueOf(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00"));
            return movie;
        }catch (MovieDaoException  | IllegalArgumentException e){
            System.out.println(e.getMessage());
            User.log(e);
            return null;
        }
    }
}
