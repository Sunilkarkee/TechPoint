package com.techpoint.servlets;

import com.techpoint.daos.UserDao;
import com.techpoint.entities.User;
import com.techpoint.helper.ImageHandler;
import com.techpoint.helper.PgmDbConnector;
import com.techpoint.helper.ValidationHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig
public class RegisterServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "/home/cosmic/Desktop/JavaProjects/TechPoint/web/profilepics";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        if (request.getContentType() == null || !request.getContentType().contains("multipart/form-data")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendResponse(response, "Request must be of type multipart/form-data");
            return;
        }

        String name = request.getParameter("user_name");
        String email = request.getParameter("user_email");
        String password = request.getParameter("user_password");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone_number");
        String about = request.getParameter("about");

        if (!ValidationHelper.validateRegistrationForm(name, email, password, phone, gender, response)) {
            return;
        }

        // Handle profile picture upload
        Part part = request.getPart("profile_pic");
        String fileName = "default.png"; // Default value

        if (part != null && part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
            String uploadedFileName = ImageHandler.processImage(part, UPLOAD_DIRECTORY);
            if (uploadedFileName != null) {
                fileName = uploadedFileName;
            }
        }
        // Create User object
        User user = new User(name, email, password, gender, phone, about, fileName);
        System.out.println("User to be saved: " + user);

        // Debug statement to check User object
        System.out.println("User to be saved: " + user);

        // Create UserDao object and attempt to save the user
        try (Connection con = PgmDbConnector.makeConnection()) {
            UserDao dao = new UserDao(con);
            boolean isSuccess = dao.saveUser(user);
            if (isSuccess) {
                sendResponse(response, "Registration successful.");
            } else {
                sendResponse(response, "Registration failed.");
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry for email.")) {
                sendResponse(response, "This email is already registered. Please use a different email.");
            } else if (e.getMessage().contains("Duplicate entry for phone number.")) {
                sendResponse(response, "This phone number is already registered. Please use a different phone number.");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                sendResponse(response, "Internal server error!!!: " + e.getMessage());
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sendResponse(response, "Internal server error: " + e.getMessage());
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
        return "Short description";
    }
}
