package by.itacademy.andrei_delesevich.cinema;

import by.itacademy.andrei_delesevich.cinema.controller.Controller;
import by.itacademy.andrei_delesevich.cinema.controller.MenuController;



/*
    Аккаунт админа   login admin   pass admin

    Аккаунт менеджера  login manager pass manager

 */

public class Main {
    public static void main(String[] args){

        Controller menu = new MenuController();

        menu.start();


//        log4j скачать jar


        // ошибки из дао слать в сервис и там посылать в лог , и юзеру сообщение
        }
}
