/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 *
 * @author Gian
 */
public class ECDHKeyPair extends KeyPair {

    public ECDHKeyPair(PublicKey publicKey, PrivateKey privateKey) {
        super(publicKey, privateKey);
    }
    
}
