package by.itacademy.andrei_delesevich.cinema.dao.ticketdao;

import by.itacademy.andrei_delesevich.cinema.connection.AbstractConnection;
import by.itacademy.andrei_delesevich.cinema.exception.TicketDaoException;
import by.itacademy.andrei_delesevich.cinema.model.ticket.Ticket;
import by.itacademy.andrei_delesevich.cinema.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoImpl implements TicketDao {
    @Override
    public Ticket createTicket(Ticket ticket, int movieId) throws TicketDaoException {
        try (Connection con = AbstractConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO tickets (movie, place, price, isSold," +
                    " movieId)" +
                    " VALUES (?,?,?,?,?)");
            stmt.setString(1, ticket.getMovie());
            stmt.setInt(2, ticket.getPlaceNumber());
            stmt.setInt(3, ticket.getPrice());
            stmt.setBoolean(4, ticket.isSold());
            stmt.setInt(5, movieId);
            stmt.execute();
            return ticket;
        } catch (SQLException throwables) {
            throw new TicketDaoException("Ошибка создания билета", throwables);
        }

    }

    @Override
    public Ticket readTicket(int ticketId) throws TicketDaoException {
        Ticket ticket = null;
        try (Connection con = AbstractConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM tickets  WHERE id = ?");
            stmt.setInt(1, ticketId);

            ResultSet rs = stmt.executeQuery();
            int id;
            String buyer;
            String movie;
            int placeNumber;
            int price;
            boolean isSold;


            while (rs.next()) {
                id = rs.getInt("id");
                buyer = rs.getString("buyer");
                movie = rs.getString("movie");
                placeNumber = rs.getInt("place");
                price = rs.getInt("price");
                isSold = rs.getBoolean("isSold");
                ticket = new Ticket(id, buyer, movie, placeNumber, price, isSold);
            }

            return ticket;
        } catch (SQLException throwables) {
            throw new TicketDaoException("Ошибка чтения билета", throwables );
        }
    }

    @Override
    public Ticket updateTicket(int id, Ticket newTicket)  throws TicketDaoException  {
        try (Connection con = AbstractConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("UPDATE tickets SET buyer =?, isSold=?  WHERE id = ?");
            stmt.setString(1, newTicket.getBuyer());
            stmt.setBoolean(2, newTicket.isSold());
            stmt.setInt(3, id);

            stmt.execute();

            return readTicket(id);
        } catch (SQLException throwables) {
            throw new TicketDaoException("Ошибка изменения билета", throwables );
        }
    }

    @Override
    public boolean deleteTicket(int id)  throws TicketDaoException  {
        try (Connection con = AbstractConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM tickets WHERE id =?");
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throw new TicketDaoException("Ошибка удаления билета", throwables );
        }
    }

    @Override
    public List<Ticket> getAllTickets()  throws TicketDaoException  {
        ArrayList<Ticket> list = new ArrayList<>();
        try (Connection con = AbstractConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM tickets");

            ResultSet rs = stmt.executeQuery();
            int id;
            String buyer;
            String movie;
            int placeNumber;
            int price;
            boolean isSold;


            while (rs.next()) {
                id = rs.getInt("id");
                buyer = rs.getString("buyer");
                movie = rs.getString("movie");
                placeNumber = rs.getInt("place");
                price = rs.getInt("price");
                isSold = rs.getBoolean("isSold");

                list.add(new Ticket(id, buyer, movie, placeNumber, price, isSold));

            }
            return list;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return list;
        }
    }

    @Override
    public List<Ticket> getUserTickets(String user)  throws TicketDaoException  {
        ArrayList<Ticket> list = new ArrayList<>();
        try (Connection con = AbstractConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM tickets WHERE buyer=?");
            stmt.setString(1, user);


            ResultSet rs = stmt.executeQuery();
            int id;
            String buyer;
            String movie;
            int placeNumber;
            int price;
            boolean isSold;


            while (rs.next()) {
                id = rs.getInt("id");
                buyer = rs.getString("buyer");
                movie = rs.getString("movie");
                placeNumber = rs.getInt("place");
                price = rs.getInt("price");
                isSold = rs.getBoolean("isSold");

                list.add(new Ticket(id, buyer, movie, placeNumber, price, isSold));

            }
            return list;

        } catch (SQLException throwables) {
            throw new TicketDaoException("Ошибка получения списка билетов пользователя", throwables );
        }
    }

    @Override
    public List<Ticket> getMovieTickets(int movieId)  throws TicketDaoException  {
        ArrayList<Ticket> list = new ArrayList<>();
        try (Connection con = AbstractConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM tickets WHERE movieId=?");
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(readTicket(rs.getInt("id")));
            }
            return list;
        } catch (SQLException throwables) {
            throw new TicketDaoException("Ошибка получения списка билетов на киносеанс", throwables );
        }
    }



}

