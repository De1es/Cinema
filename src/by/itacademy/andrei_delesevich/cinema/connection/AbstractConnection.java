package by.itacademy.andrei_delesevich.cinema.connection;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

import java.sql.*;


public abstract class AbstractConnection {

    private  static final String URL = "jdbc:mysql://127.0.0.1:3334/cinema";
    private  static final String USERNAME = "root";
    private  static final String PASSWORD = "";
    private  static final String DRIVER = "com.mysql.jdbc.Driver";

    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver not found", e);
        }

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return conn;
        }catch (CommunicationsException e){
            System.err.println("Ошибка подключения к БД");
            return conn;
        }
    }

    public static void closeConnection(Connection connection) throws SQLException{
        if (connection != null) {
                connection.close();
        }
    }


}
