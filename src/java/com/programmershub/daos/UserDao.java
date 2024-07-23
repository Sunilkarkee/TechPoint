package com.programmershub.daos;



import com.programmershub.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;



public class UserDao {

   
    private final Connection con;

    public UserDao(Connection con) {
        this.con = con;
    }

    // Method to insert user into database
    public boolean saveUser(User user) throws SQLException {
        String query = "INSERT INTO users (NAME, EMAIL, PASSWORD, PHONE_NUMBER, GENDER, ABOUT, PROFILE) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return executeUpdate(query, user.getName(), user.getEmail(), user.getPassword(),
                user.getPhone_number(), user.getGender(), user.getAbout(), user.getProfile());
    }

    // Method to update user information
    public boolean updateUser(User user) throws SQLException {
        String query = "UPDATE users SET NAME=?, EMAIL=?, PASSWORD=?, GENDER=?, PHONE_NUMBER=?, ABOUT=?, PROFILE=?, Updated_At=? WHERE ID=?";
        return executeUpdate(query, user.getName(), user.getEmail(), user.getPassword(),
                user.getGender(), user.getPhone_number(), user.getAbout(), user.getProfile(),
                new Timestamp(System.currentTimeMillis()), user.getId());
    }

    // Method to delete user by ID
    public boolean deleteUser(int userId) throws SQLException {
        String query = "DELETE FROM users WHERE ID=?";
        return executeUpdate(query, userId);
    }

    // Method to get user by ID
    public User getUserById(int userId) {
        String query = "SELECT * FROM users WHERE ID=?";
        return getUser(query, userId);
    }

    // Method to get all users
    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        return getUsers(query);
    }

    // Method to get user by email and password
    public User getUserByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM users WHERE EMAIL=? AND PASSWORD=?";
        return getUser(query, email, password);
    }

    // Method to get user by phone number and password
    public User getUserByPhoneNumberAndPassword(String phoneNumber, String password) {
        String query = "SELECT * FROM users WHERE PHONE_NUMBER=? AND PASSWORD=?";
        return getUser(query, phoneNumber, password);
    }
    
    // Method to get user by phone or email and password
    public User getUserByPhoneOrEmailAndPassword(String identifier, String password) {
        String query = "SELECT * FROM users WHERE (EMAIL=? OR PHONE_NUMBER=?) AND PASSWORD=?";
        return getUser(query, identifier, identifier, password);
    }

    // Helper method to execute an update (insert, update, delete) query
    private boolean executeUpdate(String query, Object... params) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            setParameters(pstmt, params);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            String sqlState = e.getSQLState();
            int errorCode = e.getErrorCode();
            String message = e.getMessage();
            
            // Check for duplicate entry error
            if ("23000".equals(sqlState) && errorCode == 1062) {
                if (message.contains("EMAIL")) {
                    throw new SQLException("Duplicate entry for email.", e);
                } else if (message.contains("unique_phone_number")) {
                    throw new SQLException("Duplicate entry for phone number......", e);
                } else {
                    throw new SQLException("Duplicate entry.", e);
                }
            } else {
                
                throw e;
            }
        }
    }

    // Helper method to get a single user
    private User getUser(String query, Object... params) {
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            setParameters(pstmt, params);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Helper method to get multiple users
    private List<User> getUsers(String query, Object... params) {
        List<User> userList = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            setParameters(pstmt, params);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                userList.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // Helper method to set parameters for PreparedStatement
    private void setParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }

    // Helper method to map ResultSet to User object
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("ID")); 
        user.setName(rs.getString("NAME")); 
        user.setEmail(rs.getString("EMAIL")); 
        user.setPassword(rs.getString("PASSWORD")); 
        user.setGender(rs.getString("GENDER")); 
        user.setPhone_number(rs.getString("PHONE_NUMBER")); 
        user.setAbout(rs.getString("ABOUT")); 
        user.setProfile(rs.getString("PROFILE")); 
        user.setCreated_at(rs.getTimestamp("created_at")); 
        user.setUpdated_at(rs.getTimestamp("Updated_At")); 
        return user;
    }
}
