package com.example.quanlytaikhoan.Models.DAOList;

import com.example.quanlytaikhoan.Database.DataDB;
import com.example.quanlytaikhoan.Models.Omodels.Profile;
import java.sql.*;

import java.sql.ResultSet;

public class DAOProfile {
    //Update user information
    public boolean updateUserInfo(int id, int accountId, String nameuser, String gender, String tele, String address) {
        String sql = "REPLACE INTO USERINFO (Id, AccountId, Nameuser, Gender, Tele, Address) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataDB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, accountId);
            stmt.setString(3, nameuser);
            stmt.setString(4, gender);
            stmt.setString(5, tele);
            stmt.setString(6, address);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // This method retrieves a user's profile based on the account ID
    public Profile getProfileByAccountId(int accountId) {
        String sql = "SELECT * FROM USERINFO WHERE AccountId = ?";
        try (Connection conn = DataDB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Profile(
                    rs.getInt("Id"),
                    rs.getInt("AccountId"),
                    rs.getString("Nameuser"),
                    rs.getString("Gender"),
                    rs.getString("Tele"),
                    rs.getString("Address")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Delete user information
    public boolean deleteUserInfo(int accountId) {
        String sql = "DELETE FROM USERINFO WHERE AccountId = ?";
        try (Connection conn = DataDB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}