/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/**
 *
 * @author Gian
 */
public class AESGCM {

    GCMBlockCipher encryptCipher = null;
    GCMBlockCipher decryptCipher = null;
    byte[] inputBuffer = new byte[16];
    byte[] outputBuffer = new byte[512];
    byte[] key = null;
    byte[] iv = null;
    int blockSize = 16;

    public AESGCM(byte[] key, byte[] iv) {
        this.key = new byte[key.length];
        System.arraycopy(key, 0, this.key, 0, key.length);

        this.iv = new byte[iv.length];
        System.arraycopy(iv, 0, this.iv, 0, iv.length);
    }

    public void InitCiphers() {
        encryptCipher = new GCMBlockCipher(new AESEngine());
        decryptCipher = new GCMBlockCipher(new AESEngine());
        ParametersWithIV parameterIV = new ParametersWithIV(new KeyParameter(key), iv);
        encryptCipher.init(true, parameterIV);
        decryptCipher.init(false, parameterIV);
    }

    public void Encrypt(InputStream in, OutputStream out) {
        try {
            int noBytesRead = 0;        //number of bytes read from input
            int noBytesProcessed = 0;   //number of bytes processed

            while ((noBytesRead = in.read(inputBuffer)) >= 0) {
                noBytesProcessed = encryptCipher.processBytes(inputBuffer, 0, noBytesRead, outputBuffer, 0);
                out.write(outputBuffer, 0, noBytesProcessed);
            }

            noBytesProcessed = encryptCipher.doFinal(outputBuffer, 0);
            out.write(outputBuffer, 0, noBytesProcessed);
            out.flush();
            in.close();
            out.close();
        } catch (IOException | IllegalStateException | InvalidCipherTextException ex) {
            Logger.getLogger(AESGCM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Decrypt(InputStream in, OutputStream out) {
        try {
            int noBytesRead = 0;        //number of bytes read from input
            int noBytesProcessed = 0;   //number of bytes processed 

            while ((noBytesRead = in.read(inputBuffer)) >= 0) {
                noBytesProcessed = decryptCipher.processBytes(inputBuffer, 0, noBytesRead, outputBuffer, 0);
                out.write(outputBuffer, 0, noBytesProcessed);
            }
            noBytesProcessed = decryptCipher.doFinal(outputBuffer, 0);
            out.write(outputBuffer, 0, noBytesProcessed);
            out.flush();
            in.close();
            out.close();
        } catch (IOException | IllegalStateException | InvalidCipherTextException ex) {
            Logger.getLogger(AESGCM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void resetCiphers() {
        if (encryptCipher != null) {
            encryptCipher.reset();
        }
        if (decryptCipher != null) {
            decryptCipher.reset();
        }
    }
}
