package com.programmershub.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.HttpSession;
import com.programmershub.entities.Post;
import com.programmershub.entities.User;
import com.programmershub.daos.PostDao;
import com.programmershub.helper.PgmDbConnector;
import com.programmershub.helper.ImageHandler;
import java.sql.Connection;
import java.sql.SQLException;

@MultipartConfig
public class AddPostServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "C:\\Users\\Suneel\\Desktop\\JAVA PROJECTS\\ProgrammersHub\\web\\postpics";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Retrieve form parameters
            String catIdStr = request.getParameter("catId");
            String pTitle = request.getParameter("title");
            String pContent = request.getParameter("content");
            String pCode = request.getParameter("code");

            int catId = 0;
            try {
                catId = Integer.parseInt(catIdStr);
            } catch (NumberFormatException e) {
                sendResponse(response, "Invalid category ID");
                return;
            }

            Part part = request.getPart("pic");
            String pPicName = null;
            if (part != null && part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
                pPicName = ImageHandler.processImage(part, UPLOAD_DIRECTORY);
                if (pPicName == null) {
                    sendResponse(response, "Failed to save the image");
                    return;
                }
            }

            // Getting current user Id from session
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("currentUser");
            int userId = user != null ? user.getId() : 0;

            // Create Post object with constructor
            Post post = new Post(pTitle, pContent, pCode, pPicName, catId, userId);

            try (Connection con = PgmDbConnector.makeConnection()) {
                PostDao postDao = new PostDao(con);

                if (postDao.savePost(post)) {
                    sendResponse(response, "Post added successfully");
                } else {
                    sendResponse(response, "Failed to add post");
                }
            } catch (SQLException ex) {
                sendResponse(response, "An error occurred while saving the post: " + ex.getMessage());
            }
        } catch (Exception e) {
            sendResponse(response, "An error occurred: " + e.getMessage());
        }
    }

    // Helper method to send response
    private void sendResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(message);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet to add a new post";
    }
}
