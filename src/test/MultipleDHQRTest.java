/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import crypto.RandomGenerator;
import crypto.SessionKey;
import helpers.Base32;
import helpers.XMLParser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import javax.imageio.ImageIO;
import qrcode.QRCodeGenerator;

/**
 *
 * @author Gian
 */
public class MultipleDHQRTest {

    private XMLParser xml = new XMLParser();

    public void generateQR() {
        QRCodeGenerator qr = new QRCodeGenerator();

        for (int i = 0; i < 5; i++) {
            String id = Integer.toBinaryString(i);
            
            while (id.length() < 5) {
                id = 0 + id;
            }
            System.out.println(id);
            System.out.println(Base32.getBase32(id));
            xml.addECDHRequest(Base32.getBase32(id), new SessionKey(256).getBase32());
        }
        String code = xml.getXMLasString();

        try {
            BufferedImage qrCode = qr.createQR(code, 500);
            File outputFile = new File("qr" + 500 + ".png");
            ImageIO.write(qrCode, "png", outputFile);
            System.out.println("IMAGE Created");

        } catch (IOException e) {
            System.out.printf("FEHLER" + e);
        }
    }

    public static void main(String[] args) {
        new MultipleDHQRTest().generateQR();
    }

}
