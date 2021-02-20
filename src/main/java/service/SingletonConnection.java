package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection {
    private static Connection connection;

    public static Connection getConnection() {
        if(connection == null){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/productmanager1",
                        "root",
                        "123456"
                );
            } catch (ClassNotFoundException e) {
                System.out.println("không có driver");
            } catch (SQLException throwables) {
                System.out.println("Không kết nối được");
            }
            System.out.println("ket noi thanh cong");
        }
        return connection;
    }

}