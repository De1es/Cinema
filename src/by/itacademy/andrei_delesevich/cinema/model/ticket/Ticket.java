package by.itacademy.andrei_delesevich.cinema.model.ticket;

import java.util.Objects;

public class Ticket {
    private int id;
    private String buyer;
    private String movie;
    private int placeNumber;
    private int price;
    private boolean isSold;

    public Ticket(int id, String buyerName, String movie, int placeNumber, int price, boolean isSold) {
        this.id = id;
        this.buyer = buyerName;
        this.movie = movie;
        this.placeNumber = placeNumber;
        this.price = price;
        this.isSold = isSold;
    }

    public Ticket(String movie, int placeNumber, int price, boolean isSold) {
        this.movie = movie;
        this.placeNumber = placeNumber;
        this.price = price;
        this.isSold = isSold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    @Override
    public String toString() {
        return "\nБилет № " + id +
                ", покупатель: " + buyer + '\'' +
                ", фильм: " + movie + '\'' +
                ", место: " + placeNumber +
                ", цена: " + price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && placeNumber == ticket.placeNumber && price == ticket.price && isSold == ticket.isSold && Objects.equals(buyer, ticket.buyer) && Objects.equals(movie, ticket.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, buyer, movie, placeNumber, price, isSold);
    }
}
