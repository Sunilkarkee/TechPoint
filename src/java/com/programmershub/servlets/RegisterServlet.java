package com.programmershub.servlets;

import com.programmershub.daos.UserDao;
import com.programmershub.entities.User;
import com.programmershub.helper.PgmDbConnector;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String name = request.getParameter("user_name");
        String email = request.getParameter("user_email");
        String password = request.getParameter("user_password");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone_number");
        String about = request.getParameter("about");

        // Validation for required fields and email format
        if (name == null || name.isEmpty() || email == null || email.isEmpty()
                || password == null || password.isEmpty() || phone == null || phone.isEmpty()) {
            sendResponse(response, "Please fill out all required fields.");
            return;
        }

        // Validate email format
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        if (!email.matches(emailRegex)) {
            sendResponse(response, "Invalid email format.");
            return;
        }

        // Validate password complexity
        if (password.length() < 8 || !password.matches(".*[A-Z].*")
                || !password.matches(".*[a-z].*") || !password.matches(".*\\d.*")) {
            sendResponse(response, "Password must be at least 8 characters long and include uppercase, lowercase, and numeric characters.");
            return;
        }

        if (gender == null || gender.equals("none")) {
            sendResponse(response, "Please select Your Gender");
            return;
        }

        // Create User object
        User user = new User(name, email, password, gender, phone, about);

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
        return "User Registration Servlet";
    }
}
