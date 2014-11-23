/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import crypto.HMACSHA2;
import crypto.HMacSHA;
import crypto.RandomGenerator;
import java.io.IOException;
import javax.crypto.SecretKey;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.prng.drbg.HMacSP800DRBG;


/**
 *
 * @author Gian
 */
public class HMacSHATest {
    public static void main(String[] args) throws IOException {
        HMACSHA2 test = new HMACSHA2(256);
        HMacSHA test2 = new HMacSHA();
        
        byte[] key = RandomGenerator.getRandom(256).toByteArray();
        byte[] result = test.getDerivatedKey(key);       
        SecretKey result2 = test2.getDerivatedKey(null, 256);
        
    }
}
