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
import java.util.logging.Level;
import java.util.logging.Logger;

@MultipartConfig
public class AddPostServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "C:\\Users\\Suneel\\Desktop\\JAVA PROJECTS\\ProgrammersHub\\web\\postpics";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Retrieve form parameters
            int catId = Integer.parseInt(request.getParameter("catId"));
            String pTitle = request.getParameter("title");
            String pContent = request.getParameter("content");
            String pCode = request.getParameter("code");

            Part part = request.getPart("pic");
            String pPicName = ImageHandler.processImage(part, UPLOAD_DIRECTORY); // Use the helper method

            if (pPicName == null) {
                out.println("Failed to save the image");
                return;
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
                    out.println("Post added successfully");
                } else {
                    out.println("Failed to add post");
                }
            } catch (SQLException ex) {
                Logger.getLogger(AddPostServlet.class.getName()).log(Level.SEVERE, null, ex);
                out.println("An error occurred while saving the post: " + ex.getMessage());
            }
        } catch (Exception e) {
            Logger.getLogger(AddPostServlet.class.getName()).log(Level.SEVERE, null, e);
            response.getWriter().println("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet to add a new post";
    }
}
