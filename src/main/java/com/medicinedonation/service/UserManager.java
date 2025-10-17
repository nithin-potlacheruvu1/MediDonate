package com.medicinedonation.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import com.medicinedonation.dao.UserDAO;
import com.medicinedonation.daoImpl.UserDAOImpl;
import com.medicinedonation.db.DBUtil;
import com.medicinedonation.model.User;

public class UserManager {
    private UserDAO userDAO = new UserDAOImpl();

    public void addUser(User user) throws Exception {
        userDAO.insertUser(user);
    }

    public void updateUser(User user) throws Exception {
        userDAO.updateUser(user);
    }

    public void removeUser(int userId) throws Exception {
        userDAO.deleteUser(userId);
    }

    public User getUser(int userId) throws Exception {
        return userDAO.getUserById(userId);
    }

    public User getUserByUsername(String username) throws Exception {
        return userDAO.getUserByUsername(username);
    }

    public List<User> getAllUsers() throws Exception {
    	 String BASE_URL =
                "http://localhost:9999/medidonate-rest/webresources/myresource";
    	 List<User> users = new ArrayList<>();
         try {
             Client client = ClientBuilder.newClient();

             // REST endpoint
             WebTarget target = client.target(BASE_URL);
             System.out.println("Calling REST endpoint: " + target.getUri());

             // Optional: debug print raw JSON
             String jsonResponse = target.request(MediaType.APPLICATION_JSON).get(String.class);
             System.out.println("Raw JSON from REST API: " + jsonResponse);

             // Actual deserialization into list
             users = target.request(MediaType.APPLICATION_JSON)
                           .get(new GenericType<List<User>>() {});

             client.close();
         } catch (Exception e) {
             e.printStackTrace();
         }
         return users;
    }
    public boolean changePassword(int userId, String currentPassword, String newPassword) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            
            // First verify the current password
            String verifySql = "SELECT password FROM users WHERE user_id = ?";
            pstmt = conn.prepareStatement(verifySql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                
                // Verify current password
                if (!storedPassword.equals(currentPassword)) {
                    return false; // Current password doesn't match
                }
                
                // Close previous resources
                rs.close();
                pstmt.close();
                
                // Update to new password
                String updateSql = "UPDATE users SET password = ? WHERE user_id = ?";
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setString(1, newPassword);
                pstmt.setInt(2, userId);
                
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            }
            
            return false; // User not found
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		return false;
    }
 // Add this method to UserManager class
    public boolean updateUserProfile(User user) {
        String sql = "UPDATE users SET email = ?, phone = ?, address = ? WHERE user_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPhone());
            stmt.setString(3, user.getAddress());
            stmt.setInt(4, user.getUserId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
    }
    

}
