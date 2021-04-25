package by.itacademy.andrei_delesevich.cinema.controller;

import by.itacademy.andrei_delesevich.cinema.model.movie.Movie;
import by.itacademy.andrei_delesevich.cinema.model.ticket.Ticket;
import by.itacademy.andrei_delesevich.cinema.model.user.User;
import by.itacademy.andrei_delesevich.cinema.model.user.UserAccessLevel;
import by.itacademy.andrei_delesevich.cinema.model.user.UserEntry;
import by.itacademy.andrei_delesevich.cinema.service.ServiceFacade;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuController implements Controller {
    ServiceFacade sf = new ServiceFacade();
    Scanner sc = new Scanner(System.in);


    @Override
    public void start() {
        System.out.println("Добро пожаловать в систему продажи билетов нашего кинотеатра!");
        int k = 0;
        while (k == 0) {
            System.out.println("\nВведите: \n" +
                    "1 - для входа в систему,\n" +
                    "2 - для регистрации,\n" +
                    "0 - для выхода.");
            Scanner sc = new Scanner(System.in);
            String choice1;
            choice1 = sc.nextLine();
            switch (choice1) {
                case "1":
                    System.out.println("Введите логин:");
                    String login = sc.next();
                    System.out.println("Введите пароль:");
                    String password = sc.next();
                    User user = sf.userService.userLogin(new UserEntry(login, password));
                    if (user == null) {
                        System.err.println("\nОшибка входа, такого логина не существует, либо неверный пароль");
                    } else {
                        switch (user.getAccess()) {
                            case USER:
                                userMenu(user);
                                break;
                            case MANAGER:
                                managerMenu(user);
                                break;
                            case ADMIN:
                                adminMenu(user);
                                break;
                        }
                    }
                    break;
                case "2":
                    System.out.println("Введите логин:");
                    String loginReg = sc.next();
                    System.out.println("Введите пароль:");
                    String passwordReg = sc.next();
                    if (sf.userService.userRegistration(new UserEntry(loginReg, passwordReg))) {
                        System.out.println("Пользователь зарегистрирован");
                    } else {
                        System.out.println("Ошибка регистрации пользователя");
                    }
                    break;
                case "0":
                    System.out.println("До свидания, ждем Вас снова!");
                    k++;
                    break;
                default:
                    System.out.println("Команда введена неверно, пожалуйста, попробуйте еще раз");
            }
        }
    }

    @Override
    public void userMenu(User user) {
        System.out.println("Пользователь " + user.getLogin() + ", добро пожаловть!");
        int n = 1;
        while (n != 0) {
            Scanner sc = new Scanner(System.in);
            System.out.println("\nВведите:\n" +
                    "1 - для просмотра киносеансов и покупки билетов на них,\n" +
                    "2 - для просмотра купленных билетов, и при желании возврата,\n" +
                    "0 - для выхода из учетной записи");

            String choice = sc.next();
            switch (choice) {
                case "1":
                    movieChoiceMenu(user);  // меню выбора кино и покупки на него билета
                    break;
                case "2":
                    viewUserTickets(user.getLogin());  // меню просмотра купленных билетов и возврата
                    break;
                case "0":
                    n = 0;
                    break;
                default:
                    System.err.println("Команда введена неверно! Пожалуйста, попробуйте еще раз!");
            }
        }
    }

    void movieChoiceMenu(User user) {
        List<Movie> list = sf.movieService.getNextMovies();
        System.out.println("Будущие киносеансы:" + list.toString().substring(1, list.toString().length() - 1) + ".");
        System.out.println("Для покупки билета на киносеанс введите номер киносеанса," +
                " для возврата в предыдущее меню введите 0.");
        int movieId = -1;
        while (movieId == -1) {
            Scanner sc = new Scanner(System.in);
            try {
                int num = sc.nextInt();
                if (num == 0) {
                    return;
                }

                for (Movie m : list
                ) {
                    if (m.getId() == num) {
                        movieId = num;
                        break;
                    }
                }
                if (movieId == -1) {
                    System.out.println("Киносеанса с таким номером в списке нет, попробуйте еще раз");
                }
            } catch (InputMismatchException e) {
                System.err.println("Команда должна быть числом");
            }
        }


        List<Ticket> freePlaces = sf.ticketService.getFreePlaces(movieId);
        if (freePlaces == null) {
            System.out.println("Свободных мест на данный киносеанс нет, " +
                    "пожалуйста, выберите другой киносеанс");
        } else {
            System.out.println("Цена билета: " + freePlaces.get(0).getPrice() + "\n");
            StringBuffer sb = new StringBuffer("Свободные места: ");
            for (Ticket t : freePlaces
            ) {
                sb.append(t.getPlaceNumber()).append(" ");
            }
            System.out.println(sb);
            System.out.println("Введите номер приобретаемого места:");
            int place = -1;
            int ticketId = 0;
            while (place == -1) {
                Scanner sc = new Scanner(System.in);
                try {
                    int num = sc.nextInt();
                    if (num > 0) {
                        for (Ticket t : freePlaces
                        ) {
                            if (t.getPlaceNumber() == num) {
                                place = num;
                                ticketId = t.getId();
                                break;
                            }
                        }
                    }
                    if (place == -1) {
                        System.out.println("Билета с таким номером в списке нет, попробуйте еще раз");
                    }
                } catch (InputMismatchException e) {
                    System.err.println("Команда должна быть числом");
                }
            }
            if (sf.ticketService.buyTicket(ticketId, user.getLogin())) {
                System.out.println("Билет куплен успешно!");
            } else System.out.println("Ошибка при покупке билета!");
        }
    }

    void viewUserTickets(String user) {
        System.out.println("Ваши купленные билеты:");
        List<Ticket> list = sf.ticketService.getUserTickets(user);
        System.out.println(list);
        System.out.println("Для возврата одного из указанных билетов введите номер билета,\n" +
                "для выхода в предыдущее меню введите 0");
        int choice = 0;

        while (choice == 0) {
            Scanner sc = new Scanner(System.in);
            try {
                int num = sc.nextInt();
                if (num == 0) {
                    return;
                }
                for (Ticket t : list) {
                    if (t.getId() == num) {
                        choice = num;
                        break;
                    }
                }

                if (choice == 0) {
                    System.out.println("У Вас не куплен билет с таким номером, выберите билет из списка");
                }
            } catch (InputMismatchException e) {
                System.err.println("Команда должна быть числом");

            }
        }
        if (sf.ticketService.ticketReturn(choice)) {
            System.out.println("Билет возвращен!");
        } else System.out.println("Ошибка возврата билета");
    }


    @Override
    public void managerMenu(User user) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Менеджер " + user.getLogin() + ", добро пожаловть!\n");
        int n = 1;
        while (n != 0) {
            System.out.println("\nВведите:\n" +
                    "1 - для создания нового киносеанса и билетов на него,\n" +
                    "2 - для редактирования киносеансов,\n" +
                    "3 - для покупки билета пользователю,\n" +
                    "4 - для возврата билета пользователя,\n" +
                    "0 - для выхода из учетной записи.");

            String choice = sc.next();
            switch (choice) {
                case "1":
                    movieCreateMenu(); // меню создания киносеанса
                    break;
                case "2":
                    movieUpdateMenu();  // меню редактирования киносеансов
                    break;
                case "3":
                    userTicketBuyMenu();  // меню покупки билета пользователя
                    break;
                case "4":
                    userTicketGetBackMenu();  // меню возврата билета пользователя
                    break;
                case "0":
                    n = 0;
                default:
                    System.out.println("Команда введена неверно! Пожалуйста, попробуйте еще раз!");
            }
        }

    }

    void movieCreateMenu() {
        String title = "";
        while (title.equals("")) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите название фильма:");
            title = sc.nextLine();
            if (title.trim().length() < 3) {
                System.out.println("Название фильма должно содержать больше 3 символов,");
                title = "";
            }
        }
        System.out.println("Введите дату показа фильма в формате yyyy-mm-dd:");
        String[] date = sc.next().split("-");
        if (date.length != 3) {
            date = new String[3];
        }
        System.out.println("Введите время показа фильма в формате hh-mm");
        String[] time = sc.next().split("-");
        if (time.length != 2) {
            time = new String[2];
        }
        System.out.println("Введите количество билетов на сеанс (1-10):");
        int placeCapacity = -1;
        while (placeCapacity == -1) {
            Scanner sc = new Scanner(System.in);
            try {
                int num = sc.nextInt();
                if (num > 0 && num <= 10) {
                    placeCapacity = num;
                } else System.out.println("Число должно быть от 1 до 10, попробуйте еще раз");
            } catch (InputMismatchException e) {
                System.out.println("Количество билетов должно быть числом");
            }
        }
        System.out.println("Введите стоимость билета на сеанс");
        int ticketPrice = -1;
        while (ticketPrice == -1) {
            Scanner sc = new Scanner(System.in);
            try {
                int num = sc.nextInt();
                if (num > 0) {
                    ticketPrice = num;
                } else System.out.println("Число должно быть положительным, попробуйте еще раз");
            } catch (InputMismatchException e) {
                System.out.println("Количество билетов должно быть числом");
            }
        }
        Movie movie = null;
        if (sf.movieService.movieCreate(new Movie(title, date[0], date[1], date[2], time[0], time[1]))) {
            System.out.println("Киносеанс создан");
            movie = sf.movieService.getMovie(title, date[0], date[1], date[2], time[0], time[1]);
        }


        if (movie != null) {
            if (sf.ticketService.ticketsForMovieCreate(title, placeCapacity, ticketPrice, movie.getId())) {
                System.out.println("Билеты созданы");
            } else System.out.println("Ошибка создания билетов");
        }


    }

    void movieUpdateMenu() {
        System.out.println("Все будущие киносеансы: ");
        List<Movie> moviesList = sf.movieService.getNextMovies();
        System.out.println(moviesList.toString());
        System.out.println("Введите номер киносеанса для его изменения, либо 0 для возврата в предыдущее меню");
        int movieId = 0;
        while (movieId == 0) {
            Scanner sc = new Scanner(System.in);
            try {
                int n = sc.nextInt();
                for (Movie m : moviesList
                ) {
                    if (m.getId() == n) {
                        movieId = n;
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Команда должна быть числом");
            }
            if (movieId != 0) {
                String title = "";
                while (title.equals("")) {
                    Scanner sc1 = new Scanner(System.in);
                    System.out.println("Введите новое название фильма:");
                    title = sc1.next();
                    if (title.trim().length() < 3) {
                        System.out.println("Название фильма должно содержать больше 3 символов,");
                        title = "";
                    }
                }
                System.out.println("Введите новую дату показа фильма в формате yyyy-mm-dd:");
                String[] date = sc.next().split("-");
                if (date.length != 3) {
                    date = new String[3];
                }
                System.out.println("Введите новое время показа фильма в формате hh-mm");
                String[] time = sc.next().split("-");
                if (time.length != 2) {
                    time = new String[2];
                }
                if (sf.movieService.movieUpdate(movieId, new Movie(title, date[0], date[1], date[2], time[0], time[1]))) {
                    System.out.println("Киносеанс успешно изменен");
                }
            } else {
                System.out.println("Киносеанса с таким номером не существует, выберетие один из списка выше");
            }
        }
    }

    void userTicketGetBackMenu() {
        System.out.println("Введите логин пользователя, билет которого хотите вернуть:");
        int n = 0;
        Scanner sc = new Scanner(System.in);
        List<Ticket> list = null;
        while (n == 0) {
            String name = "";
            name = sc.next();
            list = sf.ticketService.getUserTickets(name);
            if (list.isEmpty()) {
                System.out.println("Неверный логин пользователя, либо у данного пользователя нет купленных билетов");
            } else {
                System.out.println(list);
                n++;
            }
        }

        System.out.println("Введите номер билета, который вы хотите вернуть:");
        int ticketId = -1;
        int num = 0;
        while (ticketId == -1) {
            Scanner sc1 = new Scanner(System.in);
            try {
                num = sc1.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Команда должна быть числом");
            }
            for (Ticket t : list
            ) {
                if (num == t.getId()) {
                    ticketId = num;
                    break;
                }
            }
            if (ticketId == -1) {
                System.out.println("Билета с таким номером в списке нет, введите номер билета из списка");
            }
        }
        if (sf.ticketService.ticketReturn(ticketId)) {
            System.out.println("Билет №" + ticketId + " возвращен");
        } else System.out.println("Ошибка возврата билета");
    }

    void userTicketBuyMenu() {
        System.out.println("Введите логин пользователя, билет которому хотите купить, или 0, чтобы" +
                " выйти в предыдущее меню:");
        Scanner sc = new Scanner(System.in);
        int n = 0;
        String name = "";
        while (n == 0) {
            name = "";
            name = sc.next();
            if (name.equals("0")) {
                n++;
            }
            User user = sf.userService.userRead(name);
            if (user == null) {
                System.out.println("Неверный логин пользователя, такой пользователь не зарегистрирован," +
                        " попробуйте еще раз");
            } else if (user.getAccess() != UserAccessLevel.USER) {
                System.out.println("Билеты можно покупать только пользователям, попробуйте еще раз");
            } else {
                n++;

            }
        }

        System.out.println("Введите номер билета, который вы хотите купить пользователю:");
        int ticketId = -1;
        while (ticketId == -1) {
            Scanner sc1 = new Scanner(System.in);
            try {
                int num = sc1.nextInt();
                if (num > 0) {
                    ticketId = num;
                } else System.out.println("Число должно быть положительным");
            } catch (InputMismatchException e) {
                System.err.println("Команда должна быть числом");
            }
            if (sf.ticketService.buyTicket(ticketId, name)) {
                System.out.println("Вы купили билет: " + sf.ticketService.readTicket(ticketId));
            } else {
                System.out.println("Ошибка покупки билета, попробуйте другой номер билета");
                ticketId = -1;
            }
        }

    }


    @Override
    public void adminMenu(User user) {
        System.out.println("Админ " + user.getLogin() + ", добро пожаловть!");
        int n = 1;
        while (n != 0) {
            System.out.println("\nВведите:\n" +
                    "1 - для удаления пользователя,\n" +
                    "2 - для редактирования пользователя,\n" +
                    "3 - для удаления киносеанса,\n" +
                    "4 - для редактирования киносеанса,\n" +
                    "0 - для выхода из учетой записи");

            Scanner sc = new Scanner(System.in);

            String choice = sc.next();
            switch (choice) {
                case "1":
                    userDeleteMenu();
                    break;
                case "2":
                    userUpdateMenu();
                    break;
                case "3":
                    movieDeleteMenu();
                    break;
                case "4":
                    movieUpdateMenu();
                    break;
                case "0":
                    n = 0;
                    break;
                default:
                    System.err.println("Команда введена неверно! Пожалуйста, попробуйте еще раз!");
            }
        }

    }

    private void movieDeleteMenu() {
        System.out.println("Введите № удаляемого киносеанса, либо 0 для возврата в предыдущее меню");
        List<Movie> list = sf.movieService.getNextMovies();
        System.out.println("Все будущие киносеансы: ");
        System.out.println(list.toString());
        int deleteNum = -1;
        while (deleteNum == -1) {
            Scanner sc1 = new Scanner(System.in);
            try {
                int num = sc1.nextInt();
                if (num >= 0) {
                    if (num == 0) {
                        break;
                    }
                    for (Movie m : list
                    ) {
                        if (m.getId() == num) {
                            deleteNum = num;
                        }
                    }
                }
                if (deleteNum > 0) {
                    if (sf.movieService.movieDelete(deleteNum)) {
                        sf.ticketService.deleteTicketsOnMovie(deleteNum);
                        System.out.println("Киносеанс №" + deleteNum + " удален");
                    } else System.out.println("Ошибка удаления");
                } else System.out.println("Киносеанса с таким немером нет в базе");
            } catch (InputMismatchException e) {
                System.out.println("Команда должна быть числом");
            }
        }
    }

    private void userUpdateMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите логин редактируемого пользователя");
        String login = sc.next();
        User user1 = sf.userService.userRead(login);
        if (user1 == null) {
            System.out.println("Пользователь с таким логином не зарегистрирован");
        } else if (user1.getAccess() != UserAccessLevel.USER) {
            System.out.println("Редактировать менеджера или админа нельзя");
        } else {
            System.out.println("Введите новый логин пользователя");
            String newLogin = sc.next();
            System.out.println("Введите новый пароль пользователя");
            String pass = sc.next();
            if (sf.userService.userUpdate(login, new User(newLogin, pass, UserAccessLevel.USER))) {
                System.out.println("Пользователь " + login + " изменен");
            }
            ;
        }
    }

    private void userDeleteMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите логин удаляемого пользователя:");
        String userLogin = sc.next();
        User userDelete = sf.userService.userDelete(userLogin);
        if (userDelete != null) {
            System.out.println("Пользователь " + userDelete.getLogin() + " удален");
        } else System.out.println("Пользователь с таким логином не зарегистрирован," +
                " либо это менеджер или админ");
        return;
    }
}
