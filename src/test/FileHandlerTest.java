/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import helpers.FileHandler;
import java.io.File;

/**
 *
 * @author Gian
 */
public class FileHandlerTest {

    public static void main(String[] args) {
        String path = System.getProperty("user.home") + File.separator + "test.enc";
        boolean result = FileHandler.checkWritePermissions(path);
        System.out.println("Test1, write to home");
        System.out.println("Path: " + path);
        System.out.println("Result: " + result);
        System.out.println("--------------------------------------------");
        String path2 = "C:" + File.separator + "test.enc";
        boolean result2 = FileHandler.checkWritePermissions(path2);
        System.out.println("Test2, write to c");
        System.out.println("Path: " + path2);
        System.out.println("Result: " + result2);
    }
}
