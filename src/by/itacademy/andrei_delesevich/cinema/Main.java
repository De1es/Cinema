package by.itacademy.andrei_delesevich.cinema;

import by.itacademy.andrei_delesevich.cinema.controller.Controller;
import by.itacademy.andrei_delesevich.cinema.controller.MenuController;
import by.itacademy.andrei_delesevich.cinema.dao.moviedao.MovieDao;
import by.itacademy.andrei_delesevich.cinema.dao.moviedao.MovieDaoImpl;
import by.itacademy.andrei_delesevich.cinema.dao.ticketdao.TicketDao;
import by.itacademy.andrei_delesevich.cinema.dao.ticketdao.TicketDaoImpl;
import by.itacademy.andrei_delesevich.cinema.dao.userdao.UserDao;
import by.itacademy.andrei_delesevich.cinema.dao.userdao.UserDaoImpl;
import by.itacademy.andrei_delesevich.cinema.service.ServiceFacade;
import by.itacademy.andrei_delesevich.cinema.service.movie.MovieService;
import by.itacademy.andrei_delesevich.cinema.service.movie.MovieServiceImpl;
import by.itacademy.andrei_delesevich.cinema.service.ticket.TicketService;
import by.itacademy.andrei_delesevich.cinema.service.ticket.TicketServiceImpl;
import by.itacademy.andrei_delesevich.cinema.service.user.UserService;
import by.itacademy.andrei_delesevich.cinema.service.user.UserServiceImpl;




/*
    Аккаунт админа   login admin pass admin

    Аккаунт менеджера  login manager pass manager

    Аккаунт пользователя login user pass user

 */

public class Main {
    public static void main(String[] args){

        UserDao userDao = new UserDaoImpl();
        MovieDao movieDao = new MovieDaoImpl();
        TicketDao ticketDao = new TicketDaoImpl();

        UserService userService = new UserServiceImpl(userDao);
        MovieService movieService = new MovieServiceImpl(movieDao);
        TicketService ticketService = new TicketServiceImpl(ticketDao);

        ServiceFacade sf = new ServiceFacade(userService, movieService, ticketService);

        Controller menu = new MenuController(sf);

        menu.start();

        }
}
