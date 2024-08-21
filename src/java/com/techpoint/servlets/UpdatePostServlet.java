package com.techpoint.servlets;

import com.techpoint.daos.PostDao;
import com.techpoint.entities.Post;
import com.techpoint.helper.PgmDbConnector;
import com.techpoint.helper.ImageHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@MultipartConfig
public class UpdatePostServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "/home/cosmic/Desktop/JavaProjects/TechPoint/web/blogsPics";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        if (request.getContentType() == null || !request.getContentType().contains("multipart/form-data")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendResponse(response, "Request must be of type multipart/form-data");
            return;
        }

        // Retrieve and validate form parameters
        String userIdParam = request.getParameter("userId");
        if (userIdParam == null || userIdParam.isEmpty()) {
            sendResponse(response, "User ID is required.");
            return;
        }
        int userId = Integer.parseInt(userIdParam);

        String postIdParam = request.getParameter("postId");
        if (postIdParam == null || postIdParam.isEmpty()) {
            sendResponse(response, "Post ID is required.");
            return;
        }
        int postId = Integer.parseInt(postIdParam);

        String catIdParam = request.getParameter("catId");
        if (catIdParam == null || catIdParam.isEmpty()) {
            sendResponse(response, "Category ID is required.");
            return;
        }
        int catId = Integer.parseInt(catIdParam);

        String title = request.getParameter("title");
        if (title == null || title.isEmpty()) {
            sendResponse(response, "Title is required.");
            return;
        }

        String content = request.getParameter("content");
        if (content == null || content.isEmpty()) {
            sendResponse(response, "Content is required.");
            return;
        }

        String code = request.getParameter("code");

        // Create or update Post object
        Post post = new Post();
        post.setPId(postId);
        post.setUserId(userId);
        post.setCatId(catId);
        post.setPTitle(title);
        post.setPContent(content);
        post.setPCode(code);

        // Handle file upload
        Part filePart = request.getPart("pic");
        String oldImageName = request.getParameter("oldPic"); // Get the old image name from the request parameter

        if (filePart != null && filePart.getSize() > 0) { // Check if a new file was uploaded
            String newImageName = ImageHandler.processImage(filePart, UPLOAD_DIRECTORY); // Use the helper method
            post.setPPic(newImageName);

            // Delete the old image file if a new image is uploaded and is different
            if (oldImageName != null && !oldImageName.isEmpty() && !oldImageName.equals(newImageName)) {
                String oldImagePath = UPLOAD_DIRECTORY + File.separator + oldImageName;
                ImageHandler.deleteFile(oldImagePath); // Delete the old image file
            }
        } else {
            // No new image uploaded, keep the old image
            post.setPPic(oldImageName);
        }

        try (Connection con = PgmDbConnector.makeConnection()) {
            PostDao dao = new PostDao(con);
            boolean isSuccess = dao.updatePost(post);

            if (isSuccess) {
                sendResponse(response, "Update successful.");
            } else {
                sendResponse(response, "Update failed.");
            }
        } catch (SQLException e) {
            handleSQLException(response, e);
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

    private void handleSQLException(HttpServletResponse response, SQLException e) throws IOException {
        if (e.getMessage().contains("Duplicate entry")) {
            sendResponse(response, "Duplicate entry: " + e.getMessage());
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sendResponse(response, "Database error: " + e.getMessage());
        }
    }
}
