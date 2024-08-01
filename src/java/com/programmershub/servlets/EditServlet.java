package com.programmershub.servlets;

import com.programmershub.daos.UserDao;
import com.programmershub.entities.Messages;
import com.programmershub.entities.User;
import com.programmershub.helper.PgmDbConnector;
import com.programmershub.helper.ImageHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@MultipartConfig
public class EditServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "C:\\Users\\Suneel\\Desktop\\JAVA PROJECTS\\ProgrammersHub\\web\\profilepics";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        if (request.getContentType() == null || !request.getContentType().contains("multipart/form-data")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendResponse(response, "Request must be of type multipart/form-data");
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userName = request.getParameter("user_name");
        String userEmail = request.getParameter("user_email");
        String userNumber = request.getParameter("user_phone");
        String userPassword = request.getParameter("user_pwd");
        String userAbout = request.getParameter("user_about");

        Part filePart = request.getPart("image");
        String newImageName = ImageHandler.processImage(filePart, UPLOAD_DIRECTORY); // Use the helper method

        String oldImageName = user.getProfile(); // Get the old profile picture name
        if (newImageName != null) {
            user.setProfile(newImageName);
        }

        user.setName(userName);
        user.setEmail(userEmail);
        user.setPhone_number(userNumber);
        user.setPassword(userPassword);
        user.setAbout(userAbout);

        try (Connection con = PgmDbConnector.makeConnection()) {
            UserDao dao = new UserDao(con);
            boolean isSuccess = dao.updateUser(user);
            if (isSuccess) {
                if (newImageName != null) {
                    // Delete old image except default.png
                    if (oldImageName != null && !oldImageName.equals("default.png")) {
                        String oldImagePath = UPLOAD_DIRECTORY + File.separator + oldImageName;
                        ImageHandler.deleteFile(oldImagePath);
                    }
                    setSessionMessage(session, "Profile updated successfully.", "success", "alert-success");
                } else {
                    setSessionMessage(session, "Profile updated successfully.", "success", "alert-success");
                }
                sendResponse(response, "Update successful.");
            } else {
                setSessionMessage(session, "Update failed.", "error", "alert-danger");
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

    private void setSessionMessage(HttpSession session, String content, String type, String cssClass) {
        Messages msg = new Messages(content, type, cssClass);
        session.setAttribute("msg", msg);
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
