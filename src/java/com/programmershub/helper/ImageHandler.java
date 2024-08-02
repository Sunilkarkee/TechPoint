package com.programmershub.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import jakarta.servlet.http.Part;

public class ImageHandler {

    // Saves the file from the InputStream to the specified path
    public static boolean saveFile(InputStream is, String path) {
        boolean isSaved = false;
        final int BUFFER_SIZE = 4096; // Use a fixed buffer size

        try (FileOutputStream fos = new FileOutputStream(path)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            isSaved = true;
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close(); // Ensure InputStream is closed
                }
            } catch (IOException ex) {
                System.err.println("Error closing InputStream: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return isSaved;
    }

    // Processes the uploaded image
    public static String processImage(Part filePart, String uploadDirectory) throws IOException {
        if (filePart != null && filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().isEmpty()) {
            String fileName = filePart.getSubmittedFileName(); // Get the original file name
            File uploadDir = new File(uploadDirectory);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String filePath = uploadDirectory + File.separator + fileName;
            boolean isFileSaved = saveFile(filePart.getInputStream(), filePath);

            if (isFileSaved) {
                return fileName; // Return the original file name
            }
        }
        return null;
    }

    // Deletes the file at the specified path
    public static boolean deleteFile(String path) {
        boolean isDeleted = false;
        try {
            File file = new File(path);
            isDeleted = file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleted;
    }
}
