package com.example.quanlytaikhoan.Database;

import java.sql.*;

//Kết nối với database qua JDBC
public class DataDB {
    private static Connection connection;

    public static Connection connect() {
        try {
            // Kết nối MySQL
            String url = "jdbc:mysql://localhost:3306/USERLIST";
            String username = "root";  // Tên người dùng MySQL
            String password = "VKvk0974228645kc";      // Mật khẩu MySQL
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        connect();
        close();
    }
}
