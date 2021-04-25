package by.itacademy.andrei_delesevich.cinema.service.ticket;

import by.itacademy.andrei_delesevich.cinema.dao.ticketdao.TicketDao;
import by.itacademy.andrei_delesevich.cinema.dao.ticketdao.TicketDaoImpl;
import by.itacademy.andrei_delesevich.cinema.exception.TicketDaoException;
import by.itacademy.andrei_delesevich.cinema.model.ticket.Ticket;
import by.itacademy.andrei_delesevich.cinema.model.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class TicketServiceImpl implements TicketService {

    TicketDao td = new TicketDaoImpl();

    @Override
    public List<Ticket> getFreePlaces(int movieId) {
        try {
            List<Ticket> list = td.getMovieTickets(movieId);
            list = list.stream().filter(n-> !n.isSold()).collect(Collectors.toList());
            return list;
        } catch (TicketDaoException e) {
            System.out.println("Ошибка получения свободных мест");
            return null;
        }
    }

    @Override
    public boolean buyTicket(int ticketId, String buyer) {
        try {
            Ticket ticket = td.readTicket(ticketId);
            if (ticket != null) {
                ticket.setSold(true);
                ticket.setBuyer(buyer);
                td.updateTicket(ticketId, ticket);
                return true;
            } else return false;
        } catch (TicketDaoException e) {
            System.out.println("Ошибка покупки билета");
            return false;
        }
    }

    @Override
    public List<Ticket> getUserTickets(String user) {
        try {
            return td.getUserTickets(user);
        } catch (TicketDaoException e) {
            System.out.println("Ошибка получения билетов пользователя");
            return null;
        }
    }

    @Override
    public boolean ticketReturn(int ticketId) {
        try {
            Ticket ticket = td.readTicket(ticketId);
            if (ticket != null) {
                ticket.setSold(false);
                ticket.setBuyer(null);
                td.updateTicket(ticketId, ticket);
                return true;
            } else return false;
        } catch (TicketDaoException e) {
            System.out.println("Ошибка возврата билета");
            return false;
        }
    }

    @Override
    public boolean ticketsForMovieCreate(String title, int placeCapacity, int ticketPrice, int movieId) {
        for (int i = 0; i < placeCapacity; i++) {
            try {
                td.createTicket(new Ticket(title, i + 1, ticketPrice, false), movieId);
            } catch (TicketDaoException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    @Override
    public Ticket readTicket(int ticketId) {
        try {
            return td.readTicket(ticketId);
        } catch (TicketDaoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteTicketsOnMovie(int movieId) {
        List<Ticket> list = null;
        try {
            list = td.getMovieTickets(movieId);

            if (list != null) {
                for (Ticket t : list
                ) {
                    td.deleteTicket(t.getId());
                }
                return true;
            } else return false;
        } catch (TicketDaoException e) {
            System.out.println("Ошибка получения списка билетов");
            return false;
        }

    }
}
