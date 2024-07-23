package com.programmershub.servlets;

import com.programmershub.daos.UserDao;
import com.programmershub.entities.User;
import com.programmershub.helper.PgmDbConnector;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import javax.imageio.ImageIO;

@MultipartConfig
public class EditServlet extends HttpServlet {

    private static final int MAX_WIDTH = 600; // Max width of the image
    private static final int MAX_HEIGHT = 600; // Max height of the image

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Ensure request is multipart
        String contentType = request.getContentType();
        if (contentType == null || !contentType.contains("multipart/form-data")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendResponse(response, "Request must be of type multipart/form-data");
            return;
        }

        // Getting user from the HTTP session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Fetch all data from the edit form
        String userName = request.getParameter("user_name");
        String userEmail = request.getParameter("user_email");
        String userNumber = request.getParameter("user_phone");
        String userPassword = request.getParameter("user_pwd");
        String userAbout = request.getParameter("user_about");

        // Handle file upload
        Part filePart = request.getPart("image");
        String profilePicPath = user.getProfile(); // Default to existing profile picture
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadDir = getServletContext().getRealPath("") + File.separator + "profilepics";
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdir();
            }

            // Resize the image before saving
            InputStream fileContent = filePart.getInputStream();
            BufferedImage originalImage = ImageIO.read(fileContent);
            BufferedImage resizedImage = resizeImage(originalImage, MAX_WIDTH, MAX_HEIGHT);

            File file = new File(uploadDir + File.separator + fileName);
            try (FileOutputStream outStream = new FileOutputStream(file)) {
                ImageIO.write(resizedImage, "jpg", outStream); // Save as JPG format
            }
            profilePicPath = fileName;
        }

        // Update user details
        user.setName(userName);
        user.setEmail(userEmail);
        user.setPhone_number(userNumber);
        user.setPassword(userPassword);
        user.setAbout(userAbout);
        user.setProfile(profilePicPath);

        // Create UserDao object and attempt to save the user
        try (Connection con = PgmDbConnector.makeConnection()) {
            UserDao dao = new UserDao(con);
            boolean isSuccess = dao.updateUser(user);
            if (isSuccess) {
                sendResponse(response, "Update successful.");
            } else {
                sendResponse(response, "Update failed.");
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sendResponse(response, "Internal server error: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sendResponse(response, "Internal server error: " + e.getMessage());
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image tmp = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return resizedImage;
    }

    private void sendResponse(HttpServletResponse response, String message) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><body>");
            out.println("<h1>" + message + "</h1>");
            out.println("</body></html>");
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
        return "Servlet for editing user information";
    }
}
