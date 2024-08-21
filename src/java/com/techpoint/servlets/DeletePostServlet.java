package com.techpoint.servlets;

import com.techpoint.daos.PostDao;
import com.techpoint.helper.PgmDbConnector;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

public class DeletePostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain"); // Set content type to text/plain

        String postIdParam = request.getParameter("postId");
        if (postIdParam == null || postIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendResponse(response, "Post ID is required.");
            return;
        }

        int postId = Integer.parseInt(postIdParam);

        try (Connection con = PgmDbConnector.makeConnection()) {
            PostDao dao = new PostDao(con);
            boolean isSuccess = dao.deletePostByPid(postId);

            if (isSuccess) {
                sendResponse(response, "Deleted");
            } else {
                sendResponse(response, "Delete failed.");
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sendResponse(response, "Database error: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sendResponse(response, "Internal server error: " + e.getMessage());
        }
    }

    private void sendResponse(HttpServletResponse response, String message) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println(message);
        }
    }
}
