/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Gian
 */
public class FileHandler {
    public static boolean checkWritePermissions(String path) {
        File file = new File(path);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            for (int i = 0; i < 10000; i++) {
                byte[] byteTest = Integer.toString(i).getBytes();
                fos.write(byteTest);
            }
            return true;
        } catch (IOException ex) {
            return false;
        } finally {
           file.delete(); 
        }
    }
}
