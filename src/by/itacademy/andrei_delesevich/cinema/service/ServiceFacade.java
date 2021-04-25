package by.itacademy.andrei_delesevich.cinema.service;

import by.itacademy.andrei_delesevich.cinema.service.movie.MovieService;
import by.itacademy.andrei_delesevich.cinema.service.movie.MovieServiceImpl;
import by.itacademy.andrei_delesevich.cinema.service.ticket.TicketService;
import by.itacademy.andrei_delesevich.cinema.service.ticket.TicketServiceImpl;
import by.itacademy.andrei_delesevich.cinema.service.user.UserService;
import by.itacademy.andrei_delesevich.cinema.service.user.UserServiceImpl;

public class ServiceFacade {
    public UserService userService = null;
    public MovieService movieService = null;
    public TicketService ticketService = null;

    public ServiceFacade() {
        userService = new UserServiceImpl();
        movieService = new MovieServiceImpl();
        ticketService = new TicketServiceImpl();
    }
}
