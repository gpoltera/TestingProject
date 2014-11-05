/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import crypto.ECDH;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.jce.interfaces.ECPublicKey;

/**
 *
 * @author Gian
 */
public class ECDHTest1 {

    public static void main(String[] args) throws IOException {
        int quote = 0;
        int count = 100;
        System.out.println("Starte " + count + "x ECDH-Schlüsselaustausch");
        System.out.println("---------------------------------------------------------------------------------");
        for (int i = 0; i < count; i++) {
            ECDH dh1 = new ECDH("P-256");
            ECDH dh2 = new ECDH("P-256");

            KeyPair keyPair1 = dh1.getKeyPair();
            KeyPair keyPair2 = dh2.getKeyPair();

            ECPublicKey publicKey1 = (ECPublicKey) keyPair1.getPublic();
            ECPublicKey publicKey2 = (ECPublicKey) keyPair2.getPublic();
            
            byte[] encPublicKey1 = publicKey1.getQ().getEncoded(true);
            byte[] encPublicKey2 = publicKey2.getQ().getEncoded(true);
            
            SecretKey secretKey1 = dh1.getSessionKey(keyPair1.getPrivate(), dh1.rawdataToPublicKey(encPublicKey2));
            SecretKey secretKey2 = dh2.getSessionKey(keyPair2.getPrivate(), dh2.rawdataToPublicKey(encPublicKey1));
            
            System.out.println("Public Key 1 Länge: " + encPublicKey1.length*8);
            System.out.println("Public Key 2 Länge: " + encPublicKey2.length*8);
            System.out.println("Key Agreement Länge: " + secretKey1.getEncoded().length*8);
            System.out.println("Session Key Länge: " + dh1.convertTo128(secretKey1).length*8);
            System.out.println("---------------------------------------------------------------------------------");
            if (secretKey1.equals(secretKey2)) {
                quote++;
            }
        }
        double dbQuote = quote;
        double dbCount = count;
        System.out.println("Erfolgsquote des Schlüsselaustauschs: " + dbCount/dbQuote*100 + "%");
        System.out.println("---------------------------------------------------------------------------------");
    }
}
