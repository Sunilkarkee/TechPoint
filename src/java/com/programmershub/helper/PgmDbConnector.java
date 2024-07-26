
package com.programmershub.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PgmDbConnector {
     
    private static final String DBURL = "jdbc:mysql://localhost:3306/programmershub";
    private static final String USERNAME = "root";
    private static final String USERPASSWORD = "1998";
    private static Connection con;
    
    public static Connection makeConnection() {
        try {
            if (con == null || con.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(DBURL, USERNAME, USERPASSWORD);
            }
        } catch (ClassNotFoundException | SQLException e) {
           
            throw new RuntimeException("Failed to obtain database connection.");
        }
        return con;
    }
}

