/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import crypto.AESGCM;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Gian
 */
public class AESGCMTest {
    static byte[] key = new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17};
    static byte[] iv = new byte[]{0x01};
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        AESGCM aes = new AESGCM(key, iv);
        aes.InitCiphers();
        String input = System.getProperty("user.home") + File.separator + "test.zip";
        String output = System.getProperty("user.home") + File.separator + "test.encryption";
        String result = System.getProperty("user.home") + File.separator + "test2.zip";
        
        FileInputStream is = new FileInputStream(input);
        FileOutputStream os = new FileOutputStream(output);
        
        FileInputStream is2 = new FileInputStream(output);
        FileOutputStream os2 = new FileOutputStream(result);
        
        //aes.Encrypt(is, os);
        is.close();
        os.close();
        aes.Decrypt(is2, os2);
        is2.close();
        os2.close();
    }
}
