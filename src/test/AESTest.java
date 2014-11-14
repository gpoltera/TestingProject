/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import crypto.AES;
import crypto.RandomGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.util.encoders.Hex;

/**
 *
 * @author Gian
 */
public class AESTest {
    public static void main(String[] args) {
        AES aes = new AES();
        byte[] p = "www.hsr.ch".getBytes();
        byte[] key = new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17}; 
        System.out.println(key.length);
        byte[] iv = Hex.decode("cafebabefacedbaddecaf888");
        String mode = "AES/GCM/NoPadding";
        aes.encrypt(p, key, iv, mode);
    }
}