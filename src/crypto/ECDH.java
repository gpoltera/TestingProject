/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.jce.spec.ECPublicKeySpec;

/**
 *
 * @author Gian
 */
public class ECDH {

    private ECParameterSpec ecSpec;

    public ECDH(String ec) {
        Security.addProvider(new BouncyCastleProvider());
        this.ecSpec = ECNamedCurveTable.getParameterSpec(ec);
    }

    public KeyPair getKeyPair() {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDH", "BC");
            keyPairGenerator.initialize(ecSpec, new SecureRandom());
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException ex) {
            Logger.getLogger(ECDH.class.getName()).log(Level.SEVERE, null, ex);
        }

        return keyPair;
    }

    public ECParameterSpec getECParamaeterSpec() {
        return ecSpec;
    }

    public SecretKey getSessionKey(PrivateKey privateKey, PublicKey publicKey) {
        SecretKey secretKey = null;
        try {
            KeyAgreement keyAgree = KeyAgreement.getInstance("ECDH", "BC");
            keyAgree.init(privateKey);
            keyAgree.doPhase(publicKey, true);
            secretKey = keyAgree.generateSecret(NISTObjectIdentifiers.id_aes128_GCM.getId());

        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException ex) {
            Logger.getLogger(ECDH.class.getName()).log(Level.SEVERE, null, ex);
        }

        return secretKey;
    }

    public ECPublicKey rawdataToPublicKey(byte[] publicKey) {
        ECPublicKey remotePublicKey = null;
        try {
            ECPoint point = ecSpec.getCurve().decodePoint(publicKey);
            ECPublicKeySpec publicKeySpec = new ECPublicKeySpec(point, ecSpec);
            remotePublicKey = (ECPublicKey) KeyFactory.getInstance("ECDH", "BC").generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException ex) {
            Logger.getLogger(ECDH.class.getName()).log(Level.SEVERE, null, ex);
        }
        return remotePublicKey;
    }

    public byte[] convertTo128(SecretKey secretKey) {
        PKCS5S2ParametersGenerator parameterGenerator = new PKCS5S2ParametersGenerator(new SHA256Digest());
        parameterGenerator.init(secretKey.getEncoded(), "salt".getBytes(), 4096);
        byte[] derivatedKey = ((KeyParameter) parameterGenerator.generateDerivedParameters(128)).getKey();
        
        return derivatedKey;
    }
}
