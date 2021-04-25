package by.itacademy.andrei_delesevich.cinema.service.ticket;

import by.itacademy.andrei_delesevich.cinema.model.ticket.Ticket;
import by.itacademy.andrei_delesevich.cinema.model.user.User;

import java.util.List;

public interface TicketService {
    List<Ticket> getFreePlaces(int movieId);

    boolean buyTicket(int ticketId, String buyer);

    List <Ticket> getUserTickets (String user);

    boolean ticketReturn (int ticketId);

    boolean ticketsForMovieCreate (String title, int placeCapacity, int ticketPrice, int movieId);

    Ticket readTicket(int ticketId);

    boolean deleteTicketsOnMovie (int movieId);
}
