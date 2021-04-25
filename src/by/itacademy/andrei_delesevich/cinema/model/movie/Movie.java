package by.itacademy.andrei_delesevich.cinema.model.movie;

import by.itacademy.andrei_delesevich.cinema.exception.MovieDaoException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Movie {
    private int id;
    private String title;
    private Timestamp timestamp;

    public Movie(String title, String year, String month, String day, String hour, String minute) {
        this.title = title;
        try {
            setTimestamp(Timestamp.valueOf(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00"));
        }catch (IllegalArgumentException e){
            System.err.println("Неправильно введены элементы даты или времени");
        }
    }

    public Movie(int id, String title, Timestamp timestamp) {
        this.id = id;
        this.title = title;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        if (timestamp.after(Timestamp.valueOf(LocalDateTime.now()))){
            this.timestamp = timestamp;
        }
    }

    @Override
    public String toString() {
        String str = "\n№: " + id +
                ", фильм: '" + title + '\'' +
                ", дата и время показа: " + timestamp;
        return str.substring(0,str.length()-5);
    }
}
