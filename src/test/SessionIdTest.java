/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import helpers.Base32;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;
import qrcode.QRCodeGenerator;

/**
 *
 * @author Gian
 */
public class SessionIdTest {

    private static BigInteger test = new BigInteger("1298544534534545345345545453454545454545345435345345242342343243902173981273827382334343434232323343434343434343423782738");
    private static BigInteger test2 = new BigInteger(1, "hans.peter.mueller@bezirgsgericht-surselva.ch".getBytes());

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(test.bitLength());
        
        String test3 = test.toString(64);
        QRCodeGenerator qr = new QRCodeGenerator();
        
        for (int i = 0; i<5; i++) {
            test3 += test3;
        }
        qr.createQR(test3, 512);
    }
}
