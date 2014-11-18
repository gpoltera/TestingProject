/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.math.BigInteger;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 *
 * @author Gian
 */
public class HMACSHA2 {

    byte[] inputBuffer, outputBuffer, derivatedKey = null;

    public HMACSHA2(int length) {
        this.outputBuffer = new byte[length / 8];
        this.inputBuffer = "Secure Exchange".getBytes();
        this.derivatedKey = new byte[length / 16];
    }

    public byte[] getDerivatedKey(byte[] key) {
        Digest digest = new SHA256Digest();
        HMac mac = new HMac(digest);
        mac.init(new KeyParameter(key));
        mac.update(inputBuffer, 0, inputBuffer.length);
        mac.doFinal(outputBuffer, 0);

        System.out.println("Länge: " + mac.getMacSize() * 8 + ", Algorithmus: " + mac.getAlgorithmName());

        // Source Array, From Source, Destination Array, To Destination, Count
        System.arraycopy(outputBuffer, 0, derivatedKey, 0, derivatedKey.length);

        System.out.println("Länge: " + derivatedKey.length * 8);

        System.out.println("\nkey:");
        for (byte keyByte : key) {
            System.out.print(keyByte + " ");
        }
        System.out.println("\nbefore:");
        for (byte beforeByte : outputBuffer) {
            System.out.print(beforeByte + " ");
        }
        System.out.println("\nafter:");
        for (byte afterByte : derivatedKey) {
            System.out.print(afterByte + " ");
        }
        BigInteger result = new BigInteger(1, outputBuffer);
        System.out.println("");
        System.out.println(result.toString(16).toUpperCase());
        
        return derivatedKey;
    }
}
