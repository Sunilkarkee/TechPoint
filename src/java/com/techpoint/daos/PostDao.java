package com.techpoint.daos;

import com.techpoint.entities.Category;
import com.techpoint.entities.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PostDao {

    private Connection con;

    public PostDao(Connection con) {
        this.con = con;
    }

    // Map ResultSet to Category
    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        int cid = rs.getInt("CID");
        String name = rs.getString("NAME");
        String description = rs.getString("DESCRIPTION");
        String photo = rs.getString("PHOTO");

        return new Category(cid, name, description, photo);
    }

    // Get all categories
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";

        try (PreparedStatement pstmt = con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                categories.add(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    // Save a post
    public boolean savePost(Post p) {
        String query = "INSERT INTO posts (PTITLE, PCONTENT, PPIC, CID, USER_ID) VALUES (?, ?, ?, ?, ?)";
        return executePostsUpdate(query, p.getPTitle(), p.getPContent(), p.getPPic(), p.getCatId(), p.getUserId());
    }

    // Execute post updates (insert, update, delete)
    private boolean executePostsUpdate(String query, Object... params) {
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            setPostParameters(pstmt, params);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all posts
    public List<Post> getAllPosts() {
        List<Post> pList = new ArrayList<>();
        String query = "SELECT * FROM posts";

        try (PreparedStatement pstmt = con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                pList.add(mapResultSetToPost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pList;
    }

    // Get posts by category ID
    public List<Post> getPostsByCategoryId(int categoryId) {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM posts WHERE CID = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    posts.add(mapResultSetToPost(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    // Get a post by post ID
    public Post getPostByPostId(int postId) {
        Post post = null;
        String query = "SELECT * FROM posts WHERE ID = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, postId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    post = mapResultSetToPost(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }

    // Get all posts by user ID
    public List<Post> getPostByUserId(int userId) {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM posts WHERE USER_ID = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    posts.add(mapResultSetToPost(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    // Delete a post by user
    public boolean deletePostByUser(int postId, int userId) {
        String query = "DELETE FROM posts WHERE ID = ? AND USER_ID = ?";
        return executePostsUpdate(query, postId, userId);
    }

    // Update a post
    public boolean updatePost(Post post) {
        String query = "UPDATE posts SET PTITLE = ?, PCONTENT = ?, PPIC = ?, CID = ? WHERE ID = ? AND USER_ID = ?";
        return executePostsUpdate(query, post.getPTitle(), post.getPContent(), post.getPPic(), post.getCatId(), post.getPId(), post.getUserId());
    }

    // Get post count by category ID
    public int countPostsByCategoryId(int categoryId) {
        return countPosts("CID = ?", categoryId);
    }

    // Get post count by user ID
    public int countPostsByUserId(int userId) {
        return countPosts("USER_ID = ?", userId);
    }

    // Get total post count
    public int countAllPost() {
        return countPosts(null);
    }

    // Helper method to count posts
   private int countPosts(String condition, Object... params) {
    int count = 0;
    String query = "SELECT COUNT(*) FROM posts" + (condition != null ? " WHERE " + condition : "");

    try (PreparedStatement pstmt = con.prepareStatement(query)) {
        setPostParameters(pstmt, params);  // Use the helper method to set parameters
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return count;
}


    // Helper method to set parameters for PreparedStatement
    private void setPostParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }

    // Map ResultSet to Post object
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
