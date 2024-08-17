package com.techpoint.daos;

import com.techpoint.entities.Reaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ReactionDao {

    private final Connection con;

    public ReactionDao(Connection con) {
        this.con = con;
    }

    public Reaction saveReaction(int userId, int postId, Reaction.ReactionType type) {
        String query = "INSERT INTO Reactions (USER_ID, POST_ID, TYPE) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, postId);
            pstmt.setString(3, type.name()); // Store the name of the enum value

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return new Reaction(userId, postId, type); // Return a Reaction object
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Use a logger for production code
        }
        return null; // No reaction saved
    }

    public int countReactionsByType(int pId, Reaction.ReactionType type) {
        int count = 0;
        String query = "SELECT COUNT(*) FROM Reactions WHERE POST_ID=? AND TYPE=?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, pId);
            pstmt.setString(2, type.name()); // Store the name of the enum value

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1); // Retrieve the count from the first column of the result set
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Use a logger for production code
        }

        return count;
    }

    public Reaction deleteReaction(int userId, int postId) {
        Reaction.ReactionType reactionType = null;
        String queryFetch = "SELECT TYPE FROM Reactions WHERE USER_ID = ? AND POST_ID = ?";
        String queryDelete = "DELETE FROM Reactions WHERE USER_ID = ? AND POST_ID = ?";

        try (PreparedStatement pstmtFetch = con.prepareStatement(queryFetch);
             PreparedStatement pstmtDelete = con.prepareStatement(queryDelete)) {

            pstmtFetch.setInt(1, userId);
            pstmtFetch.setInt(2, postId);

            try (ResultSet rs = pstmtFetch.executeQuery()) {
                if (rs.next()) {
                    reactionType = Reaction.ReactionType.valueOf(rs.getString("TYPE")); // Convert the string to enum
                }
            }

            pstmtDelete.setInt(1, userId);
            pstmtDelete.setInt(2, postId);
            int rowsAffected = pstmtDelete.executeUpdate();

            if (rowsAffected > 0 && reactionType != null) {
                return new Reaction(userId, postId, reactionType); // Return a Reaction object
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting reaction: " + e.getMessage());
            e.printStackTrace(); // Use a logger for production code
        }
        return null; // No reaction deleted
    }

    public Reaction fetchTypeForCurrentUser(int userId, int postId) {
        String query = "SELECT * FROM Reactions WHERE USER_ID=? AND POST_ID=?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, postId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Reaction(userId, postId, Reaction.ReactionType.valueOf(rs.getString("TYPE"))); // Convert the string to enum
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Use a logger for production code
        }
        return null; // No reaction found
    }
}
