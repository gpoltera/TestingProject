/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import crypto.AESGCM;
import helpers.FileZipper;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import qrcode.QRCodeGenerator;

/**
 *
 * @author Gian
 */
public class Encryption {

    private static final SecureRandom secureRandom = new SecureRandom();
    private final List<SelectedFile> selectedFiles;
    private final String outputPath;
    private AESGCM aes;
    private FileZipper zip;
    private QRCodeGenerator qr;
    private BufferedImage biQR;

    public Encryption(List<SelectedFile> selectedFiles, String outputPath, boolean compression) {
        this.selectedFiles = selectedFiles;
        this.outputPath = outputPath;
        byte[] randomKey = createRandomArray(32);
        byte[] randomIv = createRandomArray(16);

        this.aes = new AESGCM(randomKey, randomIv);
        this.zip = new FileZipper();
        this.qr = new QRCodeGenerator();
        
        biQR = qr.createQR(new BigInteger(1, randomKey).toString(), 500);
        
        String output = System.getProperty("user.home") + File.separator + "test.encryption";
        String output2 = System.getProperty("user.home") + File.separator + "test2.zip";

        List<File> plaintextFiles = new ArrayList<>();
        selectedFiles.forEach((selectedFile) -> {
                plaintextFiles.add(selectedFile.getFile());
            });
        
        byte[] input = zip.getZippedBytes(plaintextFiles, compression);
        //byte[] input = noZip();
        boolean result = aes.encrypt(input, output);
        aes.decrypt(output, output2);
        //createSessionKey();

        //ZIP -> nosave
        //KeyGeneration -> QR-Code
        //AES-GCM -> save & key
    }

    private byte[] createRandomArray(int size) {
        byte[] randomByteArray = new byte[size];
        secureRandom.nextBytes(randomByteArray);

        return randomByteArray;
    }

    public VBox showContent() {
        VBox vb = new VBox(10);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(25, 25, 25, 25));

        Text txtTop = new Text("Bitte scannen Sie diesen Code mit dem Threema Smart-Phone App ein\nund senden Sie ihn an den gewünschten Empfänger:");
        Image image = SwingFXUtils.toFXImage(biQR, null);
        ImageView imageView = new ImageView(image);
        Button btnPrint = new Button("Drucken");
        btnPrint.setGraphic(new ImageView("file:icons/printer1.png"));
        
        vb.getChildren().addAll(txtTop, imageView, btnPrint);

        return vb;
    }
}
