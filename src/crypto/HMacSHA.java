/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Gian
 */
public class HMacSHA {

    public HMacSHA() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public SecretKey getDerivatedKey(SecretKey secretKey, int length) {
        try {
            Mac mac = Mac.getInstance("Hmac-SHA" + length, "BC");
            mac.init(secretKey);
            mac.reset();

            byte[] input = "Secure Exchange".getBytes();
            mac.update(input, 0, input.length);
            byte[] out = mac.doFinal();
            byte[] derivationKey = new byte[length / 16];

            if (out.length >= derivationKey.length) {
                // Source Array, From Source, Destination Array, To Destination, Count
                System.arraycopy(out, 0, derivationKey, 0, derivationKey.length);
            }
            SecretKey sessionKey = new SecretKeySpec(derivationKey, 0, derivationKey.length, "AES-GCM");
            //secretKey.destroy();

            System.out.println("\nbefore:");
            for (byte beforeByte : out) {
                System.out.print(beforeByte + " ");
            }
            System.out.println("\nafter:");
            for (byte afterByte : sessionKey.getEncoded()) {
                System.out.print(afterByte + " ");
            }
            System.out.println("");
            
            return sessionKey;

        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException ex) {
            Logger.getLogger(HMacSHA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
