package com.techpoint.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dbconnector {

    private static final String URL = "jdbc:mysql://localhost:3306/TechPointDb";
    private static final String USER = "root";
    private static final String PASSWORD = "root24";

    public static Connection getConnection() throws SQLException {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String fetchTypeForCurrentUser(Connection con, int userId, int postId) {
        String savedType = null;
        String query = "SELECT * FROM Reactions WHERE USER_ID = ? AND POST_ID = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, postId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                savedType = rs.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        return savedType;
    }

    public static void main(String[] args) {
        try (Connection conn = Dbconnector.getConnection()) {
            System.out.println("Connection successful!");
            // Print connection URL, user name, and connection status
            System.out.println("Connection URL: " + URL);
            System.out.println("Connection User: " + USER);
            System.out.println("Connection Status: " + (conn.isClosed() ? "Closed" : "Open"));

            // You can also print the connection's metadata
            System.out.println("Database Product Name: " + conn.getMetaData().getDatabaseProductName());
            System.out.println("Database Product Version: " + conn.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver Name: " + conn.getMetaData().getDriverName());
            System.out.println("Driver Version: " + conn.getMetaData().getDriverVersion());
            
            // Example usage of fetchTypeForCurrentUser
            int userId = 1; // Replace with the actual user ID
            int postId = 1; // Replace with the actual post ID
            String reactionType = fetchTypeForCurrentUser(conn, userId, postId);
            System.out.println("Reaction type: " + reactionType);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
