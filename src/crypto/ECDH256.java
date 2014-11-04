/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Gian
 */
public class ECDH256 {

    private static final BigInteger A = new BigInteger("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF", 16); //Group Prime/Irreducible Polynomial 
    //private static final BigInteger BIG1 = BigInteger.valueOf(1);
    //private static final BigInteger BIG2 = BigInteger.valueOf(2);
    //BigInteger result = (BIG2.pow(256)).subtract(BIG2.pow(224)).add(BIG2.pow(192)).add(BIG2.pow(96)).subtract(BIG1);
    private static final BigInteger B = new BigInteger("5AC635D8AA3A93E7B3EBBD55769886BC651D06B0CC53B0F63BCE3C3E27D2604B", 16); //Group Curve
    private static final BigInteger Q = new BigInteger("FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551", 16); //Group Order
    //private static final BigInteger SEED = new BigInteger("C49D360886E704936A6678E1139D26B7819F7E90", 16); //Seed
    private static final BigInteger GX = new BigInteger("6B17D1F2E12C4247F8BCE6E563A440F277037D812DEB33A0F4A13945D898C296", 16); //Generator x
    private static final BigInteger GY = new BigInteger("4FE342E2FE1A7F9B8EE7EB4A7C0F9E162BCE33576B315ECECBB6406837BF51F5", 16); //Generator y

    private ECParameterSpec ecSpec;

    public ECDH256() {
        Security.addProvider(new BouncyCastleProvider());
        System.out.println(B);
        this.ecSpec = getECParameterSpec();
        
    }

    private ECParameterSpec getECParameterSpec() {
        ECField field = new ECFieldFp(Q);
        EllipticCurve curve = new EllipticCurve(field,A,B); //field, a, b, seed
        
        
        ECCurve curve = new ECCurve.F2m(239, 36, A, B); //a, b
        System.out.println(curve.getA());
        System.out.println(curve.getB());
        System.out.println(curve.getOrder());
        System.out.println(curve.getField().getCharacteristic());
        ECPoint g = curve.createPoint(GX, GY);
        ECParameterSpec ecSpec = new ECParameterSpec(curve, g, Q); //curve, g, n, h, seed

        return ecSpec;
    }

    public KeyPair getKeyPair() {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDH", "BC");
            keyPairGenerator.initialize(ecSpec, new SecureRandom());
            keyPair = keyPairGenerator.generateKeyPair(); 
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException ex) {
            Logger.getLogger(ECDH256.class.getName()).log(Level.SEVERE, null, ex);
        }
        return keyPair;
    }

    public static void main(String[] args) {
        new ECDH256().getKeyPair();
    }
}
