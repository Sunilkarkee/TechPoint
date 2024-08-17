package com.techpoint.servlets;

import com.techpoint.daos.ReactionDao;
import com.techpoint.entities.Reaction;
import com.techpoint.entities.Reaction.ReactionType;
import com.techpoint.helper.PgmDbConnector;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReactionServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ReactionServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String typeStr = request.getParameter("operation").toUpperCase();
            ReactionType type;

            try {
                type = ReactionType.valueOf(typeStr);
            } catch (IllegalArgumentException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Invalid reaction type: " + typeStr + "\"}");
                return;
            }

            int uId = Integer.parseInt(request.getParameter("uid"));
            int pId = Integer.parseInt(request.getParameter("pid"));

            try (Connection con = PgmDbConnector.makeConnection()) {
                ReactionDao rd = new ReactionDao(con);
                Reaction existingReaction = rd.fetchTypeForCurrentUser(uId, pId);

                if (existingReaction != null) {
                    ReactionType existingType = existingReaction.getType();

                    if (existingType == type) {
                        // Undo the reaction if the same reaction is clicked again
                        rd.deleteReaction(uId, pId);
                    } else {
                        // Switch the reaction
                        rd.deleteReaction(uId, pId);
                        rd.saveReaction(uId, pId, type);
                    }
                } else {
                    // Save new reaction
                    rd.saveReaction(uId, pId, type);
                }

                // Get updated reaction counts
                int likeCount = rd.countReactionsByType(pId, ReactionType.LIKE);
                int dislikeCount = rd.countReactionsByType(pId, ReactionType.DISLIKE);

                // Return updated counts as JSON
                out.print("{\"likeCount\":" + likeCount + ", \"dislikeCount\":" + dislikeCount + "}");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error processing reaction", e);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"error\":\"An error occurred: " + e.getMessage() + "\"}");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles reactions for posts";
    }
}
