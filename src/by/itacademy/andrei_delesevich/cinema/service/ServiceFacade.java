package by.itacademy.andrei_delesevich.cinema.service;

import by.itacademy.andrei_delesevich.cinema.service.movie.MovieService;
import by.itacademy.andrei_delesevich.cinema.service.ticket.TicketService;
import by.itacademy.andrei_delesevich.cinema.service.user.UserService;

public class ServiceFacade {
    public UserService userService = null;
    public MovieService movieService = null;
    public TicketService ticketService = null;

    public ServiceFacade(UserService userService, MovieService movieService, TicketService ticketService) {
        this.userService = userService;
        this.movieService = movieService;
        this.ticketService = ticketService;
    }
}
