
package com.programmershub.daos;




import com.programmershub.entities.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDao {
    private Connection con;

    public PostDao(Connection con) {
        this.con = con;
    }

    // Method to map ResultSet to Category
    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        int cid = rs.getInt("cid");
        String name = rs.getString("name");
        String description = rs.getString("description");
        String photo = rs.getString("photo");
        

        Category category = new Category(cid, name, description, photo);
        

        return category;
    }

    // Method to get all categories
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories"; 

        try (PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Category category = mapResultSetToCategory(rs);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
}

