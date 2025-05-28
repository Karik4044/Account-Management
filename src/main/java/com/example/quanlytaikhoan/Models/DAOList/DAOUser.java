package com.example.quanlytaikhoan.Models.DAOList;

import com.example.quanlytaikhoan.Database.DataDB;
import com.example.quanlytaikhoan.Models.Omodels.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;

public class DAOUser {
    //Hien thi danh sach User
    public ObservableList<User> getUserlist() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try (Connection conn = DataDB.connect()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM ACCOUNTUSER");
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("passwords"), rs.getString("mail"), rs.getString("dates")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    //Hien thi so luong tai khoan User
    public int getUserCount() {
        int i = 0;
        try (Connection conn = DataDB.connect()) {
            String query = "SELECT COUNT(*) AS count FROM ACCOUNTUSER";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    i = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    //Hien thi so luong tai khoan User co thong tin
    public int getUserWithInfoCount() {
        int i = 0;
        try (Connection conn = DataDB.connect()) {
            String query = "SELECT COUNT(DISTINCT AccountId) AS count FROM USERINFO";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    i = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    //Hien thi tai khoan User recent
    public ObservableList<User> getRecentUser() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try (Connection conn = DataDB.connect()) {
            String query = "SELECT username, Dates FROM ACCOUNTUSER ORDER BY id DESC LIMIT 5";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Timestamp timestamp = rs.getTimestamp("Dates");
                    String dateTimeStr = timestamp != null ?
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp) : "N/A";
                    users.add(new User(rs.getString("username"), dateTimeStr));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public ObservableList<User> getuserprofile() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try (Connection conn = DataDB.connect()) {
            String query = "SELECT id, username, passwords, mail, dates, info FROM ACCOUNTUSER";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    users.add(new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("passwords"),
                            rs.getString("mail"),
                            rs.getString("dates")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public boolean insertUser(String id, String username, String password, String mail) {
        try (Connection conn = DataDB.connect() ) {
            String query = "SELECT * FROM ACCOUNTUSER WHERE username = ? OR mail = ? OR id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, mail);
                stmt.setString(3,id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String existingUsername = rs.getString("username");
                        String existingEmail = rs.getString("mail");
                        if (existingUsername.equals(username) || existingEmail.equals(mail)) {
                            return false;
                        }
                    }
                }

            }
            String insertQuery = "INSERT INTO ACCOUNTUSER (id, username, passwords, mail, dates) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                stmt.setString(1, id);
                stmt.setString(2, username);
                stmt.setString(3, password);
                stmt.setString(4, mail);
                stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis())); // Set current timestamp
                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(String id, String username, String password, String mail) {
        try(Connection con = DataDB.connect()) {
            String CurrentQuery = "UPDATE ACCOUNTUSER SET username = ?, passwords = ?, mail = ? WHERE id = ?";
            try (PreparedStatement stmtt = con.prepareStatement(CurrentQuery)) {
                stmtt.setString(1, username);
                stmtt.setString(2, password);
                stmtt.setString(3, mail);
                stmtt.setString(4, id);
                int rowsAffected = stmtt.executeUpdate();

                if (rowsAffected > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean deleteUser(String id) {
        try(Connection con = DataDB.connect()) {
            String CheckQuery = "DELETE FROM ACCOUNTUSER WHERE id = ?";
            try (PreparedStatement stmtt = con.prepareStatement(CheckQuery)) {
                stmtt.setString(1, id);
                int rowsAffected = stmtt.executeUpdate();

                if (rowsAffected > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ObservableList<User> search(String text) {
        ObservableList<User> results = FXCollections.observableArrayList(); // Initialize results outside the try block
        try (Connection conn = DataDB.connect()) {
            String query = "SELECT * FROM ACCOUNTUSER WHERE id LIKE ? OR username LIKE ? OR mail LIKE ?";
            try (PreparedStatement stpr = conn.prepareStatement(query)) {
                stpr.setString(1, text);
                stpr.setString(2, text);
                stpr.setString(3, text);
                try (ResultSet rs = stpr.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String username = rs.getString("username");
                        String password = rs.getString("passwords");
                        String mail = rs.getString("mail");
                        String date = rs.getString("dates");
                        results.add(new User(id, username, password, mail, date));
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Error executing search query", ex);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
        return results; // Return the results list
    }
}
