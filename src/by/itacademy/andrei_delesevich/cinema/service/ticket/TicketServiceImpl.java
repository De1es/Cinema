package by.itacademy.andrei_delesevich.cinema.service.ticket;

import by.itacademy.andrei_delesevich.cinema.dao.ticketdao.TicketDao;
import by.itacademy.andrei_delesevich.cinema.exception.TicketDaoException;
import by.itacademy.andrei_delesevich.cinema.model.ticket.Ticket;
import by.itacademy.andrei_delesevich.cinema.model.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class TicketServiceImpl implements TicketService {

    TicketDao td = null;

    public TicketServiceImpl(TicketDao td) {
        this.td = td;
    }

    @Override
    public List<Ticket> getFreePlaces(int movieId) {
        try {
            List<Ticket> list = td.getMovieTickets(movieId);
            list = list.stream().filter(n -> !n.isSold()).collect(Collectors.toList());
            return list;
        } catch (TicketDaoException e) {
            System.out.println("Ошибка получения свободных мест");
            User.log(e);
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
            User.log(e);
            return false;
        }
    }

    @Override
    public List<Ticket> getUserTickets(String user) {
        try {
            return td.getUserTickets(user);
        } catch (TicketDaoException e) {
            System.out.println("Ошибка получения билетов пользователя");
            User.log(e);
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
            User.log(e);
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
                User.log(e);
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
            User.log(e);
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
            User.log(e);
            return false;
        }
    }

    @Override
    public boolean changeTicketsUser(String oldUserLogin, String newUserLogin) {
        try {
            List<Ticket> list = td.getUserTickets(oldUserLogin);
            if (list != null) {
                for (Ticket t : list) {
                    Ticket ticket = new Ticket(t.getId(), newUserLogin, t.getMovie(),
                            t.getPlaceNumber(), t.getPrice(), t.isSold());
                    td.updateTicket(t.getId(), ticket);
                }
                return true;
            }
            return false;
        } catch (TicketDaoException e) {
            System.out.println("Ошибка изменения имени пользователя в билетах");
            User.log(e);
            return false;
        }
    }
}
