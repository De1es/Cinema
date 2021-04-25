package by.itacademy.andrei_delesevich.cinema.dao.ticketdao;

import by.itacademy.andrei_delesevich.cinema.exception.TicketDaoException;
import by.itacademy.andrei_delesevich.cinema.model.ticket.Ticket;
import by.itacademy.andrei_delesevich.cinema.model.user.User;

import java.util.List;

public interface TicketDao {
    Ticket createTicket(Ticket ticket, int movieId) throws TicketDaoException;

    Ticket readTicket(int id) throws TicketDaoException;

    Ticket updateTicket(int id, Ticket newTicket) throws TicketDaoException;

    boolean deleteTicket(int id) throws TicketDaoException;

    List<Ticket> getAllTickets() throws TicketDaoException;

    List <Ticket> getUserTickets(String user) throws TicketDaoException;

    List <Ticket> getMovieTickets (int movieId) throws TicketDaoException;




}
