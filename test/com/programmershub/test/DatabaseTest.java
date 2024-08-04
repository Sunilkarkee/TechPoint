package com.programmershub.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseTest {

    // Database connection details
    private static final String DBURL = "jdbc:mysql://localhost:3306/programmershub";
    private static final String USERNAME = "root";
    private static final String USERPASSWORD = "1998";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(DBURL, USERNAME, USERPASSWORD);
             PreparedStatement pstmt = con.prepareStatement("SELECT * FROM posts");
             ResultSet rs = pstmt.executeQuery()) {

            // Iterate through the result set and print post titles
            while (rs.next()) {
                System.out.println("Post Title: " + rs.getString("PTITLE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
