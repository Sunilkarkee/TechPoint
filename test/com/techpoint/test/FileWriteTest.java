
package com.techpoint.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriteTest {
    public static void main(String[] args){
     
        String UPLOAD_DIRECTORY = "/home/cosmic/Desktop/JavaProjects/TechPoint/web/profilepics";

    
        File testFile = new File(UPLOAD_DIRECTORY + "/testfile.txt");
        try (FileOutputStream fos = new FileOutputStream(testFile)) {
            fos.write("This is a test".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
