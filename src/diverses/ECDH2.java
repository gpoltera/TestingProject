/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diverses;

import java.security.spec.ECParameterSpec;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.jce.spec.ECPointUtil;

/**
 *
 * @author Gian
 */
public class ECDH2 {

    private ECParameterSpec getECParameters() {
        ECParameterSpec params = new ECNamedCurveSpec(ECNamedCurveTable.getParameterSpec("prime239v1"));

    }
}
