package com.programmershub.daos;

import com.programmershub.entities.Category;
import com.programmershub.entities.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PostDao {

    private Connection con = null;

    public PostDao(Connection con) {
        this.con = con;
    }

    // Method to map ResultSet to Category
    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        int cid = rs.getInt("CID");
        String name = rs.getString("NAME");
        String description = rs.getString("DESCRIPTION");
        String photo = rs.getString("PHOTO");

        return new Category(cid, name, description, photo);
    }

    // Method to get all categories
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM CATEGORIES";

        try (PreparedStatement pstmt = con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Category category = mapResultSetToCategory(rs);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    //all methods below for posts
    public boolean savePost(Post p) throws SQLException {
        String query = "INSERT INTO posts(PTITLE, PCONTENT, PPIC, CID, USER_ID) VALUES (?, ?, ?, ?, ?)";
        return executePostsUpdate(query, p.getPTitle(), p.getPContent(), p.getPPic(), p.getCatId(), p.getUserId());
    }

    private boolean executePostsUpdate(String query, String pTitle, String pContent, String pPic, int catId, int userId) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            setpostParameters(pstmt, pTitle, pContent, pPic, catId, userId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //method to get all post
    public List<Post> getAllPosts() {
        List<Post> pList = new ArrayList<>();

        String query = "SELECT * FROM posts";

        try (PreparedStatement pstmt = con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Post post = mapResultSetToPost(rs);
                pList.add(post);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pList;
    }

    //method to get post by categories id
    public List<Post> getPostsByCategoryId(int categoryId) {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM posts WHERE CID = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Post post = mapResultSetToPost(rs);
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

//    private void setParameters(PreparedStatement pstmt, String pTitle, String pContent, String pPic, int catId, int userId) throws SQLException {
//        pstmt.setString(1, pTitle);
//        pstmt.setString(2, pContent);
//        pstmt.setString(3, pPic);
//        pstmt.setInt(4, catId);
//        pstmt.setInt(5, userId);
//    }
    //above method using for loop
    private void setpostParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }

    // Helper method to map ResultSet to Post object
    private Post mapResultSetToPost(ResultSet rs) throws SQLException {
        int pId = rs.getInt("ID");
        String pTitle = rs.getString("PTITLE");
        String pContent = rs.getString("PCONTENT");
        String pCode = rs.getString("PCODE");
        String pPic = rs.getString("PPIC");
        Timestamp pDate = rs.getTimestamp("REG_DATE");
        int catId = rs.getInt("CID");
        int userId = rs.getInt("USER_ID");

        return new Post(pId, pTitle, pContent, pCode, pPic, pDate, catId, userId);
    }
}
