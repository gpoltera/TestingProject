/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import crypto.AESGCM;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import org.bouncycastle.util.Arrays;

/**
 *
 * @author Gian
 */
public class AESGCMTest {

    private static final SecureRandom secureRandom = new SecureRandom();

    public static void main(String[] args) throws FileNotFoundException, IOException {
        new AESGCMTest().aesTest1();
        System.out.println("--------------------------------------------");
        new AESGCMTest().aesTest2();
        System.out.println("--------------------------------------------");
        new AESGCMTest().aesTest3();
    }

    private boolean aesTest1() throws FileNotFoundException, IOException {
        byte[] randomKey = createRandomArray(16);
        byte[] randomIv = createRandomArray(16);

        AESGCM aes = new AESGCM(randomKey, randomIv);
        System.out.println("Schlüssellänge: " + randomKey.length * 8);

        String text = "1234567891011121314151617181920";
        byte[] input = text.getBytes();
        String encryption = System.getProperty("user.home") + File.separator + "test.encryption";
        String decryption = System.getProperty("user.home") + File.separator + "test2.txt";

        //Test1, Encryption & Decryption from a Text String
        System.out.println("Test1, Encryption & Decryption from Text String");
        System.out.println("Input: " + text);
        aes.encrypt(input, encryption);
        aes.decrypt(encryption, decryption);
        BufferedReader file_encryption = new BufferedReader(new FileReader(encryption));
        BufferedReader file_decryption = new BufferedReader(new FileReader(decryption));
        String encryption_line = file_encryption.readLine();
        String decryption_line = file_decryption.readLine();
        System.out.println("Encrypted: " + encryption_line);
        System.out.println("Decrypted: " + decryption_line);

        if (text.equals(decryption_line)) {
            System.out.println("Result: OK");
            return true;
        } else {
            System.out.println("Result: False");
            return false;
        }
    }

    private boolean aesTest2() throws FileNotFoundException, IOException {
        byte[] randomKey = createRandomArray(16);
        byte[] randomIv = createRandomArray(16);

        AESGCM aes = new AESGCM(randomKey, randomIv);
        System.out.println("Schlüssellänge: " + randomKey.length * 8);

        String input = System.getProperty("user.home") + File.separator + "test2.txt";
        String encryption = System.getProperty("user.home") + File.separator + "test.encryption";
        String decryption = System.getProperty("user.home") + File.separator + "test3.txt";

        //Test2, Encryption & Decryption from a Text File
        System.out.println("Test1, Encryption & Decryption from a Text File");
        BufferedReader file_input = new BufferedReader(new FileReader(input));
        System.out.println("Input: " + file_input.readLine());
        byte[] byte_input = Files.readAllBytes(Paths.get(input));
        aes.encrypt(byte_input, encryption);
        aes.decrypt(encryption, decryption);
        BufferedReader file_encryption = new BufferedReader(new FileReader(encryption));
        BufferedReader file_decryption = new BufferedReader(new FileReader(decryption));
        String encryption_line = file_encryption.readLine();
        String decryption_line = file_decryption.readLine();
        System.out.println("Encrypted: " + encryption_line);
        System.out.println("Decrypted: " + decryption_line);
        byte[] byte_decryption = Files.readAllBytes(Paths.get(decryption));

        if (Arrays.areEqual(byte_input, byte_decryption)) {
            System.out.println("Result: OK");
            return true;
        } else {
            System.out.println("Result: False");
            return false;
        }
    }

    private boolean aesTest3() throws FileNotFoundException, IOException {
        byte[] randomKey = createRandomArray(16);
        byte[] randomIv = createRandomArray(16);

        AESGCM aes = new AESGCM(randomKey, randomIv);
        System.out.println("Schlüssellänge: " + randomKey.length * 8);

        String text = "HSR Hochschule für Technik Rapperswil";
        byte[] input = text.getBytes();
        String encryption = System.getProperty("user.home") + File.separator + "test.encryption";
        String decryption = System.getProperty("user.home") + File.separator + "test2.txt";

        //Test3, Encryption & Decryption with change one byte
        System.out.println("Test3, Encryption & Decryption with change one byte");
        System.out.println("Input: " + text);
        
        aes.encrypt(input, encryption);
        BufferedReader file_encryption = new BufferedReader(new FileReader(encryption));
        String encryption_line = file_encryption.readLine();
        System.out.println("Encrypted: " + encryption_line);   
        byte[] byte_encryption = Files.readAllBytes(Paths.get(encryption));
        if (byte_encryption[byte_encryption.length - 1] == 106) {
            byte_encryption[byte_encryption.length - 1] = 105;
        } else {
            byte_encryption[byte_encryption.length - 1] = 106;
        }
        Files.write(Paths.get(encryption), byte_encryption);
        BufferedReader file_encryption2 = new BufferedReader(new FileReader(encryption));
        String encryption2_line = file_encryption2.readLine();
        System.out.println("Changed: " + encryption2_line);

        boolean decResult = aes.decrypt(encryption, decryption);
        BufferedReader file_decryption = new BufferedReader(new FileReader(decryption));
        String decryption_line = file_decryption.readLine();
        System.out.println("Decrypted: " + decryption_line);
        
        if (!decResult) {
            System.out.println("Result: OK, Hash Failure detected!");
            return true;
        } else {
            System.out.println("Result: False");
            return false;
        }
    }

    private static byte[] createRandomArray(int size) {
        byte[] randomByteArray = new byte[size];
        secureRandom.nextBytes(randomByteArray);

        return randomByteArray;
    }
}
