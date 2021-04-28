package by.itacademy.andrei_delesevich.cinema.service.movie;

import by.itacademy.andrei_delesevich.cinema.model.movie.Movie;

import java.util.List;

public interface MovieService {
    public List<Movie> getNextMovies();

    public boolean movieUpdate(int movieId, Movie newMovie);

    public boolean movieDelete(int movieId);

    public boolean movieCreate (Movie movie);

    Movie getMovie (String title, String year, String month, String day, String hour, String minute);


}
