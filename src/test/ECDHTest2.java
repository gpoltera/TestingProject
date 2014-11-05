/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import crypto.ECDH;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.spec.EllipticCurve;
import javax.crypto.SecretKey;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECFieldElement.Fp;
import org.bouncycastle.math.ec.ECPoint;

/**
 *
 * @author Gian
 */
public class ECDHTest2 {

    public static void main(String[] args) {
        int quote = 0;
        for (int i = 0; i < 10000; i++) {
        ECDH dh1 = new ECDH("P-256");
        ECDH dh2 = new ECDH("P-256");

        KeyPair kp1 = dh1.getKeyPair();
        KeyPair kp2 = dh2.getKeyPair();

        SecretKey sk1 = dh1.getSessionKey(kp1.getPrivate(), kp2.getPublic());
        SecretKey sk2 = dh2.getSessionKey(kp2.getPrivate(), kp1.getPublic());

        BigInteger test1 = new BigInteger(1, sk1.getEncoded());
        BigInteger test2 = new BigInteger(1, sk2.getEncoded());

        //System.out.println("SK1: " + test1.toString(16).toUpperCase() + " -> " + test1.bitLength() + " Bit");
        //System.out.println("SK2: " + test2.toString(16).toUpperCase() + " -> " + test2.bitLength() + " Bit");

        //System.out.println(kp1.getPublic());
        ECPublicKey pk = (ECPublicKey) kp1.getPublic();
        
        byte[] test = pk.getQ().getEncoded(true);
        System.out.println(new BigInteger(1, test).bitLength());
        BigInteger x = pk.getQ().getAffineXCoord().toBigInteger();
        BigInteger y = pk.getQ().getAffineYCoord().toBigInteger();
        //System.out.println("X: " + x);
        //System.out.println("Y: " + y);
        
        String yBit = y.toString(2).substring(y.toString(2).length() - 1);
        //System.out.println(yBit);
        
        BigInteger y2 = lift_x(dh2, x, yBit);
        //System.out.println("Y: " + y2);
        
        if(y.equals(y2)) {
            quote++;
        }
        }
        System.out.println(quote);
    }

    private static BigInteger lift_x(ECDH dh, BigInteger x, String yBit) {
        // y^2 = x^3 + 115792089210356248762697446949407573530086143415290314195533631308867097853948*x + 41058363725152142129326129780047268409114441015993725554835256314039467401291
        // p = 115792089210356248762697446949407573530086143415290314195533631308867097853951
        BigInteger BIG3 = new BigInteger("3");
        BigInteger a = dh.getECParamaeterSpec().getCurve().getA().toBigInteger();
        BigInteger b = dh.getECParamaeterSpec().getCurve().getB().toBigInteger();
        BigInteger p = dh.getECParamaeterSpec().getCurve().getField().getCharacteristic();
        
        //y^2 = x^3 - 3x + b
        BigInteger y2 = (x.modPow(BIG3, p).add(a.multiply(x)).add(b)).mod(p);

        ECFieldElement.Fp ySquare = new ECFieldElement.Fp(p, y2);
        BigInteger y = ySquare.sqrt().toBigInteger().mod(p);
        
        String yBit2 = y.toString(2).substring(y.toString(2).length() - 1);
        if (!yBit.equals(yBit2)) {
            y = ySquare.sqrt().negate().toBigInteger().mod(p);
        }
        
        return y;
    }
}