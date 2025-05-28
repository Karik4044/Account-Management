package com.example.quanlytaikhoan.Models.DAOList;

import com.example.quanlytaikhoan.Database.DataDB;
import com.example.quanlytaikhoan.Models.Omodels.Admin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Lớp này dùng để quản lý tài khoản Admin bằng việc kết nối với database thông qua các thao tác CRUD
public class DAOAdmin {
    public ObservableList<Admin> getAdminList() {
        ObservableList<Admin> admins = FXCollections.observableArrayList();
        try (Connection conn = DataDB.connect()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM ADMINUSER");
            while (rs.next()) {
                admins.add(new Admin(rs.getInt("id"), rs.getString("username"), rs.getString("passwords"), rs.getString("mail")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return admins;
    }

    public boolean registerUser(String username, String password, String mail) {
        try (Connection conn = DataDB.connect() ) {
            String query = "SELECT * FROM ADMINUSER WHERE username = ? OR mail = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, mail);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        if (rs.getInt(1) > 0 || rs.getInt(2) > 0) {
                            return false;
                        }
                    }
                }
            }

            String insertQuery = "INSERT INTO ADMINUSER (username, passwords, mail) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, mail);
                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        try (Connection conn = DataDB.connect()) {
            String query = "SELECT * FROM ADMINUSER WHERE username = ? AND passwords = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
            return false;
        }
    }

    public boolean sendcode(String mail) {
        try (Connection conn = DataDB.connect() ) {
            String query = "SELECT * FROM ADMINUSER WHERE mail = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, mail);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean changePassword(String password, String mail) {
        try(Connection conn = DataDB.connect()) {
            String query = "UPDATE ADMINUSER SET passwords = ? WHERE mail = ?";
            try(PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, password);
                stmt.setString(2, mail);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    return true; //Khi thay doi thi hien ra pane thong bao da cap nhat
                }
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return false;
    }
}
