/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import crypto.ECDH;
import crypto.HMacSHA;
import helpers.Base32;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import javax.crypto.SecretKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;

/**
 *
 * @author Gian
 */
public class ECDHTest1 {

    public static void main(String[] args) throws IOException {
        int count = 1;
        ecdhTest(256, count);
        ecdhTest(512, count);
    }

    private static void ecdhTest(int length, int count) {
        int quote = 0;

        System.out.println("Starte " + count + "x ECDH256-Schlüsselaustausch");
        System.out.println("---------------------------------------------------------------------------------");
        for (int i = 0; i < count; i++) {
            ECDH dh1 = new ECDH("brainpoolP" + length + "t1");
            ECDH dh2 = new ECDH("brainpoolP" + length + "t1");

            KeyPair keyPair1 = dh1.getKeyPair();
            KeyPair keyPair2 = dh2.getKeyPair();

            ECPublicKey publicKey1 = (ECPublicKey) keyPair1.getPublic();
            ECPublicKey publicKey2 = (ECPublicKey) keyPair2.getPublic();

            byte[] encPublicKey1 = publicKey1.getQ().getEncoded(true);
            byte[] encPublicKey2 = publicKey2.getQ().getEncoded(true);

            SecretKey secretKey1 = dh1.getSessionKey(keyPair1.getPrivate(), dh1.rawdataToPublicKey(encPublicKey2));
            SecretKey secretKey2 = dh2.getSessionKey(keyPair2.getPrivate(), dh2.rawdataToPublicKey(encPublicKey1));

            SecretKey sessionKey1 = new HMacSHA().getDerivatedKey(secretKey1, length);
            SecretKey sessionKey2 = new HMacSHA().getDerivatedKey(secretKey2, length);

            System.out.println("Public Key 1, Länge: " + encPublicKey1.length * 8 + " Wert: " + Base32.getBase32(new BigInteger(1, encPublicKey1).toString(2)));
            System.out.println("Public Key 2, Länge: " + encPublicKey2.length * 8 + " Wert: " + Base32.getBase32(new BigInteger(1, encPublicKey2).toString(2)));
            System.out.println("Key Agreement 1, Länge: " + secretKey1.getEncoded().length * 8 + " Wert: " + Base32.getBase32(new BigInteger(1, secretKey1.getEncoded()).toString(2)));
            System.out.println("Key Agreement 2, Länge: " + secretKey2.getEncoded().length * 8 + " Wert: " + Base32.getBase32(new BigInteger(1, secretKey2.getEncoded()).toString(2)));
            System.out.println("Derivated Key 1, Länge: " + sessionKey1.getEncoded().length * 8 + " Wert: " + Base32.getBase32(new BigInteger(1, sessionKey1.getEncoded()).toString(2)));
            System.out.println("Derivated Key 2, Länge: " + sessionKey2.getEncoded().length * 8 + " Wert: " + Base32.getBase32(new BigInteger(1, sessionKey2.getEncoded()).toString(2)));
            System.out.println("---------------------------------------------------------------------------------");
            if (sessionKey1.equals(sessionKey2)) {
                quote++;
            }
        }
        double dbQuote = quote;
        double dbCount = count;
        System.out.println("Erfolgsquote des Schlüsselaustauschs: " + dbCount / dbQuote * 100 + "%");
        System.out.println("---------------------------------------------------------------------------------");
    }
}
