package com.programmershub.servlets;

import com.programmershub.daos.UserDao;
import com.programmershub.entities.Messages;
import com.programmershub.entities.User;
import com.programmershub.helper.PgmDbConnector;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

  @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Retrieve form parameters
        String identifier = request.getParameter("Identifier");
        String password = request.getParameter("Password");

        if (identifier == null || password == null) {
            LOGGER.log(Level.WARNING, "Identifier or password is null");
            response.sendRedirect("login.jsp?error=Identifier or password cannot be null");
            return;
        }

        identifier = identifier.trim();
        password = password.trim();


        try (Connection con = PgmDbConnector.makeConnection()) {
            if (con == null) {
                LOGGER.log(Level.SEVERE, "Database connection failed");
                response.sendRedirect("login.jsp?error=Database connection failed");
                return;
            }

            UserDao dao = new UserDao(con);
            // Retrieve user based on email or phone number and password
            User u = dao.getUserByPhoneOrEmailAndPassword(identifier, password);

            if (u != null) {
                HttpSession session = request.getSession();
                session.setAttribute("currentUser", u);
                response.sendRedirect("profile.jsp");
            } else {
                LOGGER.log(Level.WARNING, "Invalid login attempt with identifier: {0}", identifier);
                
                Messages msg=new Messages("Invalid Details! try again","error","alert-danger");
                HttpSession s= request.getSession();
                s.setAttribute("msg", msg);
                response.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }
    }

  
    

    @Override
    public String getServletInfo() {
        return "LoginServlet handles user login";
    }
}
