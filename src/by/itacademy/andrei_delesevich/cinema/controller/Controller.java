package by.itacademy.andrei_delesevich.cinema.controller;

import by.itacademy.andrei_delesevich.cinema.model.user.User;

public interface Controller {
    public void start();
    public void userMenu(User user);
    public void managerMenu(User user);
    public void adminMenu(User user);
}
