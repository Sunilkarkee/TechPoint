package com.techpoint.helper;

import java.util.regex.Pattern;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ValidationHelper {

    // Method to validate registration form inputs
    public static boolean validateRegistrationForm(String name, String email, String password, String phone, String gender, HttpServletResponse response) throws IOException {
        // Validation for required fields
        if (isEmpty(name) || isEmpty(email) || isEmpty(password) || isEmpty(phone)) {
            sendVResponse(response, "Please fill out all required fields.");
            return false;
        }

        // Validate email format
        if (!isValidEmail(email)) {
            sendVResponse(response, "Invalid email format.");
            return false;
        }

        // Validate password complexity
        if (!isValidPassword(password)) {
            sendVResponse(response, "Password must be at least 8 characters long and include uppercase, lowercase, and numeric characters.");
            return false;
        }

        // Validate gender selection
        if (isEmpty(gender) || "none".equals(gender)) {
            sendVResponse(response, "Please select Your Gender");
            return false;
        }

        return true;
    }

    // Helper method to check if a string is empty or null
    private static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    // Helper method to validate email format
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Helper method to validate password complexity
    private static boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*");
    }

    // Helper method to send response to client
    private static void sendVResponse(HttpServletResponse response, String message) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println(message);
        }
    }
}
